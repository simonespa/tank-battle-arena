package core;

import java.awt.Toolkit;

import javax.swing.JLayeredPane;

public class Layered extends JLayeredPane {

	private int width;
	private int height;
	
	public Layered() {
		super();
		width = Toolkit.getDefaultToolkit().getScreenSize().width;
		height = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, width, height);
	}
	
}
