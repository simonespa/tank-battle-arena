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

import java.awt.Color;

import tankBattle.model.NullColorException;
import tankBattle.utilities.Utility;

/**
 * Questa classe aggiunge l'attributo "color" perché non tutti i componenti del
 * gioco possono cambiare colore.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class ColoredComponent extends SpriteComponent {

	/** il colore del componente */
	protected Color color;

	/**
	 * 
	 * @param color
	 */
	public ColoredComponent(Color color) {
		super();
		this.color = color;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	public ColoredComponent(double x, double y, Color color) {
		super(x, y);
		if (color == null) {
			try {
				throw new NullColorException("The tank's color is obligatory");
			} catch (NullColorException e) {
				Utility.logException(this, e);
			}
			throw new NullPointerException("The color variable in " + getType()
					+ " is null");
		} else {
			this.color = color.darker().darker();
		}
		initView();
	}

	/**
	 * Restituisce il colore.
	 * 
	 * @return un oggetto di tipo {@link java.awt.Color}
	 */
	public Color getColor() {
		return color;
	}

}