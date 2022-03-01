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

import java.net.ServerSocket;

import tankBattle.model.game.TankBattleGame;
import tankBattle.net.NetUtility;
import tankBattle.utilities.Console;

/**
 * L'istanza di questa classe è un oggetto che funge da server. Questo server si
 * metterà in ascolto di eventuali client sue due porte distinte. Una prima
 * porta verrà utilizzata per il protocollo UDP, mentre un'altra per il
 * protocollo TCP. Sulla porta UDP il server resterà in ascolto di client che
 * stanno cercando il server con una comunicazione broadcast, mentre sulla porta
 * TCP verranno stabilite le connessioni di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Server {

	/* il socket sul quale verranno accettate le connessioni */
	private ServerSocket socket;
	/* il demone che ascolta le connessioni TCP */
	private TCPDemon tcpDemon;
	/* il gruppo di appartenenza del TCPDemon */
	private ThreadGroup tcpDemonGroup;
	/* il demone che ascolta le connessioni UDP */
	private UDPDemon udpDemon;
	/* il gruppo di appartenenza del {@link UDPDemon} */
	private ThreadGroup udpDemonGroup;
	/* il flag booleano che indica se il server è stoppato */
	private boolean stopped;

	/* console su cui vengono scritti i messaggi di debug */
	private Console console;

	/**
	 * Costruttore di default
	 */
	public Server() {
		socket = NetUtility.getFreeServerSocket();
		tcpDemonGroup = new ThreadGroup("TCPDemonGroup");
		tcpDemon = new TCPDemon(tcpDemonGroup, socket);
		udpDemonGroup = new ThreadGroup("UDPDemonGroup");
		udpDemon = new UDPDemon(udpDemonGroup, socket.getLocalPort());
		stopped = false;

		Console.setConsoleVisible(TankBattleGame.isDebugMode());
		console = new Console("Server", true);
	}

	/**
	 * Fa partire il server, allocando tutte le risorse necessarie
	 */
	public void start() {
		console.printOut("Started");

		tcpDemon.start();
		udpDemon.start();

		console.printOut("game on port " + socket.getLocalPort());
	}

	/**
	 * Ferma il server. Questo metodo prima di stoppare il server, invia un
	 * segnale di stop a tutti i thread figli di questo thread
	 */
	public void stop() {
		udpDemon.stopDemon();
		tcpDemon.stopDemon();
		cleanUp();
		stopped = true;

		console.printOut("Stopped");
	}

	/**
	 * Stoppa solo i ThreadServer in ascolto.
	 */
	public void stopThreadServer() {
		tcpDemon.stopThreadServers();
	}

	/**
	 * Restituisce il numero di porta su cui è agganciato il socket.
	 * 
	 * @return un intero che identifica il numero di porta del server.
	 */
	public int getLocalPort() {
		return socket.getLocalPort();
	}

	/**
	 * Restituisce il flag stopped.
	 * 
	 * @return se stopped è false, allora il server è ancora running.
	 */
	public boolean isStopped() {
		return stopped;
	}

	/*
	 * Effettua le operazioni di cleanup su tutte le risorse allocate.
	 */
	private void cleanUp() {
		console.printOut("Cleaning up");

		while (udpDemonGroup.activeCount() != 0
				|| tcpDemonGroup.activeCount() != 0)
			;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

}