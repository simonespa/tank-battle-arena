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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import tankBattle.net.ClientServerCapable;
import tankBattle.net.NetUtility;
import tankBattle.utilities.Console;

/**
 * Rappresenta un demone che resta in ascolto degli eventuali server che
 * dovessero essere presenti sulla rete in cui ci si trova.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ResponseReceiver extends Thread implements
		ClientServerCapable {

	/** è il socket sul quale si accettano le connessioni da parte di terzi */
	private ServerSocket socket;
	/** stream di input del socket */
	private BufferedReader input;
	/** stream di output del socket */
	private PrintWriter output;
	/** invia i pacchetti in broadcast */
	private RequestSender sender;
	/** è il gruppo al quale appartiene il {@link ServerValidator} */
	private ThreadGroup validatorGroup;
	/**  */
	private ThreadGroup requestSenderGroup;
	/** è il flag di stop del thread */
	private boolean stop;
	/**  */
	private Client client;

	/** è l'identificativo dell'istanza del thread */
	private static int idNumber = 0;

	private Console consolePanel;

	/**
	 * Crea un demone in ascolto degli eventuali server presenti sulla rete.
	 * 
	 * @param client
	 *            il riferimento al client.
	 */
	public ResponseReceiver(Client client) {
		super(client.getResponseReceiverGroup(), "ResponseReceiver-"
				+ (++idNumber));
		this.client = client;
		validatorGroup = new ThreadGroup("ServerValidatorGroup");
		requestSenderGroup = new ThreadGroup("RequestSenderGroup");
		socket = NetUtility.getFreeServerSocket();
		sender = new RequestSender(requestSenderGroup, socket.getLocalPort());
		stop = false;

		consolePanel = new Console(getName());
	}

	@Override
	public void run() {
		/* DEBUG */
		consolePanel.printOut(getName(), "started on port "
				+ socket.getLocalPort());

		sender.start();
		while (!stop) {
			try {
				consolePanel.printOut(getName(),
						"waiting to receive message on port "
								+ socket.getLocalPort());

				// resta in attesa sul socket;
				Socket incoming = socket.accept();

				consolePanel.printOut(getName(), "server "
						+ incoming.getInetAddress().getHostName()
						+ " connected on port " + incoming.getLocalPort());

				// apre gli stream di I/O sul socket
				input = new BufferedReader(new InputStreamReader(incoming
						.getInputStream()));
				output = new PrintWriter(incoming.getOutputStream(), true);
				// legge cio che è stato inviato e memorizzato nel buffer
				String message = input.readLine();

				consolePanel.printOut(getName(), "message received \""
						+ message + "\"");

				// se il messaggio corrisponde al protocollo
				if (message.equals(SERVER_RESPONSE_AM_I)) {
					// crea un nuovo thread "ServerValidator"
					ServerValidator validator = new ServerValidator(incoming,
							input, output, validatorGroup, client);
					validator.start();
				}
				if (requestSenderGroup.activeCount() == 0) {
					stop = true;
				}
			} catch (IOException e) {
				stop = true;
				consolePanel.printErr(getName(), e.getMessage());
			}
		}
		cleanUpAndStop();

		consolePanel.printOut(getName(), "stopped");
	}

	/**
	 * Effettua le operazioni di clean-up e chiude il socket
	 */
	public void stopReceiver() {
		cleanUpAndStop();
	}

	/**
	 * Viene chiamato dal metodo pubblico "stopReceiver". Questo metodo esegue
	 * effettivamente le operazioni di clean-up.
	 */
	private void cleanUpAndStop() {
		consolePanel.printOut(getName(), "cleaning up");

		sender.stopSender();
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				consolePanel.printErr(getName(), e.getMessage());
			}
		}
		while (validatorGroup.activeCount() != 0)
			;
		client.notifyClosing(this);
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}