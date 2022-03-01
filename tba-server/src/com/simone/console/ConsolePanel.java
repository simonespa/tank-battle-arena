package com.simone.console;

import java.awt.BorderLayout;
import java.awt.ScrollPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class ConsolePanel extends JPanel {

	private CommandLine commandLine;
	private Console console;
	private JTabbedPane tabbedPane;
	private Controller controller;
	
	/**
	 * Crea un nuovo pannello nel quale sono contenute la linea di comando
	 * e le console di output organizzate in tab.
	 */
	public ConsolePanel() {
		super(new BorderLayout());
		// istanzia la command line e lo registra al controller
		commandLine = new CommandLine();
		// istanzia la console e la inserisce in un scroll pane
		console = new Console();
		console.setName("Server");
		// istanzia il tabbed pane e aggiunge la console
		tabbedPane = new JTabbedPane();
		addConsole(console);
		// aggiunge il tutto al pannello
		add(commandLine, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
	/**
	 * Registra il controller.
	 * 
	 * @param controller l'ascoltatore della GUI.
	 */
	public void registerController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Restituisce la console.
	 * 
	 * @return un oggetto di tipo Console
	 */
	public Console getConsole() {
		return console;
	}
	
	/**
	 * Restituisce la linea di comando.
	 * 
	 * @return un oggetto di tipo {@link com.simone.console.CommandLine}
	 */
	public CommandLine getCommandLine() {
		return commandLine;
	}
	
	/**
	 * Restituisce il pannello con i tab.
	 * 
	 * @return il pannello in cui ci sono tutte le console.
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	/**
	 * Stampa la stringa passata in input sulla console.
	 * 
	 * @param message il messaggio da stampare.
	 */
	public void print(String message) {
		
	}
	
	/**
	 * Aggiunge una nuova console al pannello.
	 * 
	 * @param console la console di output.
	 */
	public void addConsole(Console console) {
		tabbedPane.add(console);
		int index = tabbedPane.getTabCount() - 1;
		tabbedPane.setTitleAt(index, console.getName());
		
	}
	
}
