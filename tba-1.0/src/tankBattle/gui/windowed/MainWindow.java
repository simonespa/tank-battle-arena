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

import java.awt.GraphicsConfiguration;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.Utility;

/**
 * 
 * E' la finestra principale dalla quale sarà possibile cambiare le impostazioni
 * di gioco, far partire il server o il client.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class MainWindow extends JFrame {

	/** il riferimento al pannello principale **/
	private MainPanel mainPanel;
	/** il riferimento alla barra dei menù **/
	private MainMenuBar mainMenuBar;
	/** il riferimento al controller **/
	private GUIWindowedController controller;
	/** l'icona che compare nella finestra **/
	private Image icon;
	/** la larghezza della finestra **/
	private int width = 440;
	/** l'altezza della finestra **/
	private int heigth = 360;
	/** la configurazione grafica di default */
	private GraphicsConfiguration gConfig;

	/**
	 * Crea una finestra con i parametri già cablati al suo interno.
	 * 
	 * @param gConfig
	 *            la configurazione grafica di sistema.
	 */
	public MainWindow(GraphicsConfiguration gConfig) {
		// crea una finestra con un titolo ed una configurazione grafica di
		// default
		super("Tank Battle Arena", gConfig);
		// acquisisce la configurazione
		this.gConfig = gConfig;
		// setta il nome del componente
		setName("MainWindow");
		// setta l'icona del programma
		icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// setta la dimensione iniziale della finestra
		setSize(width, heigth);
		// posiziona la finestra al centro dello schermo
		setLocationRelativeTo(null);
		// imposta la finestra come non ridimensionabile
		setResizable(false);
		// impone alla finestra di chiudere il programma quando viene cliccato
		// il bottone "X"
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// inizializza gli attributi
		initFields();
		// imposta la menu bar
		setJMenuBar(mainMenuBar);
		// imposta il pannello
		setContentPane(mainPanel);
		// setta il look and feel
		setLookAndFeel();
		// rende la finestra visibile
		setVisible(true);
	}

	public void setMenuEnabled(boolean flag) {
		mainMenuBar.getMenu(0).getMenuComponent(0).setEnabled(flag);
		mainMenuBar.getMenu(1).getMenuComponent(0).setEnabled(flag);
	}

	/**
	 * Restituisce il controller.
	 * 
	 * @return il controller della GUI
	 */
	public GUIWindowedController getController() {
		return controller;
	}

	public void setMusic() {
		mainMenuBar.setMusic();
	}

	public void setSoundEffect() {
		mainMenuBar.setSoundEffect();
	}

	public void refreshLookAndFeel() {
		mainMenuBar.refreshLookAndFeel();
		setLookAndFeel();
	}

	/**
	 * Istanzia gli attributi della classe
	 */
	private void initFields() {
		controller = new GUIWindowedController(this);
		mainMenuBar = new MainMenuBar(controller, this);
		addWindowListener(controller);
		mainPanel = new MainPanel();
	}

	private void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(TankBattleGame.getLookAndFeelClass());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e) {
			Utility.logException(this, e);
		} catch (InstantiationException e) {
			Utility.logException(this, e);
		} catch (IllegalAccessException e) {
			Utility.logException(this, e);
		} catch (UnsupportedLookAndFeelException e) {
			Utility.logException(this, e);
		}
	}

}