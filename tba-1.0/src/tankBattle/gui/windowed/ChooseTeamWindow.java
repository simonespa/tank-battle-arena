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

import javax.swing.JFrame;

import tankBattle.gui.windowed.controller.GUIWindowedController;

/**
 * Da questa finestra &egrave; possibile scegliere il team di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class ChooseTeamWindow extends JFrame {

	private static ChooseTeamPanel panel;

	/**
	 * Crea una finestra di scelta, nella quale &egrave; presente una combo box
	 * contenente i nomi dei due possibili team, ed un tasto di conferma.
	 * 
	 * @param controller
	 *            il controller della GUI.
	 */
	private ChooseTeamWindow(GUIWindowedController controller) {
		super("Choose the team");
		panel = new ChooseTeamPanel(this, controller);
		setContentPane(panel);
		setSize(450, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Mostra la finestra di dialogo.
	 * 
	 * @param controller
	 *            il controller di gioco.
	 */
	public static void showWindow(GUIWindowedController controller) {
		ChooseTeamWindow c = new ChooseTeamWindow(controller);
	}

}
