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

import java.awt.GraphicsConfiguration;
import java.awt.Image;

import javax.swing.JFrame;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * La finestra delle opzioni, che compare quando mettiamo il gioco in pausa. Da
 * questa finestra &egrave; possibile uscire dal gioco, ritornare al gioco,
 * settare la musica e gli effetti sonori ad ON/OFF.
 * 
 * @author Simone Spaccarotella
 */
public class OptionWindow extends JFrame {

	private OptionPanel optionPanel;

	/**
	 * Crea una nuova finestra delle opzioni.
	 * 
	 * @param controller
	 *            il controller della GUI.
	 * @param gConf
	 *            la configurazione grafica di default.
	 */
	public OptionWindow(GUIWindowedController controller,
			GraphicsConfiguration gConf) {
		super(TankBattleGame.getMainWindow().getTitle() + " | option", gConf);
		// setta il nome
		setName("OptionWindow");
		// setta l'icona del gioco
		Image icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// registra la finestra al controller
		addWindowListener(controller);
		// istanzia il pannello e lo aggiunge
		optionPanel = new OptionPanel(controller);
		setContentPane(optionPanel);
		// imposta gli altri parametri della finestra
		setSize(200, 400);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Setta la musica ad ON/OFF, in base allo stato precedente.
	 */
	public void setMusic() {
		optionPanel.setMusic();
	}

	/**
	 * Setta gli effetti sonori ad ON/OFF, in base allo stato precedente.
	 */
	public void setSoundEffect() {
		optionPanel.setSoundEffect();
	}

}
