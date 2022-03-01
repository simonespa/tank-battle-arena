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

package tankBattle.gui.fullScreen;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.SoundManager;

/**
 * E' il pannello delle opzioni. Da qui è possibile passare alla modalità
 * "Windowed", oppure è anche possibile abilitare/disabilitare sia la musica che
 * gli effetti sonori.
 * 
 * @author Simone Spaccarotella
 */
public final class OptionPanel extends TankBattlePanel {

	private Image[] toWindowedImages;
	private Image[] musicOnImages;
	private Image[] musicOffImages;
	private Image[] soundEffectOnImages;
	private Image[] soundEffectOffImages;
	private Image[] backImages;

	private Image toWindowed;
	private Image music;
	private Image soundEffect;
	private Image back;

	public static final int TO_WINDOWED = 0;
	public static final int MUSIC = 1;
	public static final int SOUND_EFFECT = 2;
	public static final int BACK = 3;
	private final int numMenu = 4;

	/**
	 * Crea il pannello delle opzioni.
	 * 
	 * @param frame
	 *            il riferimento alla finestra principale
	 */
	public OptionPanel(TankBattleFrame frame) {
		super(frame);
		currentMenu = TO_WINDOWED;
		toWindowed();
	}

	@Override
	protected void loadImages() {
		ImageLoader loader = ImageLoader.getInstance();
		toWindowedImages = new Image[2];
		toWindowedImages[0] = loader.getGUIFullScreenImage("toWindowedNo");
		toWindowedImages[1] = loader.getGUIFullScreenImage("toWindowedSi");
		musicOnImages = new Image[2];
		musicOnImages[0] = loader.getGUIFullScreenImage("musicOnNo");
		musicOnImages[1] = loader.getGUIFullScreenImage("musicOnSi");
		musicOffImages = new Image[2];
		musicOffImages[0] = loader.getGUIFullScreenImage("musicOffNo");
		musicOffImages[1] = loader.getGUIFullScreenImage("musicOffSi");
		soundEffectOnImages = new Image[2];
		soundEffectOnImages[0] = loader
				.getGUIFullScreenImage("soundEffectOnNo");
		soundEffectOnImages[1] = loader
				.getGUIFullScreenImage("soundEffectOnSi");
		soundEffectOffImages = new Image[2];
		soundEffectOffImages[0] = loader
				.getGUIFullScreenImage("soundEffectOffNo");
		soundEffectOffImages[1] = loader
				.getGUIFullScreenImage("soundEffectOffSi");
		backImages = new Image[2];
		backImages[0] = loader.getGUIFullScreenImage("backNo");
		backImages[1] = loader.getGUIFullScreenImage("backSi");
	}

	@Override
	public void selectUp() {
		currentMenu--;
		if (currentMenu < 0) {
			currentMenu = BACK;
			back();
		} else if (currentMenu == SOUND_EFFECT) {
			soundEffect();
		} else if (currentMenu == MUSIC) {
			music();
		} else if (currentMenu == TO_WINDOWED) {
			toWindowed();
		}
	}

	@Override
	public void selectDown() {
		currentMenu = (currentMenu + 1) % numMenu;
		if (currentMenu == TO_WINDOWED) {
			toWindowed();
		} else if (currentMenu == MUSIC) {
			music();
		} else if (currentMenu == SOUND_EFFECT) {
			soundEffect();
		} else if (currentMenu == BACK) {
			back();
		}
	}

	private void toWindowed() {
		toWindowed = toWindowedImages[1];
		setMusic(0);
		setSoundEffect(0);
		back = backImages[0];
	}

	private void music() {
		toWindowed = toWindowedImages[0];
		setMusic(1);
		setSoundEffect(0);
		back = backImages[0];
	}

	private void soundEffect() {
		toWindowed = toWindowedImages[0];
		setMusic(0);
		setSoundEffect(1);
		back = backImages[0];
	}

	private void back() {
		toWindowed = toWindowedImages[0];
		setMusic(0);
		setSoundEffect(0);
		back = backImages[1];
	}

	/**
	 * Setta il testo del menù "music" in base allo stato di ON/OFF della
	 * musica.
	 * 
	 * @param index
	 *            l'identificativo che rappresenta lo stato ON/OFF
	 */
	private void setMusic(int index) {
		int m = TankBattleGame.getMusicState();
		if (m == SoundManager.ON) {
			music = musicOnImages[index];
		} else if (m == SoundManager.OFF) {
			music = musicOffImages[index];
		}
	}

	/**
	 * Setta il testo del menù "sound effect" in base allo stato di ON/OFF della
	 * musica.
	 * 
	 * @param index
	 *            l'identificativo che rappresenta lo stato ON/OFF
	 */
	private void setSoundEffect(int index) {
		int s = TankBattleGame.getSoundEffectState();
		if (s == SoundManager.ON) {
			soundEffect = soundEffectOnImages[index];
		} else if (s == SoundManager.OFF) {
			soundEffect = soundEffectOffImages[index];
		}
	}

	/**
	 * Aggiorna lo stato ON/OFF del menù "Music"
	 */
	public void refreshMusicMenu() {
		music();
	}

	/**
	 * Aggiorna lo stato di ON/OFF del menù "Sound Effect"
	 */
	public void refreshSoundEffectMenu() {
		soundEffect();
	}

	/**
	 * Aggiorna lo stato di ON/OFF dei menù "Music" e "Sound Effect e riporta la
	 * selezione al menù "To Windowed"
	 */
	public void refreshMenu() {
		music();
		soundEffect();
		toWindowed();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(toWindowed, getX(toWindowed), getY(toWindowed, -135), null);
		g.drawImage(music, getX(music), getY(music, -45), null);
		g.drawImage(soundEffect, getX(soundEffect), getY(soundEffect, +45),
				null);
		g.drawImage(back, getX(back), getY(back, +135), null);
	}

}
