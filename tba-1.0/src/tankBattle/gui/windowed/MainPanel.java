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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import tankBattle.utilities.ImageLoader;

/**
 * 
 * E' il pannello della finestra principale, all'interno del quale verrà
 * disegnata un immagine, che sarà il logo del gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class MainPanel extends JPanel {

	/** lo sfondo di questo pannello */
	private Image background;

	/**
	 * Crea un pannello con un'immagine di background
	 */
	public MainPanel() {
		super();
		background = ImageLoader.getInstance().getBackground();
	}

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.drawImage(background, 0, 0, null);
	}

}