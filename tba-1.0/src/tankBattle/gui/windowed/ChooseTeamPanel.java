/*
 * Copyright ? 2008 Simone Spaccarotella
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * Questa classe ? il pannello della finestra di scelta del team.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class ChooseTeamPanel extends JPanel {

	/* i componenti della finestra */
	private JButton ok;
	private JButton cancel;
	private JComboBox selectTeam;
	private DefaultComboBoxModel model;
	private GUIWindowedController controller;
	private Image background;
	private ChooseTeamWindow owner;

	/**
	 * Crea il pannello.
	 */
	public ChooseTeamPanel(ChooseTeamWindow window,
			GUIWindowedController guiController) {
		super();
		this.controller = guiController;
		owner = window;
		background = ImageLoader.getInstance().getBanner();
		ok = new JButton("Ok");
		// implementa un asccoltatore anonimo
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getMainWindow().setMenuEnabled(true);
				controller.getMainWindow().setVisible(false);
				controller.getStartServerWindow().dispose();
				// fa partire il server
				String team = (String) model.getSelectedItem();
				TankBattleGame.startServerGame(team);
				owner.dispose();
			}

		});
		cancel = new JButton("Cancel");
		/*
		 * aggiunge un listener anonimo che chiude semplicemente la finestra di
		 * dialogo.
		 */
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				owner.dispose();
			}

		});
		model = new DefaultComboBoxModel();
		model.addElement("Computer scientist");
		model.addElement("Engigneer");
		selectTeam = new JComboBox(model);

		add(selectTeam);
		add(ok);
		add(cancel);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(background, 0, 0, null);
	}

}