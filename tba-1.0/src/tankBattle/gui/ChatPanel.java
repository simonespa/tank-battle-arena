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

package tankBattle.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * Questa classe rappresenta il pannello nel quale è posizionata la chat.
 * 
 * Grazie a questo pannello è possibile visualizzare i messaggi ricevuti ed
 * inviarne ulteriori.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class ChatPanel extends JPanel {

	/* il font utilizzato per il testo */
	private Font font;
	/* la cornice superiore dell'area di testo */
	private JPanel northernPanel;
	/* la cornice a destra dell'area di testo */
	private JPanel easternPanel;
	/* la cornice a sinistra dell'area di testo */
	private JPanel westernPanel;
	/* la console nella quale compariranno i messaggi inviati */
	private JTextArea console;
	/* il pannello che contiene la console di visualizzazione */
	private JScrollPane consolePanel;
	/* il campo di testo nel quale vengono scritti i messaggi */
	private JTextField sender;
	/* l'immagine di sfondo del pannello */
	private Image banner;
	/* il controller della GUI */
	private GUIWindowedController guiController;

	/**
	 * Crea il pannello che conterrà la chat.
	 * 
	 * @param guiController
	 *            il controller della GUI.
	 */
	public ChatPanel(GUIWindowedController guiController) {
		super(new BorderLayout());
		this.guiController = guiController;
		banner = ImageLoader.getInstance().getBanner();
		setComponents();
		addComponents();
	}

	/**
	 * Abilita la richiesta del focus, e lo richiede al S.O.
	 */
	@Override
	public void requestFocus() {
		sender.setFocusable(true);
		sender.requestFocus();
	}

	/**
	 * Svuota il campo di testo e inserisce il messaggio nella console.
	 * 
	 * @return il testo che verrà inviato ai client.
	 */
	public String flush() {
		String text = TankBattleGame.getTankName() + ":" + sender.getText();
		console.append("IO: " + sender.getText()
				+ System.getProperty("line.separator"));
		sender.setText("");
		sender.setFocusable(false);
		return text;
	}

	/**
	 * Stampa il messaggio ricevuto sulla console.
	 * 
	 * @param message
	 *            il messaggio ricevuto.
	 */
	public void printConsole(String message) {
		String text = message.split("@")[0];
		String[] split = text.split(":");
		console.append(split[1] + ": " + split[2]
				+ System.getProperty("line.separator"));
	}

	/**
	 * Istanzia i componenti.
	 */
	private void setComponents() {
		// istanzia ed imposta gli attributi della cornice
		northernPanel = new JPanel();
		northernPanel.setOpaque(false);
		northernPanel.setFocusable(false);
		easternPanel = new JPanel();
		easternPanel.setOpaque(false);
		easternPanel.setFocusable(false);
		westernPanel = new JPanel();
		westernPanel.setOpaque(false);
		westernPanel.setFocusable(false);
		// crea il font predefinito
		font = new Font("Courier", Font.PLAIN, 16);
		// istanzia ed imposta le proprietà della console
		console = new JTextArea();
		console.setFont(font);
		console.setEditable(false);
		console.setBackground(Color.GRAY.brighter());
		console.setFocusable(false);
		// aggiunge la text area ad un pannello scrollabile
		consolePanel = new JScrollPane(console);
		consolePanel.setFocusable(false);
		// istanzia il campo di testo in cui scrivere messaggi
		sender = new JTextField();
		sender.setFont(font);
		sender.setOpaque(false);
		sender.addKeyListener(guiController);
		sender.setFocusable(false);
	}

	/**
	 * Aggiunge i componenti al pannello.
	 */
	private void addComponents() {
		add(northernPanel, BorderLayout.NORTH);
		add(easternPanel, BorderLayout.EAST);
		add(westernPanel, BorderLayout.WEST);
		add(consolePanel, BorderLayout.CENTER);
		add(sender, BorderLayout.SOUTH);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(banner, 0, 0, getWidth(), getHeight(), null);
	}

}
