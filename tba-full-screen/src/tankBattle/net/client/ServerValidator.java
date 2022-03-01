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

package tankBattle.net.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.regex.Pattern;

import tankBattle.model.game.TankBattleGame;
import tankBattle.net.ClientServerCapable;
import tankBattle.net.Endpoint;
import tankBattle.net.InvalidProtocolException;
import tankBattle.utilities.Console;

/**
 * Valida gli eventuali server che dovessero connettersi al client. Se colui il
 * quale si connette rispetta il protocollo di rete, allora verrà riconosciuto
 * come un server valido e verrà memorizzato in una struttura dati apposita
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ServerValidator extends Thread implements
		ClientServerCapable {

	/* il riferimento al socket aperto dal server */
	private Socket incoming;
	/* lo stream di input del socket */
	private BufferedReader input;
	/* lo stream di output del socket */
	private PrintWriter output;
	/* la lista dei server eventualmente presenti in rete */
	private Vector<Endpoint> serverList;
	/* il flag che indica al thread se puo effettuare le operazioni */
	private boolean run = true;

	/* l'identificativo dell'istanza del thread */
	private static int idNumber = 0;

	private Console console;
	private boolean thereIsConsolePanel;

	/**
	 * Crea un validator di server.
	 * 
	 * @param incoming
	 *            è il socket che viene passato dal {@link ResponseReceiver}
	 * @param input
	 *            è lo stream di input del socket
	 * @param output
	 *            è lo stream di output del socket
	 * @param group
	 *            è il gruppo di appartenenza di questo thread
	 * @param serverList
	 *            è la lista dei server eventualmente presenti in rete
	 */
	public ServerValidator(Socket incoming, BufferedReader input,
			PrintWriter output, ThreadGroup group, Client client) {
		super(group, "ServerValidator-" + (++idNumber));
		this.serverList = client.getServerList();
		this.thereIsConsolePanel = client.thereIsConsolePanel();
		this.incoming = incoming;
		this.input = input;
		this.output = output;
		console = new Console(getName());
	}

	@Override
	public void run() {
		printOut("started on port" + incoming.getLocalPort());
		if (run) {
			try {
				printOut("sending \"" + CLIENT_REQUEST_CAN_I_PLAY + "\"");
				output.println(CLIENT_REQUEST_CAN_I_PLAY);
				printOut("waiting to receive message");
				String message = input.readLine();
				printOut("message received \"" + message + "\"");
				String regex = SERVER_RESPONSE_YES_OF_COURSE + "[0-9]+";
				if (Pattern.matches(regex, message)) {
					printOut("right response");
					Endpoint e = new Endpoint(incoming.getInetAddress()
							.getHostName(), incoming.getInetAddress(),
							getPort(message));
					printOut("adding new server to list");
					serverList.addElement(e);
					TankBattleGame.getJoinServerPanel().addRow(e.getHostName(),
							e.getAddress().getHostAddress());
				} else {
					throw new InvalidProtocolException(
							"The server doesn't have reply "
									+ SERVER_RESPONSE_YES_OF_COURSE + "\"");
				}
			} catch (InvalidProtocolException e) {
				console.printErr(getName(), e.getMessage());
			} catch (IOException e) {
				console.printErr(getName(), e.getMessage());
			}
		}
		cleanUp();

		console.printOut(getName(), "stopped");
	}

	/**
	 * Effettua il cleanup di tutte le risorse allocate dal thread
	 */
	private void cleanUp() {
		console.printOut(getName(), "cleaning up");

		// chiude lo stream di input
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				console.printErr(getName(), e.getMessage());
			}
		}
		// chiude lo stream di output
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				console.printErr(getName(), e.getMessage());
			}
		}
	}

	/**
	 * Estrapola il numero di porta contenuto nella stringa passata tramite lo
	 * stream di input
	 * 
	 * @param data
	 *            è la stringa in cui è contenuto il numero di porta
	 * @return restituisce un intero che corrisponde al numero di porta
	 */
	private synchronized int getPort(String data) {
		String[] split = data.split(":");
		return Integer.parseInt(split[1]);
	}

	/*
	 * Stampa a video lo standard Output.
	 */
	private void printOut(String message) {
		if (thereIsConsolePanel) {
			TankBattleGame.getConsolePanel().printOut(getName(), message);
		}
		console.printOut(getName(), message);
	}

	/*
	 * Stampa a video lo standard Error.
	 */
	private void printErr(String message) {
		if (thereIsConsolePanel) {
			TankBattleGame.getConsolePanel().printErr(getName(), message);
		}
		console.printErr(getName(), message);
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}