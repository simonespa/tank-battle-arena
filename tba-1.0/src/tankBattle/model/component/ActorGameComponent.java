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

/**
 * Nella gerarchia, questa classe indica tutti i componenti che nel gioco
 * rivestono il ruolo di attore (sia principale che secondario).
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class ActorGameComponent extends ColoredComponent {

	/** il nome del componente "attore" */
	protected String name;

	/**
	 * Crea un componente con un colore ed un nome obbligatori, ed una posizione
	 * fissata nel punto {@code (0, 0)}.
	 * 
	 * @param color
	 *            è il colore che assumerà nel gioco l'attore.
	 * @param name
	 *            è il nome. Esso può anche essere non unico, sarà il gioco a
	 *            gestire l'univocità dei nomi.
	 */
	public ActorGameComponent(Color color, String name) {
		super(color);
		this.name = name;
	}

	/**
	 * Crea un attore che oltre al colore e al nome, accetta come parametro la
	 * posizione iniziale di gioco, espressa dalle coordinate {@code (x, y)}.
	 * 
	 * @param x
	 *            ascissa della posizione
	 * @param y
	 *            ordinata della posizione
	 * @param color
	 *            il colore del componente
	 * @param name
	 *            il nome del componente
	 */
	public ActorGameComponent(double x, double y, Color color, String name) {
		super(x, y, color);
		this.name = name;
	}

	/**
	 * Cambia il nome del componente.
	 * 
	 * @param name
	 *            il nuovo nome da dare al componente
	 */
	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * Restituisce il nome del componente.
	 * 
	 * @return il nome del componente
	 */
	public String getName() {
		return name;
	}

}