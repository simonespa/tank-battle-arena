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

package tankBattle.model.component;

/**
 * Rappresenta il muretto che viene piazzato nel gioco, per fare da ostacolo ai
 * Tank.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class Wall extends SpriteComponent {

	private static int width;
	private static int height;

	/**
	 * Crea un nuovo muretto, con una posizione ed una dimensione passati da
	 * input.
	 * 
	 * @param x
	 *            l'ascissa della posizione.
	 * @param y
	 *            l'ordinata della posizione.
	 * @param width
	 *            la larghezza del muretto.
	 * @param height
	 *            l'altezza del muretto.
	 */
	public Wall(double x, double y, int width, int height) {
		super(x, y);
		setDimension(width, height);
	}

	@Override
	public double getAngle() {
		return 0;
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initProperties() {

	}

}
