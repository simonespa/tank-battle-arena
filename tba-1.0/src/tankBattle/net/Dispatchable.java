/*
 * Copyright ? 2008 Simone Spaccarotella
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

package tankBattle.net;

import java.io.Serializable;

/**
 * Questa interfaccia permette a tutte le classi che la implementano, di inviare
 * un qualsiasi messaggio sulla rete.
 * 
 * Essendo una classe parametrizzata, &egrave; possibile inviare in rete un
 * qualsiasi oggetto serializzato.
 * 
 * @author Simone Spaccarotella
 * 
 * @param <T>
 */
public interface Dispatchable<T extends Serializable> {

	/**
	 * Tramite questo metodo, &egrave; possibile inviare un qualsiasi oggetto
	 * serializzato sulla rete.
	 * 
	 * @param obj
	 *            l'oggetto da inviare.
	 */
	public void dispatch(T obj);

}
