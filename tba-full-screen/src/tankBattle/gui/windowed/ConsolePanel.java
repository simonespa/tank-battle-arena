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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tankBattle.gui.windowed.controller.GUIWindowedController;

/**
 * 
 * @author Simone Spaccarotella
 *
 */
public class ConsolePanel extends JPanel {

	private JScrollPane scrollPane;
	private JTextArea console;
	private JPanel buttonPanel;
	private JButton back;
	private JButton refresh;
	private GUIWindowedController controller;
	private int counter;

	public ConsolePanel(GUIWindowedController controller) {
		super(new BorderLayout());
		this.controller = controller;
		counter = 0;
		setConsole();
		scrollPane = new JScrollPane(console);
		add(scrollPane, BorderLayout.CENTER);
		buttonPanel = new JPanel();
		back = new JButton("Back");
		back.setActionCommand("back to choose");
		back.addActionListener(controller);
		refresh = new JButton("Refresh");
		refresh.setActionCommand("refresh");
		refresh.addActionListener(controller);
		buttonPanel.add(back);
		buttonPanel.add(refresh);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/*
	 * Imposta le caratteristiche della console.
	 */
	private void setConsole() {
		console = new JTextArea();
		Font font = new Font("Verdana", Font.PLAIN, 13);
		console.setFont(font);
		console.setEditable(false);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.GREEN);
	}

	/**
	 * Stampa sullo standard output.
	 * 
	 * @param owner
	 * @param message
	 */
	public void printOut(String owner, String message) {
		counter++;
		console.append(counter + ") " + owner + ": " + message + "\n");
	}

	/**
	 * Stampa sullo standard error.
	 * 
	 * @param owner
	 * @param message
	 */
	public void printErr(String owner, String message) {
		counter++;
		console.append(counter + ") " + owner + ": [ERROR] " + message + "\n");
	}

}
