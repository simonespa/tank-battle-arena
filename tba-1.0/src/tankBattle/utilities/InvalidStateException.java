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

package tankBattle.utilities;

/**
 * &Egrave; un eccezione che viene generata quando ci si trova in uno stato non
 * valido.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class InvalidStateException extends Exception {

	/** il messaggio dell'eccezione */
	private String message;

	/**
	 * Crea un'eccezione con messaggio vuoto.
	 */
	public InvalidStateException() {
		message = "";
	}

	/**
	 * Crea un'eccezione con il messaggio passato in input.
	 * 
	 * @param message
	 */
	public InvalidStateException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}