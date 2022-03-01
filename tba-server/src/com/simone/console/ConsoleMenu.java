package com.simone.console;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * &Egrave; la barra dei menù della console. Da qui &egrave; possibile gestire
 * il server mediante il men&ugrave; "Server", oppure &egrave; possibile
 * modificare l'aspetto della console mediante il men&ugrave; "Option".
 * 
 * @author Simone Spaccarotella
 * 
 */
final class ConsoleMenu extends JMenuBar {

	private JMenu server;
	private JMenuItem startServer;
	private JMenuItem stopServer;
	private JMenuItem close;

	private JMenu option;
	private JMenuItem background;
	private JMenuItem foreground;
	private JMenuItem font;

	/**
	 * Crea una nuova barra dei men&ugrave;.
	 */
	public ConsoleMenu() {
		super();
		setServerMenu();
		setOptionMenu();
		addMenu();
	}

	public void registerController(Controller controller) {
		startServer.addActionListener(controller);
		stopServer.addActionListener(controller);
		close.addActionListener(controller);

		background.addActionListener(controller);
		foreground.addActionListener(controller);
		font.addActionListener(controller);
	}

	/**
	 * Setta le impostazioni del menù "server".
	 */
	private void setServerMenu() {
		server = new JMenu("Server");
		server.setMnemonic(KeyEvent.VK_S);

		// setta le proprietà del comando "start"
		startServer = new JMenuItem("Start");
		startServer.setActionCommand("start");
		startServer.setToolTipText("Fa partire il server");
		startServer.setMnemonic(KeyEvent.VK_S);

		// setta le proprietà del comando "stop"
		stopServer = new JMenuItem("Stop");
		stopServer.setActionCommand("stop");
		stopServer.setToolTipText("Stoppa il server");
		stopServer.setMnemonic(KeyEvent.VK_T);
		stopServer.setEnabled(false);

		close = new JMenuItem("Close");
		close.setActionCommand("close");
		close.setToolTipText("Chiude la console e stoppando il server");
		close.setMnemonic(KeyEvent.VK_C);
	}

	/**
	 * Setta le impostazioni del menù "option".
	 */
	private void setOptionMenu() {
		option = new JMenu("Option");
		option.setMnemonic(KeyEvent.VK_O);

		// setta le proprietà del menù "background"
		background = new JMenuItem("Background...");
		background.setActionCommand("background");
		background.setToolTipText("Setta il colore di background");
		background.setMnemonic(KeyEvent.VK_B);

		// setta le proprietà del menù "foreground"
		foreground = new JMenuItem("Foreground...");
		foreground.setActionCommand("foreground");
		foreground.setToolTipText("Setta il colore del testo");
		foreground.setMnemonic(KeyEvent.VK_F);

		// setta le proprietà del menù "font"
		font = new JMenuItem("Font...");
		font.setActionCommand("font");
		font.setToolTipText("Setta il font del testo");
		font.setMnemonic(KeyEvent.VK_O);
	}

	/**
	 * Aggiunge i menù alla barra dei menù.
	 */
	private void addMenu() {
		server.add(startServer);
		server.add(stopServer);
		server.add(close);

		option.add(background);
		option.add(foreground);
		option.add(font);

		add(server);
		add(option);
	}

}