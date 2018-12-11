/*
MainFrame.java contains the JFrame component specification and Game panel thread instantiation
*/
package com.main.app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.main.app.GamePanel;
import com.main.app.GameServer;

import java.io.IOException;
import java.lang.Exception;

public class MainFrame extends JFrame {
	private static final String SERVER_IP = "202.92.144.45";
	public JPanel mainFrame;
	public JPanel gamePanel;
	public GameServer gameServer;
	public GamePanel game;
	public Panel mainMenu;
	public Panel options;
	public Panel instructions;
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
		this.mainMenu = new Panel("mainMenu.png");
		this.mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));
	
		Button playButton = new Button("startButton.png");
		Button instructionsButton = new Button("instructionsButton.png");
		Button exitButton = new Button("exitButton.png");

		this.mainMenu.add(Box.createVerticalStrut(200));
		this.mainMenu.add(playButton);
		this.mainMenu.add(Box.createVerticalStrut(25));
		this.mainMenu.add(instructionsButton);
		this.mainMenu.add(Box.createVerticalStrut(25));
		this.mainMenu.add(exitButton);

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
		this.options = new Panel("mainMenu.png");
		this.options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

		Button createButton = new Button("createButton.png");
		Button connectButton = new Button("connectButton.png");
		Button backButton = new Button("backButton.png");

		this.options.add(Box.createVerticalStrut(200));
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
							
							initGame(client.getPlayerName());
							gameServer = new GameServer(maxPlayers);
							cardLayout.show(mainFrame, "game");
						}catch(IOException err){

						}catch(Exception er){}
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

							initGame(client.getPlayerName());
							cardLayout.show(mainFrame, "game");
						}catch(IOException err){

						}catch(Exception er){}
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
		this.instructions = new Panel("instructions.png");
		this.instructions.setLayout(new BoxLayout(instructions, BoxLayout.Y_AXIS));
		
		Button backButton = new Button("goBackButton.png");

		this.instructions.add(Box.createVerticalStrut(420));
		this.instructions.add(backButton);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
	
	public void initGame(String playerName) throws Exception {
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new BoxLayout(this.gamePanel, BoxLayout.X_AXIS));

		buildChatGUI();

		String ip = JOptionPane.showInputDialog(options, "Enter your IP Address");

		this.game = new GamePanel(ip, playerName);
		this.game.setPreferredSize(new Dimension(600, 500));
		Button backButton = new Button("exitGame.png");
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

		this.game.requestFocus();
	}	
	
	public void endLayout(){
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
