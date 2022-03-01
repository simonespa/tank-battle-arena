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

import javax.swing.JDialog;

/**
 * La finestra di dialogo, nella quale sono contenute informazioni circa il
 * gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class AboutDialog extends JDialog {

	private AboutPanel panel;

	/**
	 * Crea una nuova finestra di dialogo.
	 * 
	 * @param owner
	 *            il riferimento alla finestra che ha creato questa finestra di
	 *            dialogo.
	 */
	public AboutDialog(MainWindow owner) {
		super(owner, owner.getTitle() + " | about", true);
		panel = new AboutPanel();
		setContentPane(panel);
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}