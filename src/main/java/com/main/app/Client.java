package com.main.app;

import com.google.protobuf.*;
import java.net.*;
import java.io.*;

import com.main.app.TcpPacketProtos.*;
import com.main.app.PlayerProtos.*;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextArea;

public class Client {
    private static final String SERVER_IP = "202.92.144.45";
    private static final int PORT = 80;
    private static Socket server;
    private static BufferedReader client_br;
    private static String lobbyId;
    private static OutputStream outputStream;
    private static DataInputStream inputStream;
    private static ArrayList<Player> playerList;
    private static Player player;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean connected;
    private static int maxPlayers;
    private static String playerName;
    private JTextArea textArea;

    public Client(int choice, int maxPlayers, String playerName) throws IOException {
        this.maxPlayers = maxPlayers;
        this.playerName = playerName;
        runClient(choice);
    }

    public Client(int choice, String lobbyId, String playerName) throws IOException {
        this.lobbyId = lobbyId;
        this.playerName = playerName;
        runClient(choice);
    }

    public Client(int choice) throws IOException {
        runClient(choice);
    }

    public void setTextArea(JTextArea textArea){
        this.textArea = textArea;
    }

    public static void updatePlayerList() throws IOException {
        TcpPacket.PlayerListPacket playersPacket = TcpPacket.PlayerListPacket.newBuilder()
            .setType(TcpPacket.PacketType.PLAYER_LIST)
            .build();
        
        outputStream.write(playersPacket.toByteArray());
        outputStream.flush();

        while(inputStream.available() == 0){}
        byte[] serverResponse = new byte[inputStream.available()];
        inputStream.read(serverResponse);

        TcpPacket receivedPacket = TcpPacket.parseFrom(serverResponse);
        if(receivedPacket.getType() == TcpPacket.PacketType.PLAYER_LIST){                        
            playersPacket = TcpPacket.PlayerListPacket.parseFrom(serverResponse);
            playerList = new ArrayList<Player>();
            
            for(int i = 0; i < playersPacket.getPlayerListCount(); i++){
                playerList.add(playersPacket.getPlayerList(i));
            }
        }
    }

    public static void sendMessage(String message) throws IOException {

        if(message.equals("exit")){
            TcpPacket.DisconnectPacket disconnect = TcpPacket.DisconnectPacket.newBuilder()
                    .setType(TcpPacket.PacketType.DISCONNECT)
                    .build();
            
            outputStream.write(disconnect.toByteArray());
            outputStream.flush();

            System.out.println("You left the game.");
            connected = false;
            server.close();
        }else{
            TcpPacket.ChatPacket chatPacket = TcpPacket.ChatPacket.newBuilder()
                    .setType(TcpPacket.PacketType.CHAT)
                    .setMessage(message)
                    .setPlayer(player)
                    .build();         
            outputStream.write(chatPacket.toByteArray());
            outputStream.flush();
        }
    }
    
