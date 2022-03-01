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
 * 
 * @author Simone Spaccarotella
 *
 */
public class TankPanel extends JPanel {

	private JLabel tankName;
	private JLabel tankEnergy;
	private Image banner;
	
	public TankPanel(GUIWindowedController guiController) {
		super(new GridLayout(2, 1));
		banner = ImageLoader.getInstance().getBanner();
		tankName = new JLabel(TankBattleGame.getTankName());
		tankEnergy = new JLabel(String.valueOf(TankBattleGame.getTank().getEnergy()));
		add(tankName);
		add(tankEnergy);
		setFocusable(false);
	}
	
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(banner, 0, 0, getWidth(), getHeight(), null);
	}
	
}
