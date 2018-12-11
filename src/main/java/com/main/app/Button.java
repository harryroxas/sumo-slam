package com.main.app;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	public ImageIcon icon;
	
	public Button(String path) {
		this.icon = new ImageIcon(Button.class.getResource("/" + path));
		this.setIcon(this.icon);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setFocusPainted(false);
	}
}