    public void runClient(int choice) throws IOException {

        server = new Socket(SERVER_IP, PORT);
        client_br = new BufferedReader(new InputStreamReader(System.in));
        outputStream = server.getOutputStream();
        inputStream = new DataInputStream(server.getInputStream());

        if(server != null && outputStream != null && inputStream != null){
            
            while(true){
                byte[] serverResponse;
                TcpPacket receivedPacket;
                switch(choice){
                    
                    case 1: //Create Lobby
                        TcpPacket.CreateLobbyPacket lobby = TcpPacket.CreateLobbyPacket.newBuilder()
                            .setType(TcpPacket.PacketType.CREATE_LOBBY)
                            .setMaxPlayers(maxPlayers)
                            .build();

                        outputStream.write(lobby.toByteArray());
                        outputStream.flush();

                        while(inputStream.available() == 0){}
                        serverResponse = new byte[inputStream.available()];
                        inputStream.read(serverResponse);

                        receivedPacket = TcpPacket.parseFrom(serverResponse);
                        if(receivedPacket.getType() ==  TcpPacket.PacketType.CREATE_LOBBY){
                            lobby = TcpPacket.CreateLobbyPacket.parseFrom(serverResponse);
                            lobbyId = lobby.getLobbyId();
                            System.out.print("Lobby successfully created!\nID: " + lobbyId + "\n");
                        }else{
                            TcpPacket.ErrPacket error = TcpPacket.ErrPacket.parseFrom(serverResponse);
                            System.out.println(error.getErrMessage());
                            break;
                        }
                    
                    case 2: //Connect to a Lobby
                        player = Player.newBuilder().setName(playerName).build();

                        TcpPacket.ConnectPacket connectLobby = TcpPacket.ConnectPacket.newBuilder()
                            .setType(TcpPacket.PacketType.CONNECT)
                            .setPlayer(player)
                            .setLobbyId(lobbyId)
                            .build();

                        outputStream.write(connectLobby.toByteArray());
                        outputStream.flush();

                        while(inputStream.available() == 0){}
                        serverResponse = new byte[inputStream.available()];
                        inputStream.read(serverResponse);

                        receivedPacket = TcpPacket.parseFrom(serverResponse);
                        if(receivedPacket.getType() ==  TcpPacket.PacketType.CONNECT){
                            connectLobby = TcpPacket.ConnectPacket.parseFrom(serverResponse);
                            lobbyId = connectLobby.getLobbyId();
                            connected = true;
                            System.out.print("Succesfully joined lobby!\nID: " + lobbyId + "\n");
                        }else if(receivedPacket.getType() == TcpPacket.PacketType.ERR_LDNE){
                            TcpPacket.ErrLdnePacket reply = TcpPacket.ErrLdnePacket.parseFrom(serverResponse);
                            System.out.println("Error! Specified lobby does not exist.");
                            System.out.println(reply.getErrMessage());
                        }else if(receivedPacket.getType() == TcpPacket.PacketType.ERR_LFULL){
                            TcpPacket.ErrLfullPacket reply = TcpPacket.ErrLfullPacket.parseFrom(serverResponse);
                            System.out.println("Error! Specified lobby is full.");
                            System.out.println(reply.getErrMessage());
                        }else{
                            TcpPacket.ErrPacket error = TcpPacket.ErrPacket.parseFrom(serverResponse);
                            System.out.println(error.getErrMessage());
                        }
                        break;

                    case 3:
                        System.exit(0);
                    break;

                    default:
                        break;
                }

                if(connected) break;
            }

            //Start Chat
            Thread statusThread = new Thread(new Runnable()
                {
                    @Override
                    public void run(){
                        byte[] serverResponse;

                        try{
                            while(true){
                                while(inputStream.available() == 0){}
                                
                                serverResponse = new byte[inputStream.available()];
                                inputStream.read(serverResponse);
                                TcpPacket receivedPacket = TcpPacket.parseFrom(serverResponse);
                                
                                if(receivedPacket.getType() == TcpPacket.PacketType.CONNECT){
                                    TcpPacket.ConnectPacket connectPacket = TcpPacket.ConnectPacket.parseFrom(serverResponse);
                                    textArea.append(connectPacket.getPlayer().getName() + " has entered the game.\n");
                                    updatePlayerList();
                                }else if(receivedPacket.getType() == TcpPacket.PacketType.DISCONNECT){
                                    TcpPacket.DisconnectPacket disconnectPacket = TcpPacket.DisconnectPacket.parseFrom(serverResponse);
                                    textArea.append(disconnectPacket.getPlayer().getName() + " has left the game.\n");
                                    updatePlayerList();
                                }else if(receivedPacket.getType() == TcpPacket.PacketType.CHAT){
                                    TcpPacket.ChatPacket chatPacket = TcpPacket.ChatPacket.parseFrom(serverResponse);
                                    textArea.append(chatPacket.getPlayer().getName() + ": " + chatPacket.getMessage() + "\n");
                                }
                            }
                        }catch(IOException e){
                            System.err.println(e.toString());
                        }catch(Exception e){
                            System.err.println(e.toString());
                        }
                    }
                }
            );
        
            statusThread.start();
        }
    }
}