package com.simone.console;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.simone.console.state.GUIState;

/**
 * &Egrave; la console del programma, mediante la quale &egrave; possibile
 * impartire dei comandi all'interprete;
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Console extends JScrollPane {

	private JTextArea console;
	private String user;
	private String prompt;
	
	/**
	 * Crea una nuova console scrollabile.
	 */
	public Console() {
		super();
		initState();
	}

	/**
	 * Inizializza lo stato della console.
	 */
	private void initState() {
		// inizializza i campi privati
		user = System.getenv("USERNAME");
		prompt = user + "-$: ";
		// setta la console
		console = new JTextArea();
		console.setBackground(GUIState.getBackgroundColor());
		console.setForeground(GUIState.getForegroundColor());
		console.setFont(GUIState.getFont());
		console.setCaretColor(GUIState.getForegroundColor());
		console.setEditable(false);
		console.setFocusable(false);
		console.append(prompt);
		// setta lo scroll pane
		setBackground(GUIState.getBackgroundColor());
		setForeground(GUIState.getForegroundColor());
		setFont(GUIState.getFont());
		setFocusable(false);
	}
	
	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		console.setBackground(bg);
	}
	
	@Override
	public Color getBackground() {
		return console.getBackground();
	}
	
	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		console.setForeground(fg);
	}
	
	@Override
	public Color getForeground() {
		return console.getForeground();
	}
	
	@Override
	public void setFont(Font font) {
		console.setFont(font);
	}
	
	@Override
	public Font getFont() {
		return console.getFont();
	}
	
	/**
	 * Setta il colore del cursore della console.
	 * 
	 * @param color il colore che si vuole impostare.
	 */
	public void setCaretColor(Color color) {
		console.setCaretColor(color);
	}
	
	/**
	 * Restituisce il colore del cursore della console.
	 * @return
	 */
	public Color getCaretColor() {
		return console.getCaretColor();
	}
	
	/**
	 * Scrive sulla console.
	 * 
	 * @param message il messaggio da stampare.
	 */
	public void append(String message) {
		console.append(message);
		console.append(prompt);
	}
	
	/**
	 * Pulisce la console.
	 */
	public void clear() {
		console.setText("");
		console.append(prompt);
	}

}
