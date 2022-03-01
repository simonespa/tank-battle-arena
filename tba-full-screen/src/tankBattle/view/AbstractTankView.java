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

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Questa classe è la base da cui derivano {@link tankBattle.view.TankBodyView TankBodyView}
 * e {@link tankBattle.view.TankTurretView TankTurretView}. Mediante questa classe è possibile
 * visualizzare il componente {@link tankBattle.model.component.Tank Tank}.
 * 
 * @author Simone Spaccarotella
 *
 */
public abstract class AbstractTankView extends ComponentView {

	protected int x;
	protected int y;
	protected double centerX;
	protected double centerY;
	protected double angle;

	/**
	 * Crea una view per il Tank.
	 * 
	 * @param width la larghezza della superficie di gioco.
	 * @param height l'altezza della superficie di gioco.
	 * @param tank il riferimento del Tank.
	 */
	public AbstractTankView(int width, int height) {
		super(width, height);
	}

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.rotate(angle, centerX, centerY);
		g.drawImage(view, x, y, null);
	}

}