/*
MainFrame.java contains the JFrame component specification and Game panel thread instantiation
*/
package com.main.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.main.app.GamePanel;

import java.io.IOException;

public class MainFrame extends JFrame {
	public JPanel mainFrame;
	public JPanel gamePanel;
	public GamePanel game;
	public JPanel mainMenu;
	public JPanel options;
	public JPanel instructions;
	public CardLayout cardLayout;
	public JPanel chatGUI;
	public JTextArea textArea;
    public JTextField inputTextField;
	public JButton sendButton;
	public Client client;
	
	public MainFrame(){
		this.initLayout();
		this.setComponents();
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
		container.add(this.mainFrame);
	
		this.mainFrame.setLayout(new CardLayout());
		this.cardLayout = (CardLayout) this.mainFrame.getLayout();

		this.setMainMenu();
		this.setStartMenu();
		this.setInstructions();
		this.mainFrame.add(this.mainMenu, "menu");
		this.mainFrame.add(this.options, "options");
		this.mainFrame.add(this.instructions, "instructions");

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void setMainMenu() {
		this.mainMenu = new JPanel();
		this.mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
		this.mainMenu.setBackground(Color.BLUE);
		
		JLabel gameTitle = new JLabel("SUMO SLAM");
		JButton playButton = new JButton("START");
		JButton instructionsButton = new JButton("INSTRUCTIONS");
		JButton exitButton = new JButton("EXIT");

		gameTitle.setPreferredSize(new Dimension(200,200));
		gameTitle.setFont(new Font("Serif", Font.PLAIN, 72));
		playButton.setPreferredSize(new Dimension(100,100));
		playButton.setBackground(Color.BLUE);
		playButton.setFont(new Font("Serif", Font.PLAIN, 24));
		playButton.setBorderPainted(false);
		instructionsButton.setPreferredSize(new Dimension(100,100));
		instructionsButton.setBackground(Color.BLUE);
		instructionsButton.setFont(new Font("Serif", Font.PLAIN, 24));
		instructionsButton.setBorderPainted(false);
		exitButton.setPreferredSize(new Dimension(100,100));
		exitButton.setBackground(Color.BLUE);
		exitButton.setFont(new Font("Serif", Font.PLAIN, 24));
		exitButton.setBorderPainted(false);

		this.mainMenu.add(gameTitle);
		this.mainMenu.add(playButton);
		this.mainMenu.add(Box.createVerticalStrut(25));
		this.mainMenu.add(instructionsButton);
		this.mainMenu.add(Box.createVerticalStrut(25));
		this.mainMenu.add(exitButton);
		gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		instructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		playButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "options");
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

	public void setStartMenu(){
		this.options = new JPanel();
		this.options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
		this.options.setBackground(Color.BLUE);

		JButton createButton = new JButton("CREATE A LOBBY");
		JButton connectButton = new JButton("CONNECT TO A LOBBY");
		JButton backButton = new JButton("BACK");

		createButton.setPreferredSize(new Dimension(100,100));
		createButton.setBackground(Color.BLUE);
		createButton.setFont(new Font("Serif", Font.PLAIN, 24));
		createButton.setBorderPainted(false);
		createButton.setMargin(new Insets(0,0,0,0));
		connectButton.setPreferredSize(new Dimension(100,100));
		connectButton.setBackground(Color.BLUE);
		connectButton.setFont(new Font("Serif", Font.PLAIN, 24));
		connectButton.setBorderPainted(false);
		connectButton.setMargin(new Insets(0,0,0,0));
		backButton.setPreferredSize(new Dimension(100,100));
		backButton.setBackground(Color.BLUE);
		backButton.setFont(new Font("Serif", Font.PLAIN, 24));
		backButton.setBorderPainted(false);
		backButton.setMargin(new Insets(0,0,0,0));
		
		this.options.add(Box.createVerticalStrut(100));
		this.options.add(createButton);
		this.options.add(Box.createVerticalStrut(50));
		this.options.add(connectButton);
		this.options.add(Box.createVerticalStrut(50));
		this.options.add(backButton);

		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		createButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					String num = JOptionPane.showInputDialog(options, "Enter max number of players");
					String player = JOptionPane.showInputDialog(options, "Enter your name");
					if(num != null && player != null){
						Integer maxPlayers = Integer.parseInt(num);
						try{
							client = new Client(1, maxPlayers, player);

							initGame();
							cardLayout.show(mainFrame, "game");
						}catch(IOException err){}
					}
				}
			}
		);

		connectButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String player = JOptionPane.showInputDialog(options, "Enter your name");
					String lobbyID = JOptionPane.showInputDialog(options, "Enter lobby id");
					if(player != null && lobbyID != null){
						try{
							client = new Client(2, lobbyID, player);

							initGame();
							cardLayout.show(mainFrame, "game");
						}catch(IOException err){}
					}
				}
			}
		);

		backButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.show(mainFrame, "menu");
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

	public void buildChatGUI(){
		this.chatGUI = new JPanel();
		this.chatGUI.setLayout(new BoxLayout(this.chatGUI, BoxLayout.Y_AXIS));

		this.textArea = new JTextArea(30, 10);
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		this.chatGUI.add(new JScrollPane(textArea), BorderLayout.CENTER);
		client.setTextArea(textArea);

		Box box = Box.createHorizontalBox();
		this.chatGUI.add(box, BorderLayout.SOUTH);
		this.inputTextField = new JTextField();
		this.sendButton = new JButton("Send");
		box.add(inputTextField);
		box.add(sendButton);

		ActionListener sendListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = inputTextField.getText();
				if (str != null && str.trim().length() > 0){
					try{
						client.sendMessage(str);
					}catch(IOException err){}
				}
				inputTextField.selectAll();
				inputTextField.requestFocus();
				inputTextField.setText("");
			}
		};
		inputTextField.addActionListener(sendListener);
		sendButton.addActionListener(sendListener);
	}
	
	public void initGame() {
		this.gamePanel = new GamePanel();
		this.gamePanel.setLayout(new BoxLayout(this.gamePanel, BoxLayout.X_AXIS));

		buildChatGUI();

		this.game = new GamePanel();
		this.game.setPreferredSize(new Dimension(600, 500));
		JButton backButton = new JButton("EXIT GAME");
		this.game.add(backButton);
	
		backButton.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try{
						client.sendMessage("exit");
						cardLayout.show(mainFrame, "menu");
					}catch(IOException err){}
				}
			}
		);

		this.gamePanel.add(this.chatGUI);
		this.gamePanel.add(this.game);
		this.mainFrame.add(this.gamePanel, "game");
	}	
	
	public void endLayout(){
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
