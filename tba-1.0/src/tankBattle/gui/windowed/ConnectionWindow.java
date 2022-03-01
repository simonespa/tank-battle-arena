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

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.utilities.ImageLoader;

/**
 * Crea una finestra dalla quale è possibile connettersi al server conoscendo a
 * priori il suo indirizzo e la sua porta.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ConnectionWindow extends JFrame {

	/* il riferimento del pannello */
	private ConnectionPanel panel;

	/**
	 * Crea la finestra con i parametri cablati al suo interno.
	 * 
	 * @param frame
	 *            il riferimento alla finestra principale.
	 * @param controller
	 *            il controller della GUI.
	 */
	public ConnectionWindow(MainWindow frame, GUIWindowedController controller) {
		super(frame.getTitle() + " | connecting...", frame
				.getGraphicsConfiguration());
		// setta il nome
		setName("ConnectionWindow");
		// setta l'icona del gioco
		Image icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// registra la finestra al controller
		addWindowListener(controller);
		// istanzia e setta il pannello
		setBackground(Color.BLACK);
		panel = new ConnectionPanel(controller);
		setContentPane(panel);
		// setta la dimensione iniziale della finestra
		setSize(435, 165);
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
