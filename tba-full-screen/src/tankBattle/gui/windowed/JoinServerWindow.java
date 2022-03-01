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

package tankBattle.gui.windowed;

import java.awt.Image;

import javax.swing.JFrame;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.utilities.ImageLoader;

/**
 * &Egrave; la finestra da cui è possibile cercare un server e agganciarsi ad
 * esso per una nuova sessione di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class JoinServerWindow extends JFrame {

	/* il pannello */
	private JoinServerPanel panel;
	/* la barra dei menù */
	private JoinServerMenuBar menuBar;

	/**
	 * Crea una finestra da cui è possibile scannerizzare la rete locale in
	 * cerca di un server.
	 * 
	 * @param frame
	 *            la finestra principale.
	 * @param controller
	 *            il controller della GUI.
	 */
	public JoinServerWindow(MainWindow frame, GUIWindowedController controller) {
		// setta il titolo e la configurazione grafica
		super(frame.getTitle() + " | join server", frame
				.getGraphicsConfiguration());
		// setta il nome
		setName("JoinServerWindow");
		// setta l'icona del gioco
		Image icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// registra la finestra al controller
		addWindowListener(controller);
		// istanzia il pannello e lo aggiunge alla finestra
		panel = new JoinServerPanel(controller);
		setContentPane(panel);
		// istanzia e setta la barra dei menù
		menuBar = new JoinServerMenuBar(controller);
		setJMenuBar(menuBar);
		// setta la dimensione iniziale della finestra
		setSize(600, 400);
		// impone che la finestra non sia ridimensionabile
		setResizable(false);
		// setta la posizione iniziale della finestra nello schermo
		setLocationRelativeTo(frame);
		// setta l'operazione di default alla chiusura
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// setta la visibilità
		setVisible(true);
	}

}
