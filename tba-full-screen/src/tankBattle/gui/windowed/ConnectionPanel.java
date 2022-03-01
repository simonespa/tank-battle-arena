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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.Utility;

/**
 * Crea il pannello della finestra dalla quale è possibile connettersi ad un
 * server, conoscendo a priori il suo indirizzo e porta.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ConnectionPanel extends JPanel {

	private JLabel addressLabel;
	private JLabel portLabel;
	private AddressInputTextField a;
	private AddressInputTextField b;
	private AddressInputTextField c;
	private AddressInputTextField d;
	private AddressInputTextField port;
	private JButton connect;
	private GUIWindowedController controller;

	public ConnectionPanel(GUIWindowedController controller) {
		super(null);
		this.controller = controller;
		setBackground(Color.BLACK);
		initField();
		addField();
	}

	private void initField() {
		addressLabel = new JLabel("address");
		addressLabel.setForeground(Color.WHITE);
		portLabel = new JLabel("port");
		portLabel.setForeground(Color.WHITE);
		a = new AddressInputTextField();
		a.setBounds(40, 40, 40, 30);
		b = new AddressInputTextField();
		b.setBounds(90, 40, 40, 30);
		c = new AddressInputTextField();
		c.setBounds(140, 40, 40, 30);
		d = new AddressInputTextField();
		d.setBounds(190, 40, 40, 30);
		port = new AddressInputTextField();
		port.setBounds(260, 40, 80, 30);
		connect = new JButton("Connect");
		connect.setBounds(40, 100, 300, 50);
		connect.setFont(port.getFont());
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new Integer(a.getText());
					new Integer(b.getText());
					new Integer(c.getText());
					new Integer(d.getText());
					int portNumber = new Integer(port.getText());
					String address = a.getText() + "." + b.getText() + "."
							+ c.getText() + "." + d.getText();
					TankBattleGame.startClient(address, portNumber);
				} catch (NumberFormatException exception) {
					Utility.logException(this, exception);
				}
			}
		});
	}

	private void addField() {
		add(a);
		add(b);
		add(c);
		add(d);
		add(port);
		add(connect);
	}

}
