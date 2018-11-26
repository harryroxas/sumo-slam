import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;

public class GamePanel extends JPanel implements KeyListener{
    public static final int STAGE_WIDTH = 540;
    public static final int STAGE_HEIGHT = 240;

	public Player player;
	public Player player2;
	private boolean gameDone;

	public GamePanel(){
		this.setLayout();
		this.addComponents();
		this.addKeyListener(this);	
	}
		
	public void setLayout(){
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	public void addComponents(){
		this.player  = new Player(1, 300, 300);
		this.player2 = new Player(2, 100, 100);
	}

	public void checkCollisions() {
	    Rectangle active = this.player.getBounds();
	    //for (Players player : players) {
	        Rectangle others = this.player2.getBounds();
	        if (active.intersects(others)) {
	        	System.out.println("COLLIDES");
	        	this.player.collidesWith(player2);
	        	this.repaint();
	        }
	    //}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(this.player.getImg(),this.player.getX(),this.player.getY(),this);
		g2.drawImage(this.player2.getImg(),this.player2.getX(),this.player2.getY(),this);
		Ellipse2D stage = new Ellipse2D.Float(200,100,STAGE_WIDTH, STAGE_HEIGHT);
		g2.draw (stage);
	}

	public void keyPressed(KeyEvent ke){
		if(ke.getKeyCode()==KeyEvent.VK_UP){
			this.player.moveY(-10);
			System.out.println("UP");
		}
		if(ke.getKeyCode()==KeyEvent.VK_LEFT){
			this.player.moveX(-10);
			System.out.println("LEFT");
		}

		if(ke.getKeyCode()==KeyEvent.VK_DOWN){
			this.player.moveY(10);
			System.out.println("DOWN");
		}
		if(ke.getKeyCode()==KeyEvent.VK_RIGHT){
			this.player.moveX(10);
			System.out.println("RIGHT");
		}

		this.player.move();
		this.checkCollisions();
		this.repaint();
	}

	public void keyTyped(KeyEvent ke){}

	public void keyReleased(KeyEvent ke){
		this.player.resetPosition();
	}
}
