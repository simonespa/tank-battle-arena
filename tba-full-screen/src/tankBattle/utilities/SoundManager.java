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

package tankBattle.utilities;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

import tankBattle.model.game.TankBattleGame;

/**
 * Gestisce l'audio e gli effetti sonori del gioco. &Egrave; implementata
 * secondo il pattern {@code Singleton}.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class SoundManager implements LocalSoundLoadable {

	private Clip menu;
	private Clip game;
	private Clip backgroundNoise;
	private boolean pause;

	/** ON */
	public static final int ON = 1;
	/** OFF */
	public static final int OFF = 0;
	/* il riferimento all'istanza di questa classe */
	private static SoundManager soundManager;

	/**
	 * Carica le risorse audio.
	 */
	private SoundManager() {
		menu = load("menu.wav");
	}

	/**
	 * Restituisce l'istanza di questa classe. Se non è ancora stat creata, la
	 * istanzia e la restituisce.
	 * 
	 * @return un'istanza di questa classe.
	 */
	public static SoundManager player() {
		if (soundManager == null) {
			soundManager = new SoundManager();
		}
		return soundManager;
	}

	/**
	 * Riproduce l'audio dell livello di gioco appropriato.
	 * 
	 * @param level
	 */
	public void playSoundLevel(String level) {
		if (level.equalsIgnoreCase("desert")) {
			game = load("desert.wav");
		} else if (level.equalsIgnoreCase("grassland")) {
			game = load("grassland.wav");
		} else if (level.equalsIgnoreCase("glacier")) {
			game = load("glacier.wav");
		} else if (level.equalsIgnoreCase("metal arena")) {
			game = load("metalArena.wav");
		}
		if (TankBattleGame.getMusicState() == ON) {	
			game.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Riproduce l'audio del menù in loop.
	 */
	public void playMenu() {
		if (TankBattleGame.getMusicState() == ON) {
			menu.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Stoppa l'audio del menù.
	 */
	public void stopMenu() {
		menu.stop();
	}

	public void startGame() {
		pause = false;
		menu.stop();
		playSoundLevel(TankBattleGame.getMap());
		playBackgroundNoise();
	}

	public void pause() {
		pause = true;
		if (TankBattleGame.getMusicState() == ON) {
			game.stop();
			menu.loop(Clip.LOOP_CONTINUOUSLY);
		}
		if (TankBattleGame.getSoundEffectState() == ON) {
			if (backgroundNoise != null && backgroundNoise.isActive()) {
				stopBackgroundNoise();
			}
		}
	}

	public void returnToGame() {
		pause = false;
		if (TankBattleGame.getMusicState() == ON) {
			menu.stop();
			game.loop(Clip.LOOP_CONTINUOUSLY);
		}
		if (TankBattleGame.getSoundEffectState() == ON) {
			backgroundNoise.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void returnToMenu() {
		game.close();
		backgroundNoise.close();
		playMenu();
	}

	/* ------------------------- EFFETTI SONORI ------------------------- */

	/**
	 * Riproduce l'effetto sonoro dello sparo.
	 */
	public void shoot() {
		if (TankBattleGame.getSoundEffectState() == ON && !pause) {
			load("shoot.wav").start();
		}
	}

	/**
	 * Fa partire l'effetto del motore.
	 */
	private void playBackgroundNoise() {
		backgroundNoise = load("tankEngine.wav");
		if (TankBattleGame.getSoundEffectState() == ON) {
			backgroundNoise.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	/**
	 * Mette in pausa l'effetto sonoro del motore.
	 */
	private void stopBackgroundNoise() {
		if (backgroundNoise != null) {
			if (backgroundNoise.isActive()) {
				backgroundNoise.stop();
			}
		}
	}

	/**
	 * Chiude l'effetto sonoro del motore.
	 */
	private void closeBackgroundNoise() {
		if (backgroundNoise != null) {
			if (backgroundNoise.isOpen()) {
				backgroundNoise.close();
			}
		}
	}

	/**
	 * Riproduce l'effetto sonoro dell'esplosione del tank.
	 */
	public void tankExplosion() {
		if (TankBattleGame.getSoundEffectState() == ON && !pause) {
			load("tankExplosion.wav").start();
		}
	}

	public void bulletVsTank() {
		if (TankBattleGame.getSoundEffectState() == ON && !pause) {
			load("bulletVsTank.wav").start();
		}
	}

	public void bulletVsObstacle() {
		if (TankBattleGame.getSoundEffectState() == ON && !pause) {
			load("bulletVsObstacle.wav").start();
		}
	}

	/*
	 * Carica il file della risorsa audio.
	 * 
	 * @param fileName il nome del file.
	 * 
	 * @return restituisce un oggetto riproducibile.
	 */
	private Clip load(String fileName) {
		File soundFile = new File(soundsPath + fileName);
		AudioInputStream audioInputStream = null;
		Clip clip = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (audioInputStream != null) {
			AudioFormat format = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			try {
				clip = (Clip) AudioSystem.getLine(info);
				clip.open(audioInputStream);
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return clip;
	}

	/**
	 * Chiude l'audio e gli effetti di gioco.
	 */
	public void closeAll() {
		if (menu != null)
			menu.close();
		if (game != null)
			game.close();
	}

}