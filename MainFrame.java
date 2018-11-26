/*
MainFrame.java contains the JFrame component specification and Game panel thread instantiation
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
	public JPanel mainFrame;
	public GamePanel game;
	public JPanel mainMenu;
	public JPanel instructions;
	public CardLayout cardLayout;
	
	public MainFrame(){
		this.initLayout();
		//this.setComponents();
		this.game = new GamePanel();
		this.setContentPane(game);
		this.endLayout();	
	}

	public void initLayout(){
		this.setSize(800,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Sumo Slam");
	}

	public void setComponents(){
		Container container = this.getContentPane();
		
		this.mainFrame = new JPanel();
		this.mainFrame.setPreferredSize(new Dimension(800,500));
		container.add(this.mainFrame, BorderLayout.CENTER);
	
		this.mainFrame.setLayout(new CardLayout());
		this.cardLayout = (CardLayout) this.mainFrame.getLayout();

		this.setMainMenu();
		this.setInstructions();
		this.initGame();
		this.mainFrame.add(this.mainMenu, "menu");
		this.mainFrame.add(this.instructions, "instructions");
		this.mainFrame.add(this.game, "game");

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void setMainMenu() {
		this.mainMenu = new JPanel();
		mainMenu.setBackground(Color.BLUE);
		
		JButton playButton = new JButton("start");
		JButton instructionsButton = new JButton("instructions");
		JButton exitButton = new JButton("exit");
		
		this.mainMenu.add(playButton);
		this.mainMenu.add(instructionsButton);
		this.mainMenu.add(exitButton);
	
		playButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "game");
				}
			}
		);
	
		instructionsButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "instructions");
				}
			}
		);
	
		exitButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			}
		);
	}

	public void setInstructions() {
		this.instructions = new JPanel();
		instructions.setBackground(Color.RED);
		
		JButton backButton = new JButton("Go back");
		this.instructions.add(backButton);
		
		backButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "menu");
				}
			}
		);
	}
	
	public void initGame() {
		this.game = new GamePanel();
		JButton backButton = new JButton("Go back");
		this.game.add(backButton);
	
		backButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "menu");
				}
			}
		);
	}	
	
	public void endLayout(){
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
