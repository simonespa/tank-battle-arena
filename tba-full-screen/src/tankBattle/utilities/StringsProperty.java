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

package tankBattle.utilities;

/**
 * Classe di utilit� che restituisce la chiave e il valore di una stringa che
 * rappresenta le propriet� di un componente
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class StringsProperty {

	private static String property;

	/**
	 * Acquisisce la stringa di propriet�
	 * 
	 * @param string
	 *            stringa di propriet�
	 */
	public static void acquireProperty(String string) {
		property = string;
	}

	/**
	 * Restituisce la chiave della propriet�.
	 * 
	 * @return una stringa che rappresenta la chiave univoca a cui corrisponde
	 *         uno ed un solo valore
	 */
	public static String getKey() {
		return property.split("=")[0].split("\\.")[1];
	}

	/**
	 * Restituisce il valore della propriet�.
	 * 
	 * @return una stringa che rappresenta il valore identificato da una chiave
	 *         univoca
	 */
	public static String getValue() {
		return property.split("=")[1];
	}

}
