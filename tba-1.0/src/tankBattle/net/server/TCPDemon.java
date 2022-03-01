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

package tankBattle.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tankBattle.net.Demon;
import tankBattle.utilities.Console;

/**
 * &Egrave; il demone TCP. L'istanza di questa classe resta in ascolto sulla
 * prima porta libera per quanto riguarda il protocollo TCP. Ad ogni connessione
 * crea un thread separato che gestirà la connessione con il client ad esso
 * associato.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TCPDemon extends Demon {

	// è il socket sul quale i client si connettono
	private ServerSocket socket;
	// è l'oggetto che smista agli altri client i messaggi arrivati da un client
	// specifico
	private Dispatcher dispatcher;
	// è l'identificativo dell'istanza del thread
	private static int idNumber = 0;

	private Console console; // DEBUG

	/**
	 * Crea un demone TCP il quale afferisce ad un {@code group}, agganciato al
	 * socket {@code socket}.
	 * 
	 * @param group
	 *            il gruppo di appartenenza del thread.
	 * @param socket
	 *            il socket su cui è agganciato.
	 */
	public TCPDemon(ThreadGroup group, ServerSocket socket) {
		super(group, "TCPDemon-" + (++idNumber));
		this.socket = socket;
		dispatcher = new Dispatcher();

		console = new Console(getName()); // DEBUG
	}

	@Override
	public void run() {
		console.printOut(getName(), "Started on port " + socket.getLocalPort()); // DEBUG
		dispatcher.start();
		while (run) {
			try {
				Socket incoming = socket.accept();
				console.printOut(getName(), "New client connected"); // DEBUG
				dispatcher.addClient(incoming);
			} catch (IOException e) {
				run = false;
				console.printErr(e.getMessage()); // DEBUG
			}
		}
		console.printOut(getName(), "Stopped"); // DEBUG
	}

	@Override
	public void stopDemon() {
		super.stopDemon();
		try {
			socket.close();
		} catch (IOException e) {
		}
		dispatcher.stopDemon();
	}

	/**
	 * Stoppa tutti i ThreadServer in ascolto.
	 */
	public void stopThreadServers() {
		dispatcher.stopThreadServers();
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}