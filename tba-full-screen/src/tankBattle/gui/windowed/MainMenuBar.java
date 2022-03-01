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

import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.SoundManager;

/**
 * 
 * @author Simone Spaccarotella
 * 
 *         E' la barra dei menù della finestra principale.
 * 
 */
public final class MainMenuBar extends JMenuBar {

	// la finestra principale
	private MainWindow mainWindow;

	// menu playerMode;
	private JMenu game;
	private JMenu newGame;
	private JMenuItem startServer;
	private JMenuItem joinServer;
	private JMenuItem exit;

	// menu option
	private JMenu option;
	private JMenuItem toFullScreen;
	private JMenu lookAndFeel;
	private JMenuItem music;
	private JMenuItem soundEffect;

	// menu help
	private JMenu help;
	private JMenuItem about;

	/* la lista dei menù corrispondenti ai look and feel istallati */
	private LinkedList<JMenuItem> lookAndFeelItems;
	/* il look and feel corrente */
	private String previousLookAndFeel;

	/**
	 * Richiama i metodi privati della classe che servono ad inizializzare gli
	 * attributi
	 * 
	 * @param actionListener
	 *            è l'ascoltatore degli eventi di tutti i menù
	 */
	public MainMenuBar(GUIWindowedController controller, MainWindow window) {
		super();
		this.mainWindow = window;
		initMenu();
		setIconMenu();
		addListener(controller);
		setDefaultLookAndFeel(controller);
		addMenu();
	}

	/**
	 * Inizializza tutti i menù
	 */
	private void initMenu() {
		game = new JMenu("Game");
		newGame = new JMenu("New game");
		startServer = new JMenuItem("Start server");
		joinServer = new JMenuItem("Join server");
		joinServer.setActionCommand("choose tank");
		exit = new JMenuItem("Exit");

		option = new JMenu("Option");
		toFullScreen = new JMenuItem("To full screen");
		lookAndFeel = new JMenu("Look and Feel");
		music = new JMenuItem();
		soundEffect = new JMenuItem();

		help = new JMenu("Help");
		about = new JMenuItem("About");
	}

	/**
	 * Setta le icone.
	 */
	private void setIconMenu() {
		/* game menu */
		ImageLoader r = ImageLoader.getInstance();
		newGame.setIcon(r.getGUIWindowedImage("newGame"));
		startServer.setIcon(r.getGUIWindowedImage("startServer"));
		joinServer.setIcon(r.getGUIWindowedImage("joinServer"));
		exit.setIcon(r.getGUIWindowedImage("exit"));

		/* option menu */
		toFullScreen.setIcon(r.getGUIWindowedImage("toFullScreen"));
		lookAndFeel.setIcon(r.getGUIWindowedImage("lookAndFeelBlue"));
		setMusic();
		setSoundEffect();

		/* help menu */
		about.setIcon(r.getGUIWindowedImage("about"));
	}

	/**
	 * Setta il testo e l'icona del menù "music", in base allo stato reale di
	 * ON/OFF.
	 */
	public void setMusic() {
		int musicState = TankBattleGame.getMusicState();
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (musicState == SoundManager.ON) {
			music.setText("Music ON");
			music.setIcon(imageLoader.getGUIWindowedImage("musicOn"));
		} else if (musicState == SoundManager.OFF) {
			music.setText("Music OFF");
			music.setIcon(imageLoader.getGUIWindowedImage("musicOff"));
		}
	}

	/**
	 * Setta il testo e l'icona del menù "sound effect", in base allo stato
	 * reale di ON/OFF.
	 */
	public void setSoundEffect() {
		int soundEffectState = TankBattleGame.getSoundEffectState();
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (soundEffectState == SoundManager.ON) {
			soundEffect.setText("Sound Effect ON");
			soundEffect.setIcon(imageLoader
					.getGUIWindowedImage("soundEffectOn"));
		} else if (soundEffectState == SoundManager.OFF) {
			soundEffect.setText("Sound Effect OFF");
			soundEffect.setIcon(imageLoader
					.getGUIWindowedImage("soundEffectOff"));
		}
	}

	/**
	 * Registra i menù all'ascoltatore
	 */
	private void addListener(GUIWindowedController controller) {
		startServer.addActionListener(controller);
		joinServer.addActionListener(controller);
		exit.addActionListener(controller);
		toFullScreen.addActionListener(controller);
		music.addActionListener(controller);
		soundEffect.addActionListener(controller);
		about.addActionListener(controller);
	}

	/**
	 * Setta il look and feel dell'applicazione con il look di Windows
	 */
	private void setDefaultLookAndFeel(GUIWindowedController controller) {
		// acquisisce i look and feel istallati
		LookAndFeelInfo[] installedLookAndFeelInfo = UIManager
				.getInstalledLookAndFeels();
		// istanzia la lista di menù relativi ai look and feel istallati
		lookAndFeelItems = new LinkedList<JMenuItem>();

		// crea un menù per ogni look and feel istallato
		for (LookAndFeelInfo l : installedLookAndFeelInfo) {
			JMenuItem item = new JMenuItem(l.getName());
			item.setName(l.getName());
			item.setActionCommand("lookAndFeel");
			item.addActionListener(controller);
			lookAndFeel.add(item);
			lookAndFeelItems.add(item);
		}
		previousLookAndFeel = TankBattleGame.getLookAndFeelName();
		refreshLookAndFeel();
	}

	/**
	 * Fa il refresh del look and feel e del menù che lo definisce.
	 */
	public void refreshLookAndFeel() {
		// acquisisce il look and feel di default
		String lookAndFeel = TankBattleGame.getLookAndFeelName();
		for (JMenuItem i : lookAndFeelItems) {
			if (i.getName().equals(previousLookAndFeel)) {
				i.setIcon(null);
				break;
			}
		}
		for (JMenuItem i : lookAndFeelItems) {
			if (i.getName().equals(lookAndFeel)) {
				i.setIcon(ImageLoader.getInstance().getGUIWindowedImage(
						"checked"));
				break;
			}
		}
		previousLookAndFeel = lookAndFeel;
	}

	/**
	 * Aggiunge ogni {@link JMenuItem} al proprio {@link JMenu}, ed infine
	 * aggiunge quest'ultimi alla barra dei menù ({@link JMenuBar}).
	 */
	private void addMenu() {
		// game
		newGame.add(startServer);
		newGame.add(joinServer);
		game.add(newGame);
		game.addSeparator();
		game.add(exit);

		// option
		option.add(toFullScreen);
		option.add(lookAndFeel);
		option.addSeparator();
		option.add(music);
		option.add(soundEffect);

		// help
		help.add(about);

		// Aggiunge i menu alla menu bar
		add(game);
		add(option);
		add(help);
	}

}