package com.main.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Panel extends JPanel {
	public BufferedImage img;
	
	public Panel(String path) {
		try{
            this.img = ImageIO.read(Panel.class.getResource("/" + path));
        }catch(Exception e){}
    }
    
 	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.img, 0, 0, null);
	}
}