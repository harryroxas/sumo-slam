import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class Button extends JButton {
	public BufferedImage img;
	public int x;
	public int y;
	
	public Button(int x, int y, String path) {
		this.x = x;
		this.y = y;
		try {
			this.img = ImageIO.read(new File(path));
		} catch (Exception e) {}		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.img, 0, 0, null);
	}
}
