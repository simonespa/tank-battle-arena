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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.Utility;

/**
 * Crea il pannello della finestra dalla quale è possibile connettersi ad un
 * server, conoscendo a priori il suo indirizzo e porta.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ConnectionPanel extends JPanel {

	private AddressInputTextField a;
	private AddressInputTextField b;
	private AddressInputTextField c;
	private AddressInputTextField d;
	private PortInputTextField port;
	private JButton connect;
	private GUIWindowedController controller;

	public ConnectionPanel(GUIWindowedController controller) {
		super(new BorderLayout());
		this.controller = controller;
		initField();
		addField();
	}

	private void initField() {
		try {
			a = new AddressInputTextField();
			b = new AddressInputTextField();
			c = new AddressInputTextField();
			d = new AddressInputTextField();
			port = new PortInputTextField();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		connect = new JButton("Connect");
		connect.setFont(port.getFont());
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int uno = new Integer(a.getText().trim());
					int due = new Integer(b.getText().trim());
					int tre = new Integer(c.getText().trim());
					int quattro = new Integer(d.getText().trim());
					int portNumber = new Integer(port.getText().trim());
					String address = uno + "." + due + "." + tre + "."
							+ quattro;
					TankBattleGame.startClient(address, portNumber);
				} catch (NumberFormatException exception) {
					Utility.logException(this, exception);
				}
			}
		});
	}

	private void addField() {
		JPanel north = new JPanel();
		JPanel center = new JPanel();
		JPanel east = new JPanel();
		JPanel south = new JPanel();
		north.setOpaque(false);
		center.setOpaque(false);
		east.setOpaque(false);
		south.setOpaque(false);
		center.add(a);
		center.add(b);
		center.add(c);
		center.add(d);
		east.add(port);
		south.add(connect);
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(east, BorderLayout.EAST);
		add(south, BorderLayout.SOUTH);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		Image banner = ImageLoader.getInstance().getBanner();
		g.drawImage(banner, 0, 0, null);
	}

}