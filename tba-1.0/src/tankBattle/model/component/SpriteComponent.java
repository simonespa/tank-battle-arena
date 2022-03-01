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

import java.awt.Image;

/**
 * Questa classe rappresenta tutti i componenti di gioco che possono essere
 * visualizzati, ovvero tutti quei componenti che hanno un'immagine
 * rappresentabile a schermo.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class SpriteComponent extends GameComponent {

	/** è il riferimento all'immagine del componente */
	protected Image view;

	/**
	 * 
	 */
	public SpriteComponent() {
		this(0, 0);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public SpriteComponent(double x, double y) {
		super(x, y);
	}

	/**
	 * 
	 */
	protected abstract void initView();

	/**
	 * Restituisce l'immagine del componente.
	 * 
	 * @return l'immagine del componente
	 */
	public Image getView() {
		return view;
	}

	public abstract double getAngle();

}