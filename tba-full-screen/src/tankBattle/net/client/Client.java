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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Vector;
import java.util.regex.Pattern;

import tankBattle.gui.windowed.ConsolePanel;
import tankBattle.model.RotationManager;
import tankBattle.model.component.Tank;
import tankBattle.model.game.Collider;
import tankBattle.model.game.TankBattleGame;
import tankBattle.net.BufferedIODemon;
import tankBattle.net.ClientServerCapable;
import tankBattle.net.Endpoint;
import tankBattle.utilities.Console;
import tankBattle.utilities.RegularExpressionConstants;

/**
 * Questa classe definise il client del gioco. Con un oggetto di tipo client è
 * possibile ricercare automaticamente un server sulla rete e connettersi ad
 * esso. Inoltre gestisce la connessione di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Client extends BufferedIODemon implements
		ClientServerCapable, RegularExpressionConstants {

	/* un modulo che resta in ascolto dei server */
	private ResponseReceiver receiver;
	/* il gruppo di appartenenza del receiver */
	private ThreadGroup receiverGroup;
	/* la lista dei server */
	private Vector<Endpoint> serverList;
	/* indica se il server è stato stoppato */
	private boolean stopped;

	/* console su cui vengono scritti i messaggi di debug */
	private Console console;
	private ConsolePanel consolePanel;
	private boolean thereIsConsolePanel;

	/**
	 * Crea un nuovo client, facendo partire gli opportuni thread di ricerca dei
	 * server nella rete.
	 */
	public Client() {
		super("Client");
		Console.setConsoleVisible(TankBattleGame.getDebugMode());
		receiverGroup = new ThreadGroup("ResponseReceiverGroup");
		consolePanel = TankBattleGame.getConsolePanel();
		console = new Console(getName());
		thereIsConsolePanel = true;
	}

	/**
	 * Crea un nuovo client e lo connette al socket definito dai parametri che
	 * vengono passati al costruttore.
	 * 
	 * @param host
	 *            indirizzo dell'host.
	 * @param port
	 *            porta dell'host.
	 */
	public Client(String host, int port) {
		super(host, port, "Client");
		TankBattleGame.setOutputStream(output);
		Console.setConsoleVisible(TankBattleGame.getDebugMode());
		receiverGroup = new ThreadGroup("ResponseReceiverGroup");
		console = new Console(getName());
		thereIsConsolePanel = false;
	}

	/**
	 * Restituisce lo stato del flag "therIsConsolePanel".
	 * 
	 * @return "true" se è stata creata la console del client.
	 */
	public boolean thereIsConsolePanel() {
		return thereIsConsolePanel;
	}

	/*
	 * Comunica al server la presenza di un nuovo Tank. Contestualmente il
	 * server comunicherà l'ID del Tank e la mappa di gioco.
	 */
	private void handshake() {
		printOut("Starting handshake");

		// attende il messaggio dal server <ID:MAPPA>
		String message = read();
		printOut("received \"" + message + "\"");
		// se il messaggio ricevuto è compliant al protocollo allora
		if (Pattern.matches(INTEGER + ":" + WORD, message)) {
			createTankAndMap(message);
			sendTankParameter();
			TankBattleGame.startGameWindow(output);
		} else {
			printErr("Invalid protocol. Expected message was ${id}:${map}");
			run = false;
		}

		printOut("Stopping handshake");
	}

	/*
	 * Crea il tank e la mappa
	 */
	private void createTankAndMap(String message) {
		String[] split = message.split(":");
		String id = split[0];
		String map = split[1];

		TankBattleGame.setMap(map);

		// crea un oggetto random
		Random r = new Random(System.nanoTime());
		// genera le coordinate del tank in modo casuale
		int x = r.nextInt(Collider.getMaximumX());
		int y = r.nextInt(Collider.getMaximumY());
		// istanzia il tank
		Tank tank = new Tank(x, y, TankBattleGame.getTankColor(),
				TankBattleGame.getTankName());
		tank.start();
		tank.setID(id);
		// setta un nuovo seed all'oggetto random
		r.setSeed(System.nanoTime());
		// genera una numero casuale compreso tra 0 e 4
		int direction = r.nextInt(4);
		// in base al valore del numero casuale generato viene settato
		// l'orientamento del tank sul piano di gioco
		if (direction == 0) {
			tank.setStartPositionToward(RotationManager.NORTHWARD);
		} else if (direction == 1) {
			tank.setStartPositionToward(RotationManager.SOUTHWARD);
		} else if (direction == 2) {
			tank.setStartPositionToward(RotationManager.WESTWARD);
		} else if (direction == 3) {
			tank.setStartPositionToward(RotationManager.EASTWARD);
		}
		// setta l'oggetto Tank nello stato globale
		TankBattleGame.setTank(tank);
	}

	/*
	 * Invia i parametri del tank
	 */
	private void sendTankParameter() {
		Tank tank = TankBattleGame.getTank();
		String name = tank.getName();
		String color = String.valueOf(tank.getColor().getRGB());
		String x = String.valueOf(tank.getX());
		String y = String.valueOf(tank.getY());
		String currentAngle = String.valueOf(tank.getCurrentAngle());
		String message = name + ":" + color + ":" + x + ":" + y + ":"
				+ currentAngle;
		printOut("Sending message \"" + message + "\"");
		write(message);
	}

	@Override
	public void run() {
		printOut("Started");
		// effettua l'handshake iniziale
		handshake();
		while (run) {
			// acquisisce il messaggio
			printOut("Waiting for a message");
			String message = read();
			if (message != null) {
				printOut(message);
				if (message.startsWith(CREATE)) {
					createEnemy(message);
				} else if (message.startsWith(DESTROY)) {
					destroyEnemy(message);
				} else if (message.startsWith(CLOSE_GAME)) {
					stopDemon();
				} else if (message.startsWith(MOVE)) {
					String id = getID(message);
					float[] point = getPoint(message);
					TankBattleGame.move(id, point[0], point[1]);
				} else if (message.startsWith(TURN_BODY)) {
					String id = getID(message);
					int direction = getDirection(message);
					TankBattleGame.turnBody(id, direction);
				} else if (message.startsWith(TURN_TURRET)) {
					String id = getID(message);
					int direction = getDirection(message);
					TankBattleGame.turnTurret(id, direction);
				} else if (message.startsWith(SHOOT)) {
					TankBattleGame.shoot(getID(message));
				} else if (message.startsWith(SET_VISIBLE)) {
					String id = getID(message);
					boolean visible = getVisible(message);
					TankBattleGame.setVisible(id, visible);
				} else if (message.startsWith(MESSAGE)) {
					TankBattleGame.receiveMessage(message);
				}
			} else {
				printErr("Message is null. Destroying the tank");
			}
		}
		printOut("Stopped");
	}

	/*
	 * Crea un nuovo nemico e lo aggiunge nel gioco.
	 */
	private void createEnemy(String message) {
		// splitta il messaggio per ricavare l'ID e i parametri del nuovo tank
		String[] split = message.split("@");
		// acquisisce l'ID
		String newID = split[1];
		// prende l'elemento che contiene i parametri e lo splitta ulteriormente
		// per ":";
		split = split[0].split(":");
		// acquisisce i parametri del nuovo tank
		String name = split[1];
		Color color = new Color(new Integer(split[2]));
		double x = new Double(split[3]);
		double y = new Double(split[4]);
		int direction = new Integer(split[5]);
		// crea il nuovo tank
		printOut("creating new Tank");
		Tank tank = new Tank(x, y, color, name);
		tank.setID(newID);
		tank.setStartPositionToward(direction);
		TankBattleGame.addEnemy(tank);
	}

	/*
	 * Elimina il nemico specificato dalla lista.
	 */
	private void destroyEnemy(String message) {
		// splitta il messaggio per ricavare l'ID e i parametri del nuovo tank
		String[] split = message.split("@");
		// acquisisce l'ID
		String newID = split[1];
		// distrugge il tank specificato
		TankBattleGame.removeTank(newID);
	}

	/*
	 * Restituisce l'ID ricavato dal messaggio.
	 */
	private String getID(String message) {
		String[] split = message.split("@");
		return split[1];
	}

	/*
	 * Restituisce le coordinate da settare al tank.
	 * 
	 * @param message il messaggio da cui estrapolare i parametri.
	 * 
	 * @return una coppia di punti che rappresentano le coordinate del tank.
	 */
	private float[] getPoint(String message) {
		String[] split = message.split("@");
		String[] param = split[0].split(":");
		float[] point = new float[2];
		point[0] = new Float(param[1]);
		point[1] = new Float(param[2]);
		return point;
	}

	/*
	 * Restituisce un intero che rappresenta l'indice dell'angolo di rotazione
	 * sia del tank che della torretta.
	 * 
	 * @param message il messaggio da cui estrapolare i parametri.
	 * 
	 * @return l'indice dell'array che rappresenta l'angolo di rotazione sia del
	 * tank che della torretta.
	 */
	private int getDirection(String message) {
		String[] split = message.split("@");
		String[] param = split[0].split(":");
		return new Integer(param[1]);
	}

	/*
	 * Restituisce il valore di visibilità del tank.
	 * 
	 * @param message il messaggio da cui estrapolare i parametri.
	 * 
	 * @return "true" se il tank è visibile.
	 */
	private boolean getVisible(String message) {
		String[] split = message.split("@");
		String[] param = split[0].split(":");
		return new Boolean(param[1]);
	}

	@Override
	public void stopDemon() {
		super.stopDemon();
		try {
			if (socket != null) {
				socket.close();
				TankBattleGame.getWindowedController().leave();
			}
		} catch (IOException e) {
			printErr(e.getMessage());
		}
	}

	/**
	 * Scannerizza la rete alla ricerca di uno o più server.
	 */
	public void findServer() {
		printOut("Finding server");
		serverList = new Vector<Endpoint>();
		receiver = new ResponseReceiver(this);
		receiver.start();
		stopped = false;
	}

	/**
	 * Restituisce il gruppo a cui appartiene il "ResponseReceiver"
	 * 
	 * @return restituisce un oggetto di tipo {@link java.lang.ThreadGroup}
	 */
	public ThreadGroup getResponseReceiverGroup() {
		return receiverGroup;
	}

	/**
	 * L'oggetto ResponseReceiver notifica al client la sua fine attività.
	 * 
	 * @param obj
	 *            un riferimento all'oggetto
	 *            {@link tankBattle.net.client.ResponseReceiver}
	 */
	public void notifyClosing(ResponseReceiver obj) {
		// se l'oggetto che chiama il metodo è il ResponseReceiver
		// istanziato dal client allora pone a true il flag
		if (obj == receiver) {
			stopped = true;
		}
		printOut("Finding stopped");
	}

	/**
	 * Connette il client al server specificato dai parametri.
	 * 
	 * @param endpoint
	 */
	public void connectTo(Endpoint endpoint) {
		try {
			socket = new Socket(endpoint.getAddress(), endpoint.getPort());
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
			TankBattleGame.setOutputStream(output);
			printOut("Connecting to " + endpoint.getHostName());
		} catch (IOException e) {
			printErr(e.getMessage());
		}
	}

	/**
	 * Stoppa lo scanner di rete lanciato alla ricerca di server.
	 */
	public void stopFinder() {
		receiver.stopReceiver();
	}

	/**
	 * Verifica che i thread lanciati dal client abbiano finito le loro
	 * operazioni e si siano fermati in modo pulito.
	 * 
	 * @return true se il client ha finito la ricerca dei server.
	 */
	public synchronized boolean isStopped() {
		return stopped;
	}

	/**
	 * Restituisce la lista dei server.
	 * 
	 * @return una lista di server oppure null se non ne è stato trovato
	 *         nessuno.
	 */
	public Vector<Endpoint> getServerList() {
		return serverList;
	}

	/*
	 * Stampa a video lo standard Output.
	 */
	private void printOut(String message) {
		if (thereIsConsolePanel) {
			consolePanel.printOut(getName(), message);
		}
		console.printOut(getName(), message);
	}

	/*
	 * Stampa a video lo standard Error.
	 */
	private void printErr(String message) {
		if (thereIsConsolePanel) {
			consolePanel.printErr(getName(), message);
		}
		console.printErr(getName(), message);
	}

}