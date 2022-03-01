/*
 * Copyright  2008 Simone Spaccarotella
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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import tankBattle.net.ClientServerCapable;
import tankBattle.net.NetUtility;
import tankBattle.utilities.Console;

/**
 * L'istanza di questa classe invia un messaggio in broadcast in cerca di
 * eventuali server. Invia un messaggio all'indirizzo di broadcast e questo
 * messaggio verr ripetuto su tutte le porte del range dinamico.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class RequestSender extends Thread implements ClientServerCapable {

	/** la porta sulla quale il server dovr attestare la sua presenza */
	private int localPort;
	/** il flag di stop del demone */
	private boolean stop;

	/** l'identificativo dell'istanza del thread */
	private static int idNumber = 0;

	private Console consolePanel;

	/**
	 * Crea un {@code Sender} di pacchetti.
	 * 
	 * @param port
	 */
	public RequestSender(ThreadGroup group, int port) {
		super(group, "RequestSender-" + (++idNumber));
		localPort = port;
		stop = false;

		consolePanel = new Console(getName());
	}

	@Override
	public void run() {

		consolePanel.printOut(getName(), "started");

		// acquisisce l'indirizzo di broadcast
		InetAddress broadcast = getBroadcast();

		// se il client è connesso alla rete invia i pacchetti in broadcast
		if (broadcast != null) {
			// alloca un socket UDP
			DatagramSocket socket = NetUtility.getFreeDatagramSocket();

			consolePanel.printOut(getName(), "sending broadcast from port "
					+ socket.getLocalPort());

			byte[] buffer = (CLIENT_REQUEST_WHO_IS_SERVER + localPort)
					.getBytes();
			for (int port = 49152; !stop && port <= 65535; port++) {
				// prepara il pacchetto con il messaggio da inviare
				DatagramPacket packet = new DatagramPacket(buffer,
						buffer.length, broadcast, port);
				try {
					socket.send(packet);
				} catch (IOException e) {
					consolePanel.printErr(getName(), e.getMessage());
				}
			}
		}
		consolePanel.printOut(getName(), "stopped");
	}

	/**
	 * Restituisce un indirizzo che corrisponde all'indirizzo di broadcast della
	 * rete a cui l'interfaccia di rete connessa
	 * 
	 * @return indirizzo di broadcast della rete
	 */
	private InetAddress getBroadcast() {
		InetAddress broadcast = null;
		boolean isBreaked = false;
		// acquisisce le interfaccie di rete
		Enumeration<NetworkInterface> interfaces = null;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			consolePanel.printErr(getName(),
					"maybe one or more network interfaces are down"
							+ e.getMessage());
		}
		// scandisce ogni singola interfaccia
		while (!isBreaked && interfaces.hasMoreElements()) {
			// acquisisce la singola interfaccia di rete
			NetworkInterface devices = interfaces.nextElement();
			// ne acquisisce gli indirizzi IPv4 e IPv6
			List<InterfaceAddress> interfaceAddresses = devices
					.getInterfaceAddresses();
			for (InterfaceAddress addresses : interfaceAddresses) {
				// acquisisce l'indirizzo dell'interfaccia di rete
				InetAddress addr = addresses.getAddress();
				// e controlla che sia IPv4 e che non sia il loopback
				if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
					broadcast = addresses.getBroadcast();
					isBreaked = true;
					break;
				}
			}
		}
		return broadcast;
	}

	/**
	 * Stoppa il thread
	 */
	public void stopSender() {
		stop = true;
	}

	@Override
	protected void finalize() throws Throwable {
		--idNumber;
	}

}