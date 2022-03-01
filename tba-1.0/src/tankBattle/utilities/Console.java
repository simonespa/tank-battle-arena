/*
 * Copyright © 2008 Simone Spaccarotella
 * 
 * This file is part of "Tank Battle Arena" game.
 * 
 * Tank Battle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Tank Battle Arena is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Tank Battle.  If not, see <http://www.gnu.org/licenses/>. 
 * 
 */

package tankBattle.utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Questa classe rappresenta la console di debug del programma. Può essere
 * utilizzata per effettuare il debug delle istruzioni che vengono eseguite, sia
 * per quanto riguarda la rete che per quanto riguarda la scrittura del codice.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Console extends JFrame {

	private JScrollPane pannello;
	private JTextArea console;
	private static boolean visibility = false;
	private int counter;

	/**
	 * Crea la console con un titolo.
	 * 
	 * @param title
	 *            il titolo della console.
	 */
	public Console(String title) {
		this(title, null, false);
	}

	/**
	 * Crea la console con un titolo e specifica che essa dovrà essere la
	 * finestra principale, e di conseguenza alla sua chiusura, verrà terminato
	 * l'intero programma. Per default questo flag è impostato a false.
	 * 
	 * @param title
	 *            il titolo.
	 * @param main
	 *            true se questa finestra deve essere quella principale del
	 *            programma
	 */
	public Console(String title, boolean main) {
		this(title, null, main);
	}

	/**
	 * Crea la console con un titolo ed un controller, ovvero un gestore degli
	 * eventi scatenati dalla grafica.
	 * 
	 * @param title
	 *            il titolo della console.
	 * @param controller
	 *            il gestore degli eventi.
	 */
	public Console(String title, KeyAdapter controller) {
		this(title, controller, false);
	}

	/**
	 * Crea la console con un titolo, un controller e specifica se la finestra
	 * dovrà essere quella principale del programma.
	 * 
	 * @param title
	 *            il titolo della finestra.
	 * @param controller
	 *            il controller.
	 * @param main
	 *            true se questa finestra deve essere quella principale del
	 *            programma.
	 */
	public Console(String title, KeyAdapter controller, boolean main) {
		super(title);
		counter = 0;
		// setta i parametri della console
		console = new JTextArea();
		Font font = new Font("Verdana", Font.PLAIN, 13);
		console.setFont(font);
		console.setEditable(false);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.GREEN);
		// istanzia il pannello e lo aggiunge alla finestra
		pannello = new JScrollPane(console);
		setContentPane(pannello);
		// aggiunge il controller
		addKeyListener(controller);
		// se è la finestra principale chiude il programma
		if (main) {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			// altrimenti chiude solo la finestra corrente
		} else {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		setSize(500, 300);
		setLocationRelativeTo(null);
		setVisible(visibility);
	}

	/**
	 * Setta la trasparenza della console. Se {@code visible} sarà {@code true}
	 * allora la finestra risulterà invisibile.
	 * 
	 * @param visible
	 *            true per rendere la finestra visibile
	 */
	public static void setConsoleVisible(boolean visible) {
		Console.visibility = visible;
	}

	/**
	 * Permette di stampare sulla console i messaggi indirizzati allo stream di
	 * output.
	 * 
	 * @param message
	 *            il messaggio da stampare.
	 */
	public void printOut(String message) {
		counter++;
		console.append(counter + ") " + ": " + message + "\n");
	}

	/**
	 * Permette di stampare sulla console i messaggi indirizzati allo stream di
	 * errore.
	 * 
	 * @param message
	 *            il messaggio da stampare.
	 */
	public void printErr(String message) {
		counter++;
		console.append(counter + ") " + ": [ERROR] " + message + "\n");
	}

	/**
	 * Come il metodo {@link #printOut(String)}, con la differenza che possiamo
	 * specificare un'etichetta che identifica l'autore di questo messaggio.
	 * 
	 * @param owner
	 *            l'etichetta.
	 * @param message
	 *            il messaggio.
	 */
	public synchronized void printOut(String owner, String message) {
		counter++;
		console.append(counter + ") " + owner + ": " + message + "\n");
	}

	/**
	 * Come il metodo {@link #printErr(String)}, con la differenza che possiamo
	 * specificare un'etichetta che identifica l'autore di questo messaggio.
	 * 
	 * @param owner
	 *            l'etichetta.
	 * @param message
	 *            il messaggio.
	 */
	public synchronized void printErr(String owner, String message) {
		counter++;
		console.append(counter + ") " + owner + ": [ERROR] " + message + "\n");
	}

}