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

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

import tankBattle.utilities.Utility;

/**
 * Questa classa fornisce degli strumenti utili per l'allocazione dinamica di
 * socket tcp/udp su una qualsiasi porta libera
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class NetUtility {

	/**
	 * Restituisce una porta libera per le connessioni TCP.
	 * 
	 * @return è un intero che corrisponde al numero di porta al quale il server
	 *         può connettersi
	 */
	public static ServerSocket getFreeServerSocket() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(0);
		} catch (IOException e) {
			Utility.logException("NetUtility says: " + e);
		}
		return socket;
	}

	/**
	 * Restituisce una porta libera per le connessioni UDP
	 * 
	 * @return è un intero che corrisponde al numero di porta al quale il server
	 *         può connettersi
	 */
	public static DatagramSocket getFreeDatagramSocket() {
		DatagramSocket socket = null;
		boolean unbound = true;
		for (int port = 49152; unbound && port <= 65535; port++) {
			try {
				socket = new DatagramSocket(port);
				unbound = false;
			} catch (BindException e) {
			} catch (SocketException e) {
				Utility.logException(e.getMessage());
			}
		}
		return socket;
	}

}