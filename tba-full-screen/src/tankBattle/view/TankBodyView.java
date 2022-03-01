/*
 * Copyright � 2008 Simone Spaccarotella
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

package tankBattle.view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tankBattle.model.component.Tank;

/**
 * Rappresenta la view del corpo del tank.
 * 
 * @author Simone Spaccarotella
 *
 */
public final class TankBodyView extends AbstractTankView {

	private Tank tank;
	
	/**
	 * Crea la view del corpo del tank.
	 * 
	 * @param width la larghezza della superficie di gioco.
	 * @param height l'altezza della superficie di gioco.
	 * @param tank il riferimento al tank.
	 */
	public TankBodyView(int width, int height, Tank tank) {
		super(width, height);
		this.tank = tank;
		view = tank.getView();
	}

	@Override
	public void updateObserver() {
		x = (int) tank.getX();
		y = (int) tank.getY();
		centerX = tank.getCenterX();
		centerY = tank.getCenterY();
		angle = tank.getAngle();
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics graph) {
		Graphics2D g = (Graphics2D) graph;
		if (!tank.isVisible()) {
			float opacity;
			if( tank.isLocale() ) {
				opacity = 0.3F;
			} else {
				opacity = 0.0F;
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		}
		super.paintComponent(graph);
		
	}

}