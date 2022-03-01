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

package tankBattle.net;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class SocketOutputStream {

	private String id;
	private PrintWriter output;

	public SocketOutputStream(Socket socket, String id) {
		this.id = id;
		if (socket != null) {
			try {
				output = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				output = null;
			}
		}
	}

	public PrintWriter getOutputStream() {
		return output;
	}

	public String getID() {
		return id;
	}
}
