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

package tankBattle.model;

/**
 * Questa eccezione viene lanciata quando ad un'istanza di tipo
 * {@link tankBattle.model.component.ColoredComponent} viene passato un
 * riferimento nullo al posto del colore.
 * 
 * @author Simone Spaccarotella
 */
public final class NullColorException extends Exception {

	/** è il messaggio di errore */
	private String message;

	/**
	 * Crea un'eccezione con il messaggio d'errore della superclasse
	 */
	public NullColorException() {
		message = super.getMessage();
	}

	/**
	 * Crea un'eccezione con un messaggio di errore personalizzato passato dai
	 * parametri
	 * 
	 * @param message
	 *            è il messaggio di errore
	 */
	public NullColorException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
