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

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.SoundManager;

/**
 * Questa classe
 *
 * @author Simone Spaccarotella
 */
public class OptionPanel extends JPanel {

	private GUIWindowedController controller;

	private JButton returnGame;
	private JButton leaveGame;
	private JToggleButton soundEffect;
	private JToggleButton music;

	public OptionPanel(GUIWindowedController controller) {
		super(new GridLayout(4, 1));
		this.controller = controller;
		initField();
		addComponent();
	}

	public void setMusic() {
		int musicState = TankBattleGame.getMusicState();
		if (musicState == SoundManager.ON) {
			music.setText("Music ON");
			music.setSelected(true);
		} else if (musicState == SoundManager.OFF) {
			music.setText("Music OFF");
			music.setSelected(false);
		}
	}

	public void setSoundEffect() {
		int soundEffectState = TankBattleGame.getSoundEffectState();
		if (soundEffectState == SoundManager.ON) {
			soundEffect.setText("Sound Effect ON");
			soundEffect.setSelected(true);
		} else if (soundEffectState == SoundManager.OFF) {
			soundEffect.setText("Sound Effect OFF");
			soundEffect.setSelected(false);
		}
	}

	private void initField() {
		returnGame = new JButton("Return to Game");
		returnGame.addActionListener(controller);
		leaveGame = new JButton("Leave Game");
		leaveGame.addActionListener(controller);
		music = new JToggleButton();
		music.addActionListener(controller);
		soundEffect = new JToggleButton();
		soundEffect.addActionListener(controller);
		// setta il testo dei bottoni e lo stato selezionato in base
		// allo stato ON/OFF della musica e degli effetti sonori
		if (TankBattleGame.getMusicState() == SoundManager.ON) {
			music.setText("Music ON");
			music.setSelected(true);
		} else {
			music.setText("Music OFF");
			music.setSelected(false);
		}
		if (TankBattleGame.getSoundEffectState() == SoundManager.ON) {
			soundEffect.setText("Sound Effect ON");
			soundEffect.setSelected(true);
		} else {
			soundEffect.setText("Sound Effect OFF");
			soundEffect.setSelected(false);
		}
	}

	private void addComponent() {
		add(music);
		add(soundEffect);
		add(returnGame);
		add(leaveGame);
	}

}
