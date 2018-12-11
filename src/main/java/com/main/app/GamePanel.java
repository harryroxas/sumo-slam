package com.main.app;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final int PORT = 4444;
    public static final int STAGE_WIDTH = 540;
	public static final int STAGE_HEIGHT = 240;

	Thread t = new Thread(this);
	String name;
	String server = "localhost";
	boolean connected = false;
	DatagramSocket socket = new DatagramSocket();
	String serverData;

	public ArrayList<User> players = new ArrayList<User>();
	public User player;
	private boolean gameDone;
	Ellipse2D stage;

	public GamePanel(String server, String name) throws Exception {
		this.server = server;
		this.name = name;
		socket.setSoTimeout(100);

		//this.offscreen=(BufferedImage)this.createImage(640, 480);

		this.setLayout();
		this.addComponents();
		this.addKeyListener(this);	
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				requestFocus();
			}
		});

		t.start();
	}

	public void send(String msg){
		try{
			byte[] buf = msg.getBytes();
        	InetAddress address = InetAddress.getByName(server);
        	DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        	socket.send(packet);
        }catch(Exception e){}
		
	}

	public void run(){
		while(true){
			try{
				Thread.sleep(1);
			}catch(Exception ioe){}
						
			byte[] buf = new byte[256];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try{
     			socket.receive(packet);
			}catch(Exception ioe){}
			
			serverData=new String(buf);
			serverData=serverData.trim();
			
			//if (!serverData.equals("")){
			//	System.out.println("Server Data:" +serverData);
			//}
 
			if (!connected && serverData.startsWith("CONNECTED")){
				connected=true;
				System.out.println("Connected.");
			}else if (!connected){
				System.out.println("Connecting..");				
				send("CONNECT "+name);
			}else if (connected){
				//offscreen.getGraphics().clearRect(0, 0, 640, 480);
				players.clear();
				if (serverData.startsWith("PLAYER")){
					String[] playersInfo = serverData.split(":");
					for (int i=0;i<playersInfo.length;i++){
						String[] playerInfo = playersInfo[i].split(" ");
						String pname =playerInfo[1];
						int x = Integer.parseInt(playerInfo[2]);
						int y = Integer.parseInt(playerInfo[3]);
						//draw on the offscreen image
						User user = new User(i+1, x, y);
						players.add(user);
						this.repaint();					
					}
				}			
			}			
		}
	}
		
	public void setLayout(){
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void addComponents(){
		this.stage = new Ellipse2D.Float(10,210,STAGE_WIDTH, STAGE_HEIGHT);
		this.player  = new User(1, 300, 300);
	}

	public void checkCollisions() {
	    Rectangle active = this.player.getBounds();
	    for (User player : players) {
	        Rectangle others = player.getBounds();
	        if (active.intersects(others)) {
	        	System.out.println("COLLIDES");
	        	this.player.collidesWith(player);
				this.checkIfDead(player);
	        	this.repaint();
	        }
	    }
	}
	
	public void checkIfDead(User player) {
	    Point2D active = player.getCenter();
	    //for (Players player : players) {
	        if (!stage.contains(active)) {
	        	System.out.println("DIES");
	        	player.playerDies();
	        	this.repaint();
	        }
	    //}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.draw (this.stage);
		for(User player : players){
			g2.drawImage(player.getImg(),player.getX(),player.getY(),this);
		}
	}

	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode()==KeyEvent.VK_UP){
			this.player.moveY(-10);
		}
		if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			this.player.moveX(-10);
		}
		if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			this.player.moveY(10);
		}
		if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			this.player.moveX(10);
		}
		this.player.move();
		this.checkIfDead(this.player);
		this.checkCollisions();
		this.send("PLAYER "+name+" "+player.getX()+" "+player.getY());
	}

	public void keyTyped(KeyEvent ke){}

	public void keyReleased(KeyEvent ke){
		this.player.resetPosition();
	}
}
