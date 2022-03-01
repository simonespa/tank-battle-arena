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

package tankBattle.net;

import java.net.InetAddress;

/**
 * Incapsula una tripla <String, InetAddress, int>, con i relativi metodi
 * accessori. Questa classe definisce un qualsiasi host che pu√≤ connettersi
 * sulla rete
 * 
 * @author Simone Spaccarotella
 */
public final class Endpoint {

	/** nome dell'host */
	private String hostName;
	/** indirizzo dell'host */
	private InetAddress address;
	/** porta dell'host */
	private int port;
	/** numero di host istanziati */
	private static int numberOfHost = 0;

	/**
	 * Crea un endpoint che si riferisce ad un host specifico
	 * 
	 * @param address
	 *            indirizzo dell'host
	 * @param port
	 *            porta dell'host
	 */
	public Endpoint(InetAddress address, int port) {
		this("Host-" + (++numberOfHost), address, port);
	}

	/**
	 * Crea un endpoint mediante i parametri passati da input
	 * 
	 * @param name
	 *            Ë il nome dell'host
	 * @param address
	 *            Ë l'indirizzo dell'host
	 * @param port
	 *            Ë la porta dell'host
	 */
	public Endpoint(String name, InetAddress address, int port) {
		this.hostName = name;
		this.address = address;
		this.port = port;
	}

	/**
	 * Restituisce il nome dell'host
	 * 
	 * @return Ë una stringa che corrisponde al nome dell'host
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * Restituisce l'indirizzo dell'host
	 * 
	 * @return Ë un {@link InetAddress} che corrisponde all'indirizzo dell'host
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * Restituisce la porta dell'host
	 * 
	 * @return Ë un intero che corrisponde alla porta dell'host
	 */
	public int getPort() {
		return port;
	}

}