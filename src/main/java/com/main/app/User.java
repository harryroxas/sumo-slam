package com.main.app;

import java.net.InetAddress;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class User extends Sprite {
    public static final int NORMAL_SPEED = 10;
    private InetAddress address;
    private int port;
    private String name;

    public int xPosition;
    public int yPosition;
    public float xCenter;
    public float yCenter;
	public int playerNo;
	public int speed;
    public int noOfWins;
    public boolean isDead;
    public BufferedImage img;

	public User(InetAddress address, int port, String name) {
        super(300, 300);
        this.address = address;
        this.port = port;
        this.name = name;
        this.speed = NORMAL_SPEED;
        this.noOfWins = 0;
        this.isDead = false;
        try{
            this.img = ImageIO.read(User.class.getResource("/sumoWrestler" + this.playerNo + ".png"));
        }catch(Exception e){}
        this.setImg(this.img);
        this.width = 60;
        this.height = 60;
    }

    public User(int playerNo, int x, int y) {
        super(x, y);
        this.playerNo = playerNo;
        this.speed = NORMAL_SPEED;
        this.noOfWins = 0;
        this.isDead = false;
        try{
            this.img = ImageIO.read(User.class.getResource("/sumoWrestler" + this.playerNo + ".png"));
        }catch(Exception e){}
        this.setImg(this.img);
        this.width = 60;
        this.height = 60;
    }

	public void resetPosition(){
		this.xPosition = 0;
		this.yPosition = 0;
	}
	
	public void moveX(int xPosition){
		this.xPosition = xPosition;
	}
	
	public void moveY(int yPosition){
		this.yPosition = yPosition;
	}

	public void setCenter() {
		this.xCenter = this.getX()+(this.width/2);
		this.yCenter = this.getY()+(this.height-(this.height/3));
	}
	
	public void checkIfDead() {
		
	}
	
    public void move() {
        int newX = this.getX() + this.xPosition;
        int newY = this.getY() + this.yPosition;
        this.setX(newX);
        this.setY(newY);
        this.setCenter();
        System.out.print(this.x);
        System.out.print("-");
        System.out.println(this.xCenter);        
        System.out.print(this.y);
        System.out.print("-");
        System.out.println(this.yCenter);
    }

    public void speedUp() {
		this.speed = this.speed + 1;
    }

	public void slowDown() {
        if(this.speed > 1) {
            this.speed = this.speed - 1;
        }
	}

    public void resetSpeed() {
        this.speed = NORMAL_SPEED;
    }

    public void resetPlayer() {
        this.resetSpeed();
        this.isDead = false;
    }

    public void collidesWith(User others) {
    	if(this.getMoveX() == 0) {
        	int minusY = others.getMoveY() + this.getMoveY();
            others.moveY(minusY);
    	}else {
        	int minusX = others.getMoveX() + this.getMoveX();
        	others.moveX(minusX);
    	}
        others.move();
        others.resetPosition();
    }
    
    public void playerWins() {
        this.noOfWins = this.noOfWins + 1;
    }

    public void playerDies() {
        this.isDead = true;
    }

    public boolean getPlayerStatus() {
        return this.isDead;
    }
    
	public int getMoveX(){
		return this.xPosition;
	}
	
	public int getMoveY(){
		return this.yPosition;
    }
    
    public InetAddress getAddress(){
        return this.address;
    }

    public int getPort(){
        return this.port;
    }

    public String getName(){
        return this.name;
    }
	
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	
	public Point2D getCenter() {
		return new Point2D.Float(this.xCenter, this.yCenter);
    }
    
    public String toString(){
		String retval="";
		retval+="PLAYER ";
		retval+=name+" ";
		retval+=this.getX()+" ";
		retval+=this.getY();
		return retval;
	}
}

// References: zetcode.com/tutorials/javagamestutorial
