package com.simone.console;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.simone.console.state.GUIState;

/**
 * La finestra all'interno della quale &egrave; presente una console dei
 * comandi.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class ConsoleWindow extends JFrame {

	/* il content pane della finestra */
	private ConsolePanel contentPane;
	/* la menù bar */
	private ConsoleMenu menuBar;
	/* il controller della grafica */
	private Controller controller;

	/**
	 * Crea una finestra all'interno della quale ci sarà una console che
	 * interpreterà i comandi dell'utente.
	 * 
	 * @param title
	 *            il titolo della console
	 */
	public ConsoleWindow(String title) {
		super(title);
		GUIState.init();
		setLookAndFeel();
		// istanzia e setta il content pane
		contentPane = new ConsolePanel();
		setContentPane(contentPane);
		// istanzia la menu bar passandogli il riferimento del controller
		menuBar = new ConsoleMenu();
		setJMenuBar(menuBar);
		// istanzia il controller e gli passa il riferimento alla finestra
		controller = new Controller(this);
		contentPane.registerController(controller);
		addWindowListener(controller);
		// imposta i parametri della finestra
		setSize(GUIState.getWidth(), GUIState.getHeight());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Restituisce un riferimento alla Console.
	 * 
	 * @return restituisce un oggetto di tipo
	 *         {@link com.simone.console.ConsoleWindow Console}.
	 */
	public Console getConsole() {
		return contentPane.getConsole();
	}
	
	/**
	 * Restituisce la linea di comando.
	 * 
	 * @return la linea di comando in cui impartire gli ordini all'interprete.
	 */
	public CommandLine getCommandLine() {
		return contentPane.getCommandLine();
	}
	
	/**
	 * Restituisce il tabbed pane.
	 * 
	 * @return il pannello in cui ci sono tutte le console.
	 */
	public JTabbedPane getTabbedPane() {
		return contentPane.getTabbedPane();
	}
	
	/**
	 * Restituisce il controller.
	 * 
	 * @return il controller dell'interfaccia grafica.
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Setta il look and feel dell'interfaccia grafica
	 */
	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(GUIState.getLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new ConsoleWindow("Console");
	}

}