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

package tankBattle.gui.windowed;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import tankBattle.gui.windowed.controller.GUIWindowedController;

/**
 * Crea una barra dei menù, la quale verrà aggiunta alla finestra del client.
 * Mediante questa finestra è possibile connettersi al server, inserendo
 * direttamente l'indirizzo e la porta.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class JoinServerMenuBar extends JMenuBar {

	private JMenu connectTo;
	private JMenuItem connect;

	/**
	 * Crea una barra dei menù, nella quale è presente un solo menù. Questo menù
	 * serve a connettere il client ad un server, conoscendo l'indirizzo e la
	 * porta.
	 * 
	 * @param controller
	 *            è il controller della GUI.
	 */
	public JoinServerMenuBar(GUIWindowedController controller) {
		super();
		connectTo = new JMenu("Connect to");
		connect = new JMenuItem("connect to...");
		connect.addActionListener(controller);
		connect.setActionCommand("connectTo");

		connectTo.add(connect);
		add(connectTo);
	}

}