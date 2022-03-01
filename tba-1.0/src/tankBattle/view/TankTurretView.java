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

package tankBattle.view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import tankBattle.model.component.Tank;
import tankBattle.model.component.Turret;

/**
 * Rappresenta la view della torretta.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankTurretView extends AbstractTankView {

	/** il riferimento alla torretta */
	private Tank tank;
	/** il riferimento alla torretta */
	private Turret turret;

	/**
	 * Crea la view della torretta.
	 * 
	 * @param width
	 *            la larghezza della superficie di gioco.
	 * @param height
	 *            l'altezza della superficie di gioco.
	 * @param tank
	 *            il riferimento del tank.
	 */
	public TankTurretView(int width, int height, Tank tank) {
		super(width, height);
		this.tank = tank;
		turret = tank.getTurret();
		view = turret.getView();
	}

	@Override
	public void updateObserver() {
		// le istruzioni seguenti sommati all'offset, servono
		// a calibrare la torretta sopra il tank
		x = ((int) turret.getX()) + 8;
		y = ((int) turret.getY()) + 8;
		centerX = turret.getCenterX() + 18;
		centerY = turret.getCenterY() + 18;
		angle = turret.getAngle();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graph) {
		Graphics2D g = (Graphics2D) graph;
		if (!tank.isVisible()) {
			float opacity;
			if (tank.isLocale()) {
				opacity = 0.3F;
			} else {
				opacity = 0.0F;
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					opacity));
		}
		super.paintComponent(graph);
	}

}