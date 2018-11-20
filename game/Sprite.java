// Note: In progress

import javax.swing.JPanel;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Rectangle;

public class Sprite extends JPanel {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	private BufferedImage img;
	
	/*
	private BufferedImage imgLeft;
	private BufferedImage imgRight;
	*/

	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Setters
	public void setImg(String path){
		try {
			this.img = ImageIO.read(new File(path));
			this.width = img.getWidth();
			this.height = img.getHeight();
		} catch (Exception e) {}

	/*
		String[] filename = path.split("\\.");
		try {
			this.imgLeft = ImageIO.read(new File(filename[0] + "Left." + filename[1]));
			this.imgRight = ImageIO.read(new File(filename[0] + "Right." + filename[1]));
		} catch (Exception e) {}
	*/

		this.setOpaque(false);
		this.setSize(width, height);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	// Getters
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, this.x, this.y, null);
	}
}

// References: zetcode.com/tutorials/javagamestutorial
