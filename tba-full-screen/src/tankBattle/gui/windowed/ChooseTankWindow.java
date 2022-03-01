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
 * La finestra da cui è possibile scegliere il nome e il colore del tank.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ChooseTankWindow extends JFrame {

	/* il pannello della finestra */
	private ChooseTankPanel panel;

	/**
	 * Crea una dialog box.
	 * 
	 * @param controller
	 *            il controller di gioco.
	 */
	public ChooseTankWindow(MainWindow frame, GUIWindowedController controller) {
		// setta il titolo e la configurazione grafica
		super(frame.getTitle() + " | start server", frame
				.getGraphicsConfiguration());
		// setta il nome
		setName("StartServerWindow");
		// setta l'icona del gioco
		Image icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// registra la finestra al controller
		addWindowListener(controller);
		// istanzia il pannello e lo aggiunge alla finestra
		panel = new ChooseTankPanel(controller);
		setContentPane(panel);
		// imposta gli altri parametri della finestra
		setResizable(false);
		setSize(430, 300);
		setLocationRelativeTo(frame);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void setButtonColor(Color color) {
		panel.setColorChoosed(color);
	}

}
