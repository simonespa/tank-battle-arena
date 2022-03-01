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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Gli oggetti di questa classe contengono una lista di oggetti serializzati i
 * quali rappresentano le proprietà del motore del tank.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankProperties implements Serializable {

	// numero seriale univoco
	private static final long serialVersionUID = -6826461066569279526L;
	// la mappa che conterrà le diverse configurazioni in base al terreno
	private Map<String, TankEngineProperties> tankConfiguration;

	/**
	 * Crea un oggetto capace di contenere i parametri di configurazione del
	 * motore del tank.
	 */
	public TankProperties() {
		tankConfiguration = new HashMap<String, TankEngineProperties>();
	}

	/**
	 * Aggiunge all'oggetto un elemento di tipo
	 * {@link tankBattle.utilities.TankEngineProperties}.
	 * 
	 * @param key
	 *            la chiave di ricerca dell'oggetto.
	 * @param value
	 *            l'oggetto passato.
	 */
	public void putNewConfiguration(String key, TankEngineProperties value) {
		tankConfiguration.put(key, value);
	}

	/**
	 * Restituisce l'oggetto che corrisponde alla chiave {@code key}.
	 * 
	 * @param key
	 *            la chiave di ricerca dell'oggetto.
	 * @return l'oggetto identificato dalla chiave {@code key}.
	 */
	public TankEngineProperties getConfiguration(String key) {
		return tankConfiguration.get(key);
	}

}
