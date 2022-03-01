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
 * 
 * 
 * @author Simone Spaccarotella
 * 
 */
public class GamingOptionPanel extends TankBattlePanel {

	private Image[] musicOnImages;
	private Image[] musicOffImages;
	private Image[] soundEffectOnImages;
	private Image[] soundEffectOffImages;
	private Image[] returnToGameImages;
	private Image[] leaveGameImages;

	private Image music;
	private Image soundEffect;
	private Image returnToGame;
	private Image leaveGame;

	public static final int MUSIC = 0;
	public static final int SOUND_EFFECT = 1;
	public static final int RETURN_TO_GAME = 2;
	public static final int LEAVE_GAME = 3;
	private final int numMenu = 4;

	/**
	 * 
	 * @param frame
	 */
	public GamingOptionPanel(TankBattleFrame frame) {
		super(frame);
		currentMenu = MUSIC;
		music();
	}

	@Override
	protected void loadImages() {
		ImageLoader loader = ImageLoader.getInstance();
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
		returnToGameImages = new Image[2];
		returnToGameImages[0] = loader.getGUIFullScreenImage("returnToGameNo");
		returnToGameImages[1] = loader.getGUIFullScreenImage("returnToGameSi");
		leaveGameImages = new Image[2];
		leaveGameImages[0] = loader.getGUIFullScreenImage("leaveGameNo");
		leaveGameImages[1] = loader.getGUIFullScreenImage("leaveGameSi");
	}

	@Override
	public void selectUp() {
		currentMenu--;
		if (currentMenu < 0) {
			currentMenu = LEAVE_GAME;
			leaveGame();
		} else if (currentMenu == RETURN_TO_GAME) {
			returnToGame();
		} else if (currentMenu == SOUND_EFFECT) {
			soundEffect();
		} else if (currentMenu == MUSIC) {
			music();
		}
	}

	@Override
	public void selectDown() {
		currentMenu = (currentMenu + 1) % numMenu;
		if (currentMenu == MUSIC) {
			music();
		} else if (currentMenu == SOUND_EFFECT) {
			soundEffect();
		} else if (currentMenu == RETURN_TO_GAME) {
			returnToGame();
		} else if (currentMenu == LEAVE_GAME) {
			leaveGame();
		}
	}

	private void music() {
		setMusic(1);
		setSoundEffect(0);
		returnToGame = returnToGameImages[0];
		leaveGame = leaveGameImages[0];
	}

	private void soundEffect() {
		setMusic(0);
		setSoundEffect(1);
		returnToGame = returnToGameImages[0];
		leaveGame = leaveGameImages[0];
	}

	private void returnToGame() {
		setMusic(0);
		setSoundEffect(0);
		returnToGame = returnToGameImages[1];
		leaveGame = leaveGameImages[0];
	}

	private void leaveGame() {
		setMusic(0);
		setSoundEffect(0);
		returnToGame = returnToGameImages[0];
		leaveGame = leaveGameImages[1];
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
	 * Aggiorna il menù "music" con le impostazioni correnti. Non fa altro che
	 * richiamare il metodo "music()".
	 */
	public void refreshMusicOption() {
		music();
	}

	/**
	 * Aggiorna il menù "sound effect" con le impostazioni correnti. Non fa
	 * altro che richiamare il metodo "soundEffect()".
	 */
	public void refreshSoundEffectOption() {
		soundEffect();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(music, getX(music), getY(music, -135), null);
		g.drawImage(soundEffect, getX(soundEffect), getY(soundEffect, -45),
				null);
		g.drawImage(returnToGame, getX(returnToGame), getY(returnToGame, +45),
				null);
		g.drawImage(leaveGame, getX(leaveGame), getY(leaveGame, +135), null);
	}

}
