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

import java.awt.Image;

/**
 * Questa classe rappresenta la view di ogni componente di tipo
 * {@link tankBattle.model.component.GameComponent GameComponent}.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class ComponentView extends View {

	/** è il riferimento all'immagine del componente */
	protected Image view;

	/**
	 * Crea una view per tutte le classi che derivano da {@code GameComponent}.
	 * 
	 * @param width
	 *            la larghezza della superficie di gioco.
	 * @param height
	 *            l'altezza della superficie di gioco.
	 */
	public ComponentView(int width, int height) {
		super(width, height);
		setOpaque(false);
	}

	/**
	 * Questo metodo avvisa la view, che lo stato del model è cambiato, e la
	 * obbliga ad aggiornare il monitor.
	 */
	public abstract void updateObserver();

}
