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

import java.awt.GridLayout;

import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;

/**
 * &Egrave; il pannello posto nella parte superiore della finestra di gioco.
 * 
 * Questo pannello contiene al suo interno due pannelli: uno per la
 * visualizzazione dello stato del tank, e l'altro nel quale è possibile
 * scrivere dei messaggi da recapitare a tutti i componenti presenti nel gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GameStatePanel extends JPanel {

	/* il pannello che visualizza lo stato del tank */
	private TankPanel tankPanel;
	/* il pannello che contiene la chat */
	private ChatPanel chatPanel;

	/**
	 * Crea il pannello che compare nella parte superiore della finestra di
	 * gioco.
	 */
	public GameStatePanel(GUIWindowedController guiController) {
		super(new GridLayout(1, 2));
		tankPanel = new TankPanel(guiController);
		chatPanel = new ChatPanel(guiController);
		add(tankPanel);
		add(chatPanel);
	}

	/**
	 * Restituisce il pannello che visualizza lo stato del tank.
	 * 
	 * @return un pannello.
	 */
	public TankPanel getTankPanel() {
		return tankPanel;
	}

	/**
	 * Restituisce il pannello che contiene la chat.
	 * 
	 * @return un pannello.
	 */
	public ChatPanel getChatPanel() {
		return chatPanel;
	}

}