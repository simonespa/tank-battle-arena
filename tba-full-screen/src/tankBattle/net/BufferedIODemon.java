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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class BufferedIODemon extends Demon {

	protected Socket socket;
	protected BufferedReader input;
	protected PrintWriter output;

	public BufferedIODemon() {
		super();
		socket = null;
		input = null;
		output = null;
	}

	public BufferedIODemon(String name) {
		super(name);
		socket = null;
		input = null;
		output = null;
	}

	public BufferedIODemon(ThreadGroup group, String name) {
		super(group, name);
		socket = null;
		input = null;
		output = null;
	}

	public BufferedIODemon(Socket socket) {
		super();
		try {
			this.socket = socket;
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	public BufferedIODemon(Socket socket, String name) {
		super(name);
		try {
			this.socket = socket;
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	public BufferedIODemon(Socket socket, ThreadGroup group, String name) {
		super(group, name);
		try {
			this.socket = socket;
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	public BufferedIODemon(String host, int port) {
		super();
		try {
			socket = new Socket(host, port);
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	public BufferedIODemon(String host, int port, String name) {
		super(name);
		try {
			socket = new Socket(host, port);
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	public BufferedIODemon(String host, int port, ThreadGroup group, String name) {
		super(group, name);
		try {
			socket = new Socket(host, port);
			input = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			run = false;
		} catch (IOException e) {
			run = false;
		}
	}

	protected void write(String message) {
		output.println(message);
		output.flush();
	}

	protected String read() {
		String message = null;
		try {
			return input.readLine().trim();
		} catch (NullPointerException e) {
			run = false;
			return null;
		} catch (IOException e) {
			run = false;
			return null;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		output.close();
		input.close();
		socket.close();
	}

}
