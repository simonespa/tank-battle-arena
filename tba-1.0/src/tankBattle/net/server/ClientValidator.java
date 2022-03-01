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

package tankBattle.net.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import tankBattle.net.ClientServerCapable;
import tankBattle.net.Endpoint;
import tankBattle.net.InvalidProtocolException;
import tankBattle.utilities.Console;

/**
 * Crea un validatore di client, il quale controlla che chi si connette rispetti
 * il protocollo di comunicazione. Se il protocollo viene rispettato vuol dire
 * che chi si � connesso � un client valido e quindi ha il permesso di
 * agganciarsi alla porta di gioco sulla quale il server � in ascolto.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ClientValidator extends Thread implements
		ClientServerCapable {

	/** � il socket che il validatore crea per potersi connettere al client */
	private Socket socket;
	/** � lo stream di input del socket */
	private BufferedReader input;
	/** � lo stream di output del socket */
	private PrintWriter output;
	/** � la porta di gioco alla quale tutti i client dovranno connettersi */
	private int gamePort;
	/**
	 * � un flag che indica al validatore la possibilit� di continuare
	 * l'esecuzione
	 */
	private boolean run = true;

	/** � l'identificatore dell'istanza del thread */
	private static int idNumber = 0;

	private Console console;

	/**
	 * Crea un validatore. Il suo compito � di controllare che colui il quale si
	 * connette sia un client valido che segue il protocollo di comunicazione.
	 * 
	 * @param group
	 *            � il gruppo di appartenenza del thread.
	 * @param endpoint
	 *            corrisponde all'indirizzo del socket del client.
	 * @param serverPort
	 *            � la porta del server alla quale il client dovr� connettersi
	 *            per giocare.
	 */
	public ClientValidator(ThreadGroup group, Endpoint endpoint, int serverPort) {
		super(group, "ClientValidator-" + (++idNumber));
		gamePort = serverPort;

		console = new Console(getName());

		try {
			socket = new Socket(endpoint.getAddress(), endpoint.getPort());
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			run = false;
			console.printErr(getName(), e.getMessage());
		}
	}

	@Override
	public void run() {
		console.printOut(getName(), "started on port " + socket.getLocalPort()
				+ ", connected on port " + socket.getPort());

		if (run) {
			try {
				console.printOut(getName(), "sending " + SERVER_RESPONSE_AM_I
						+ " to " + socket.getPort());

				output.println(SERVER_RESPONSE_AM_I);

				console.printOut(getName(), "waiting to receive reply on "
						+ socket.getLocalPort());

				String message = input.readLine(); // QUI SI FERMA IL PROGRAMMA

				console.printOut(getName(), "message received: " + message
						+ " from " + socket.getPort());

				if (message.equals(CLIENT_REQUEST_CAN_I_PLAY)) {
					console.printOut(getName(), "sending "
							+ SERVER_RESPONSE_YES_OF_COURSE + " to "
							+ socket.getPort());

					output.println(SERVER_RESPONSE_YES_OF_COURSE + gamePort);
				} else {
					throw new InvalidProtocolException("unexpected CAN_I_PLAY");
				}
			} catch (InvalidProtocolException e) {
				console.printErr(getName(), e.getMessage());
			} catch (IOException e) {
				console.printErr(getName(), e.getMessage());
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					console.printErr(getName(), e.getMessage());
				}
			}
		}
		cleanUp();

		console.printOut(getName(), "Stopped");
	}

	/**
	 * Effettua il cleanup dell risorse allocate dal thread
	 */
	private void cleanUp() {
		output.close();
		try {
			input.close();
		} catch (IOException e) {
			console.printErr(getName(), e.getMessage());
		}
		try {
			socket.close();
		} catch (IOException e) {
			console.printErr(getName(), e.getMessage());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}