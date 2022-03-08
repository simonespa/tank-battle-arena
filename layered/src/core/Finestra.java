package core;

import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Finestra extends JFrame {
	
	private JLayeredPane layered;
	
	private Tank tank;
	private Terreno terreno;
	
	private KeyListener listener;
	
	public Finestra() {
		super();
		terreno = new Terreno();
		tank = new Tank();
		layered = new Layered();
		layered.add(terreno, new Integer(0));
		layered.add(tank, new Integer(1));
		setLayeredPane(layered);
		listener = new Controller(tank);
		addKeyListener(listener);
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Finestra();
	}
	
}