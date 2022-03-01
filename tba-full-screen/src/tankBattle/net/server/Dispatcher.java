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

import java.net.Socket;
import java.util.Vector;

import tankBattle.net.ClientServerCapable;
import tankBattle.net.Demon;
import tankBattle.net.Dispatchable;
import tankBattle.net.SynchronizedBuffer;
import tankBattle.utilities.Console;

/**
 * Questa classe rappresenta lo smistatore dei messaggi, delegato dal server. Si
 * occupa di acquisire i messaggi inviati dai client, i quali verranno
 * memorizzati in un buffer, e successivamente processati.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Dispatcher extends Demon implements Dispatchable<String>,
		ClientServerCapable {

	// il contatore che determina l'ID del client
	private int clientCounter;
	// la lista dei client
	private Vector<ThreadServer> clientList;
	// il buffer sincronizzato
	private SynchronizedBuffer<String> buffer;
	// l'identificativo dell'istanza del thread
	private static int idNumber = 0;

	private Console console;

	/**
	 * Crea un modulo software deputato allo smistamento dei messaggi che
	 * arrivano dalla rete.
	 */
	public Dispatcher() {
		super("Dispatcher-" + (++idNumber));
		clientCounter = 0;
		clientList = new Vector<ThreadServer>();
		buffer = new SynchronizedBuffer<String>();

		console = new Console(getName());
	}

	@Override
	public void run() {
		console.printOut(getName(), "Started");
		while (run || buffer.size() > 0) {
			// prende il messaggio dal buffer
			String message = buffer.get();
			// se il messaggio non è nullo
			if (message != null) {
				// acquisisce l'ID del mittente
				String id = message.split("@")[1];
				// sincronizza l'accesso al vettore
				synchronized (clientList) {
					// invia il messaggio a tutti i client connessi
					for (ThreadServer t : clientList) {
						// se il client non è lo stesso che ha inviato il messaggio
						// allora
						if (!t.getTankID().equals(id)) {
							// viene notificato
							t.dispatch(message);
							console.printOut(getName(), "Sending \"" + message
									+ "\"");
							// altrimenti se il client è lo stesso
						} else {
							// e il messaggio che ha inviato è "#create" allora
							if (message.startsWith(CREATE)) {
								// questo client riceve i dati di tutti gli altri
								// client connessi
								// (tranne se stesso), in modo da ricreare in locale
								// la lista dei nemici
								for (ThreadServer enemy : clientList) {
									if (enemy != t) {
										t.dispatch(CREATE + ":"
												+ enemy.getTankName() + ":"
												+ enemy.getTankColor() + ":"
												+ enemy.getTankX() + ":"
												+ enemy.getTankY() + ":"
												+ enemy.getTankDirection() + "@"
												+ enemy.getTankID());
									}
								}
							}
						}
					}
				}
			}
		}
		console.printOut(getName(), "Stopped");
	}

	/**
	 * Aggiunge un client alla lista.
	 * 
	 * @param socket
	 *            è il socket del client.
	 */
	public void addClient(Socket socket) {
		clientCounter++;
		ThreadServer t = new ThreadServer(this, socket, clientCounter);
		clientList.addElement(t);
		console.printOut(getName(), "Client added");
		t.start();
	}

	/**
	 * Rimuove il client specificato dalla lista.
	 * 
	 * @param socket
	 *            il socket del client.
	 */
	public void removeClient(ThreadServer t) {
		clientList.removeElement(t);
		console.printOut(getName(), "Client removed");
	}

	/**
	 * Rimuove tutti i client dalla lista.
	 */
	public void removeAllClient() {
		clientList.removeAllElements();
		console.printOut(getName(), "All clients removed");
	}

	/**
	 * Bufferizza il messaggio passato come parametro, per essere
	 * successivamente processato.
	 * 
	 * @param message
	 *            una stringa che rappresenta il messaggio da processare.
	 */
	@Override
	public void dispatch(String message) {
		buffer.put(message);
	}

	@Override
	public void stopDemon() {
		super.stopDemon();
		buffer.close();
		for (ThreadServer t : clientList) {
			t.stopDemon();
		}
	}

	/**
	 * Stoppa tutti i threadserver in ascolto sul proprio client.
	 */
	public void stopThreadServers() {
		while( buffer.size() > 0);
		for (ThreadServer t : clientList) {
			t.dispatch(CLOSE_GAME);
			t.stopDemon();
		}
		if (clientList.size() != 0) {
			clientList.removeAllElements();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}