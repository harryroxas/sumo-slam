package com.main.app;

import java.awt.image.BufferedImage;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
	public int x;
	public int y;
	public int width;
	public int height;
	public BufferedImage img;

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setImg(BufferedImage img){
		this.img = img;
	}
	
	public void setImgSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Image getImg() {
		return this.img;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
}

// References: zetcode.com/tutorials/javagamestutorial