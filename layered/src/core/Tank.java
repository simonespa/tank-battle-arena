package core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Tank extends JPanel {

	int x;
	int y;
	Image view;
	private int width;
	private int height;
	
	public Tank() {
		super();
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, width, height);
		setOpaque(false);
		x = 100;
		y = 200;
		view = new ImageIcon("images/body/body-0.png").getImage();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(view, x, y, null);
	}
	
	public void moveDown() {
		y = y + 5;
	}

	public void moveDownLeft() {
		y = y + 5;
		x = x - 5;
	}

	public void moveDownRight() {
		y = y + 5;
		x = x + 5;
	}

	public void moveLeft() {
		x = x - 5;
	}

	public void moveRight() {
		x = x + 5;
	}

	public void moveUp() {
		y = y - 5;
	}

	public void moveUpLeft() {
		y = y - 5;
		x = x - 5;
	}

	public void moveUpRight() {
		y = y - 5;
		x = x + 5;
	}
		
}