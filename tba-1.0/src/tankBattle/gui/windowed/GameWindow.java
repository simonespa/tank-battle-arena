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
import java.io.PrintWriter;

import javax.swing.JFrame;

import tankBattle.controller.TankController;
import tankBattle.gui.GamePanel;
import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * Rappresenta la finestra di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GameWindow extends JFrame {

	/* il pannello della finestra */
	private GamePanel gamePanel;
	/* il controller di gioco */
	private TankController tankController;

	/**
	 * Crea una nuova finestra di gioco a tutto schermo.
	 * 
	 * @param controller
	 *            il controller dell'interfaccia grafica.
	 * @param conf
	 *            la configurazione grafica di default.
	 */
	public GameWindow(GUIWindowedController controller,
			GraphicsConfiguration conf, PrintWriter output) {
		super(TankBattleGame.getMainWindow().getTitle() + " | game", conf);
		// setta l'icona del gioco
		Image icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// istanzia e setta il pannello
		gamePanel = new GamePanel(controller);
		setContentPane(gamePanel);
		// istanzia e registra il controller di gioco
		tankController = new TankController(TankBattleGame.getTank(), output);
		addKeyListener(tankController);
		// setta il controller di gioco alla finestra

		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public TankController getTankController() {
		return tankController;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/**
	 * Stoppa il thread ridisegnatore.
	 */
	public void stopRepainter() {
		gamePanel.stopRepainter();
	}

}
