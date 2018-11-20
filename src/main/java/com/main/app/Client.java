package com.main.app;

import com.google.protobuf.*;
import java.net.*;
import java.io.*;

import com.main.app.TcpPacketProtos.*;
import com.main.app.TcpPacketProtos.TcpPacket.ErrPacket;
import com.main.app.PlayerProtos.*;

import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "202.92.144.45";
    private static final int PORT = 80;
    private static Socket server;
    private static BufferedReader client_br;
    private static String lobbyId;
    private static OutputStream outputStream;
    private static DataInputStream inputStream;
    private static Player player;
    private static Scanner scanner = new Scanner(System.in);
    private static boolean connected;
    
    public static void main(String[] args) throws IOException {

        server = new Socket(SERVER_IP, PORT);
        client_br = new BufferedReader(new InputStreamReader(System.in));
        outputStream = server.getOutputStream();
        inputStream = new DataInputStream(server.getInputStream());

        Client client = new Client();

        if(server != null && outputStream != null && inputStream != null){
            boolean chooseAction = true;
            int choice;
            
            while(true){ 
                do{      
                    System.out.println("[1] Create Lobby");
                    System.out.println("[2] Connect to a Lobby");
                    System.out.println("[3] Exit");
                    System.out.print("Choice: ");
                    choice = Integer.parseInt(scanner.nextLine());
                    
                    if(choice > 0 && choice < 4) chooseAction = false;
                
                }while(chooseAction == true);

                byte[] serverResponse;
                TcpPacket receivedPacket;
                switch(choice){
                    
                    case 1: //Create Lobby
                        int maxPlayers = 0;

                        while(maxPlayers < 4){
                            System.out.print("Maximum players (at least 4) in the chat lobby: ");
                            maxPlayers = scanner.nextInt();
                        }

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
                        System.out.print("Enter lobby ID: ");
                        String id = scanner.next();
                        System.out.print("Enter player name: ");
                        String name = scanner.next();
                        player = Player.newBuilder().setName(name).build();

                        TcpPacket.ConnectPacket connectLobby = TcpPacket.ConnectPacket.newBuilder()
                            .setType(TcpPacket.PacketType.CONNECT)
                            .setPlayer(player)
                            .setLobbyId(id)
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

                if(!connected){
                    continue;
                }

                break;
            }
        }
    }
}