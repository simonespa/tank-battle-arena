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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * &Egrave; la parte di pannello in alto a sinistra, nella quale &egrave;
 * presente un immagine di sfondo e lo stato del gioco (il nome del player, e
 * l'energia).
 * 
 * @author Simone Spaccarotella
 * 
 */
public class TankPanel extends JPanel {

	private JLabel tankName;
	private JLabel tankEnergy;
	private Image banner;

	/**
	 * Crea un nuovo pannello, a cui viene passato il controller di gioco.
	 * 
	 * @param guiController
	 *            il controller della GUI.
	 */
	public TankPanel(GUIWindowedController guiController) {
		super(new GridLayout(2, 1));
		banner = ImageLoader.getInstance().getBanner();
		initAndSetLabel();
		add(tankName);
		add(tankEnergy);
		setFocusable(false);
	}

	/**
	 * Inizializza e setta le label di questo pannello.
	 */
	private void initAndSetLabel() {
		tankName = new JLabel(TankBattleGame.getTankName());
		tankEnergy = new JLabel(String.valueOf(TankBattleGame.getTank()
				.getEnergy()));
		TankBattleGame.registerEnergyObserver(tankEnergy);
		Font font = new Font("Verdana", Font.BOLD, 24);
		tankName.setFont(font);
		tankName.setForeground(Color.WHITE);
		tankEnergy.setFont(font);
		tankEnergy.setForeground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(banner, 0, 0, getWidth(), getHeight(), null);
	}

}
