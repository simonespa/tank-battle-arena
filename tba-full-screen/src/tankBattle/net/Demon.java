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

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class Demon extends Thread {

	protected boolean run;

	/**
	 * Inizializza il flag "run" a true.
	 */
	public Demon() {
		super();
		run = true;
	}

	public Demon(String name) {
		super(name);
		run = true;
	}

	public Demon(ThreadGroup group, String name) {
		super(group, name);
		run = true;
	}

	/**
	 * Setta il flag "run" a false.
	 */
	public void stopDemon() {
		run = false;
	}

}
