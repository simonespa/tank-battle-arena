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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.component.Desert;
import tankBattle.model.component.Glacier;
import tankBattle.model.component.Grassland;
import tankBattle.model.component.Ground;
import tankBattle.model.component.MetalArena;
import tankBattle.model.component.Tank;
import tankBattle.model.game.TankBattleGame;
import tankBattle.view.RootComponentView;

/**
 * L'oggetto istanziato da questa classe è un pannello di gioco predefinito
 * dall'utente. Al suo interno ci sono due ulteriori pannelli: il pannello di
 * stato e il pannello di gioco. Il pannello di stato serve a visualizzare lo
 * stato del tank e del gioco, mentre sul pannello di gioco verranno
 * visualizzati i componenti e tutta l'azione di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GamePanel extends JPanel {

	// il pannello di stato
	private GameStatePanel statePanel;
	// il pannello su cui verranno visualizzati i componenti
	private RootComponentView rootView;
	// la larghezza del pannello di gioco
	private static int width;
	// l'altezza del pannello di gioco
	private static int height;

	/**
	 * Crea un nuovo pannello di gioco, composto da un pannello di stato posto
	 * al di sopra della finestra e di un pannello sul quale verranno disegnati
	 * i componenti di gioco.
	 */
	public GamePanel(GUIWindowedController guiController) {
		super(new GridBagLayout(), true);
		width = 800;
		height = 600;
		setStatePanel(guiController);
		initGamePanel();
	}

	/**
	 * Restituisce il pannello di stato.
	 * 
	 * @return il pannello di stato al di sopra del pannello di gioco.
	 */
	public GameStatePanel getStatePanel() {
		return statePanel;
	}

	/**
	 * Restituisce il pannello di gioco.
	 * 
	 * @return il pannello di gioco di tipo
	 *         {@link tankBattle.view.RootComponentView}.
	 */
	public RootComponentView getRootComponentView() {
		return rootView;
	}

	/*
	 * 
	 */
	private void setStatePanel(GUIWindowedController guiController) {
		statePanel = new GameStatePanel(guiController);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(statePanel, constraints);
	}

	/*
	 * Setta il tipo di pavimento
	 */
	private void initGamePanel() {
		float gameHeight = (float) (height * 0.1);
		gameHeight = height - gameHeight;
		// istanzia il nodo principale della vista di gioco
		rootView = new RootComponentView(width, (int) gameHeight);
		// setta il constraint
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1;
		constraints.weighty = 0.9;
		constraints.gridx = 0;
		constraints.gridy = 1;
		// crea il pavimento scelto
		String map = TankBattleGame.getMap();
		Ground ground = null;
		// in base alla mappa prescelta viene creata la
		// relativa vista
		if (map.equals("Desert")) {
			ground = new Desert();
		} else if (map.equals("Glacier")) {
			ground = new Glacier();
		} else if (map.equals("Grassland")) {
			ground = new Grassland();
		} else if (map.equals("Metal Arena")) {
			ground = new MetalArena();
		}
		// setta il pavimento
		rootView.setGround(ground);
		// aggiunge il tank
		rootView.addTank(TankBattleGame.getTank());
		// aggiunge il pannello
		add(rootView, constraints);
	}

	/**
	 * 
	 * @param tank
	 */
	public void addTank(Tank tank) {
		rootView.addTank(tank);
	}

	/**
	 * 
	 * @param tank
	 */
	public void removeTank(Tank tank) {
		rootView.removeTank(tank);
	}

}