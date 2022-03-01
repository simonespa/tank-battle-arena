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
import java.net.Socket;
import java.util.regex.Pattern;

import tankBattle.model.game.TankBattleGame;
import tankBattle.net.BufferedIODemon;
import tankBattle.net.ClientServerCapable;
import tankBattle.net.Dispatchable;
import tankBattle.utilities.Console;
import tankBattle.utilities.RegularExpressionConstants;

/**
 * &Egrave; il thread creato dal server, il quale gestisce la comunicazione con
 * il lient. Ogni ThreadServer è associato ad un client diverso, in modo da
 * permettere la connessione simultanea di più client.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ThreadServer extends BufferedIODemon implements
		ClientServerCapable, RegularExpressionConstants, Dispatchable<String> {

	// smista i messaggi che riceve dai vari client verso i diversi client
	private Dispatcher dispatcher;
	// identificativo dell'istanza del thread
	private static int idNumber = 0;

	/* --------------- PARAMETRI DEL CLIENT --------------- */
	// ID univoco associato al client nel gioco
	private String id;
	private String name;
	private String color;
	private String x;
	private String y;
	private String direction;
	/* ---------------------------------------------------- */

	// console su cui vengono scritti i messaggi di debug
	private Console console;

	/**
	 * Crea un nuovo ThreadServer. Questo thread avrà il riferimento del
	 * dispatcher al quale inoltrare i messaggi arrivati dal client ed un ID da
	 * settare al client associato.
	 * 
	 * @param dispatcher
	 *            il dispatcher che inoltrerà i messaggi agli altri eventuali
	 *            client.
	 * @param socket
	 *            è il socket su cui è agganciato questo Thread.
	 * @param id
	 *            è l'identificativo associato al client.
	 */
	public ThreadServer(Dispatcher dispatcher, Socket socket, int id) {
		super(socket, "ThreadServer-" + (++idNumber));
		this.dispatcher = dispatcher;
		this.id = new Integer(id).toString();

		console = new Console(getName()); // DEBUG
	}
	
	/*
	 * Gestisce la connessione iniziale. 
	 */
	private void handshake() {
		printOut("Starting handshake");
		
		// invia al client l'ID e la mappa
		String message = id + ":" + TankBattleGame.getMap();
		printOut("Sending message \"" + message + "\"");
		write(message);
		// legge il messaggio di risposta
		message = read();
		printOut("Message received \"" + message + "\"");
		// costruisce l'espressione regolare
		String regex = WORD + ":" + INTEGER + ":" + DECIMAL + ":" + DECIMAL + ":" + INTEGER;
		// se la RE combacia con il messaggio
		if( Pattern.matches(regex, message) ) {
			// estrapola i parametri e li setta
			extractParameter(message);
			// invia il comando di creazione di un nuovo tank agli altri client connessi
			message = CREATE + ":" + message + "@" + id;
			printOut("Sending message \"" + message + "\" to the other client");
			dispatcher.dispatch(message); 
		} else {
			// altrimenti mette a false il flag e chiude il thread
			printErr("Invalid protocol. Expected message was ${name}:${color}:${x}:${y}:${direction}");
			run = false;
		}
		
		printOut("Stopping handshake");
	}
	
	/*
	 * Estrapola i parametri dal messaggio e
	 * li assegna alle variabili locali.
	 */
	private void extractParameter(String message) {
		String[] parameter = message.split(":");
		name = parameter[0];
		color = parameter[1];
		x = parameter[2];
		y = parameter[3];
		direction = parameter[4];
	}

	@Override
	public void run() {
		printOut("Started");
		
		handshake();
		while (run) {
			// legge i messaggi inviati dal client
			String message = read();
			// se non è nullo
			if (message != null) {
				if (message.startsWith(ClientServerCapable.DESTROY)) {
					dispatcher.removeClient(this);
				}
				printOut("Message received \"" + message + "\"");
				dispatcher.dispatch(message);
			} else {
				printOut("Message is null. Destroying the tank");
			}
		}
	
		printOut("Stopped");
	}

	/**
	 * Restituisce l'ID del client.
	 * 
	 * @return una stringa che rappresenta l'ID del client.
	 */
	public String getTankID() {
		return id;
	}

	public String getTankName() {
		return name;
	}

	public String getTankColor() {
		return color;
	}

	public String getTankX() {
		return x;
	}

	public String getTankY() {
		return y;
	}
	
	public String getTankDirection() {
		return direction;
	}

	@Override
	public void stopDemon() {
		super.stopDemon();
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				console.printErr(e.getMessage()); // debug
			}
		}
	}

	/*
	 * questo metodo pubblico dell'interfaccia "Dispatchable" fa da wrapper al
	 * metodo privato "write()"
	 */
	@Override
	public void dispatch(String obj) {
		write(obj);
	}
	
	/*
	 * Stampa i messaggi di output sulla console
	 */
	private void printOut(String message) {
		console.printOut(getName(), message);
	}
	
	/*
	 * Stampa i messaggi di errore sulla console
	 */
	private void printErr(String message) {
		console.printErr(getName(), message);
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}