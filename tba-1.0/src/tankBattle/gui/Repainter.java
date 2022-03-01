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

package tankBattle.gui;

import tankBattle.view.RootComponentView;

/**
 * Ridisegna la grafica. L'istanza di questa classe &egrave; un thread che
 * effettua il refresh periodico del gioco.
 * 
 * @author Simone Spaccarotella.
 * 
 */
public class Repainter extends Thread {

	private boolean run;
	private RootComponentView rootView;

	/**
	 * Crea un nuovo repainter.
	 * 
	 * @param view
	 *            il pannello radice a cui vengono agganciati tutte le viste di
	 *            ogni componente.
	 */
	public Repainter(RootComponentView view) {
		run = true;
		rootView = view;
	}

	@Override
	public void run() {
		while (run) {
			rootView.update();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				run = false;
			}
		}
	}

	/**
	 * Stoppa il repainter.
	 */
	public void stopRepainter() {
		run = false;
	}

}
