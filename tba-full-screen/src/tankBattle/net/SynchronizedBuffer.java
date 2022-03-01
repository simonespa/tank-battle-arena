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

package tankBattle.net;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Questa classe implementa un buffer sincronizzato, con politica di accesso
 * FIFO. Questa classe, essendo parametrizzata, memorizza qualsiasi tipo di
 * oggetto.
 * 
 * @author Simone Spaccarotella
 * 
 * @param <T>
 *            questo parametro pu� essere un qualsiasi oggeto.
 */
public final class SynchronizedBuffer<T> {

	/* � il buffer */
	private List<T> buffer;
	/* questo flag server a rendere non pi� bloccante il metodo get() */
	private boolean closed;

	/**
	 * Crea un buffer vuoto, che dovr� essere riempito successivamente mediante
	 * gli appositi metodi.
	 */
	public SynchronizedBuffer() {
		buffer = new LinkedList<T>();
		closed = false;
	}

	/**
	 * Aggiunge in coda al buffer un nuovo oggetto.
	 * 
	 * @param obj
	 *            l'oggetto da memorizzare.
	 */
	public synchronized void put(T obj) {
		if (!closed) {
			buffer.add(obj);
			notifyAll();
		}
	}

	/**
	 * Restituisce l'oggetto in testa al buffer. Se il buffer � vuoto, il thread
	 * che ha invocato questo metodo andr� in wait.
	 * 
	 * @return un oggetto parametrizzato.
	 */
	public synchronized T get() {
		// se il buffer non � chiuso
		if (!closed) {
			// fintanto che il buffer � vuoto
			while (buffer.size() == 0) {
				// manda il thread in wait
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			// non appena il buffer si riempe il thread viene svegliato
			// e restituisce l'elemento in testa al buffer
			return buffer.remove(0);
			// altrimenti se il buffer � chiuso
		} else {
			// se non � vuoto
			if (buffer.size() > 0) {
				// restituisci ancora l'elemento in testa
				return buffer.remove(0);
			} else {
				// altrimenti restituisci null
				return null;
			}
		}
	}

	/**
	 * Restituisce la dimensione del buffer.
	 * 
	 * @return un intero che indica il numero degli elementi contenuti nel
	 *         buffer.
	 */
	public synchronized int size() {
		return buffer.size();
	}

	/**
	 * Chiude definitivamente il buffer.
	 */
	public synchronized void close() {
		closed = true;
		notifyAll();
	}

}