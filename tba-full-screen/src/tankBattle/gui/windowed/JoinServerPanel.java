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

import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class JoinServerPanel extends JSplitPane {

	private JScrollPane scrollPane;
	private ConsolePanel consolePanel;
	private JTable table;
	private DefaultTableModel model;
	private GUIWindowedController controller;

	/**
	 * 
	 * @param controller
	 */
	public JoinServerPanel(GUIWindowedController controller) {
		super(VERTICAL_SPLIT, true);
		this.controller = controller;
		initField();
		setDivider();
	}

	/**
	 * Inizializza i campi istanza.
	 */
	private void initField() {
		// aggiunge il nome delle colonne
		String[] columnNames = { "Host Name", "IP Address" };
		// fa in modo che le celle non siano editabili
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		// crea una nuova tabella
		table = new JTable(model);
		// aggiunge il listener che ascolta i doppi click sulle righe della
		// tabella
		table.addMouseListener(controller);
		// istanzia uno scrolpane passandogli come parametro la tabella
		scrollPane = new JScrollPane(table);
		// aggiunge questo componente nella parte superiore dello split pane
		setTopComponent(scrollPane);
		consolePanel = new ConsolePanel(controller);
		TankBattleGame.setConsolePanel(consolePanel);
		setBottomComponent(consolePanel);
	}

	/**
	 * 
	 */
	private void setDivider() {
		setDividerSize(3);
		setDividerLocation(100);
	}

	/**
	 * 
	 * @param server
	 * @param ip
	 */
	public void addRow(String server, String ip) {
		Vector<String> rowData = new Vector<String>();
		rowData.add(server);
		rowData.add(ip);
		model.addRow(rowData);
		table.repaint();
	}

	/**
	 * 
	 */
	public void removeAllRow() {
		model.setRowCount(0);
	}

	public synchronized void printOut(String owner, String message) {
		consolePanel.printOut(owner, message);
	}

	public synchronized void printErr(String owner, String message) {
		consolePanel.printErr(owner, message);
	}

}
