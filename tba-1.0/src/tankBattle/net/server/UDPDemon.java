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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Pattern;

import tankBattle.net.ClientServerCapable;
import tankBattle.net.Endpoint;
import tankBattle.net.NetUtility;
import tankBattle.utilities.Console;

/**
 * E un demone che ascolta le connessioni UDP che possono provenire dai client
 * che cercano l'indirizzo del server
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class UDPDemon extends Thread implements ClientServerCapable {

	/** è il socket del thread */
	private DatagramSocket socket;
	/** è un riferimento ai pacchetti che verranno inviati in broadcast */
	private DatagramPacket packet;
	/** è un buffer che consente di memorizzare o inviare dei dati */
	private byte[] buffer;
	/** è la porta di gioco dove c'è in ascolto un'altro demone */
	private int gamePort;
	/** è il flag di stop del demone */
	private boolean stop = false;

	/** è l'identificativo dell'istanza del thread */
	private static int idNumber = 0;

	private Console console;

	/**
	 * Crea un demone e lo mette in ascolto su una determinata porta libera
	 * 
	 * @param group
	 * @param port
	 */
	public UDPDemon(ThreadGroup group, int port) {
		super(group, "UDPDemon-" + (++idNumber));
		socket = NetUtility.getFreeDatagramSocket();
		gamePort = port;

		console = new Console(getName());
	}

	@Override
	public void run() {
		console.printOut(getName(), "Started on port " + socket.getLocalPort());

		// crea un gruppo per i thread di tipo "ClientValidator"
		ThreadGroup validatorGroup = new ThreadGroup("ClientValidatorGroup");
		// crea un'espressione regolare
		String regex = CLIENT_REQUEST_WHO_IS_SERVER + "\\d+";
		Pattern pattern = Pattern.compile(regex);
		while (!stop) {
			try {
				buffer = new byte[255];
				packet = new DatagramPacket(buffer, buffer.length);

				console.printOut(getName(),
						"Waiting to receive a message on port "
								+ socket.getLocalPort());

				socket.receive(packet);
				String message = new String(packet.getData(), packet
						.getOffset(), packet.getLength());

				console.printOut(getName(), "Received: " + message + " from "
						+ packet.getPort());

				// controlla se le due stringhe combaciano
				boolean hasMatching = pattern.matcher(message).matches();
				if (hasMatching) {
					console.printOut(getName(),
							"Message ok. Creating new ClientValidator.");

					Endpoint endpoint = new Endpoint(packet.getAddress(),
							getPort(message));
					ClientValidator validator = new ClientValidator(
							validatorGroup, endpoint, gamePort);
					validator.start();
				}
			} catch (IOException e) {
				stop = true;
				console.printErr(e.getMessage());
			}
		}
		cleanUp(validatorGroup);
	}

	/**
	 * Ferma il demone.
	 */
	public void stopDemon() {
		if (socket != null) {
			socket.close();
		}
		console.printOut(getName(), "Stopped");
	}

	/**
	 * Effettua il cleanup di tutte le risorse allocate
	 * 
	 * @param group
	 */
	private void cleanUp(ThreadGroup group) {
		console.printOut(getName(), "Cleaning up");

		while (group.activeCount() != 0)
			;
	}

	/**
	 * Estrapola la porta alla quale ci si deve connettere per poter giocare
	 * 
	 * @param data
	 *            è la stringa contenente il numero di porta
	 * @return restituisce un intero che corrisponde proprio al numero di porta
	 */
	private synchronized int getPort(String data) {
		String[] split = data.split(":");
		return Integer.parseInt(split[1]);
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}