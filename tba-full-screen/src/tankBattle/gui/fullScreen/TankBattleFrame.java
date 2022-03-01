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

import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import tankBattle.controller.TankController;
import tankBattle.gui.GamePanel;
import tankBattle.gui.fullScreen.controller.GUIFullScreenController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * 
 * @author Simone Spaccarotella
 *
 */
public final class TankBattleFrame extends JFrame {

	/* l'icona del programma */
	private Image icon;

	/* il pannello correntemente visualizzato */
	private int currentPanel;
	private boolean newGame;
	private boolean fromGamePanel;

	/* sono i pannelli che questa finestra puï¿½ visualizzare */
	private AnimationPanel animationPanel;
	private MainPanel mainPanel;
	private OptionPanel optionPanel;
	private StartServerPanel startServerPanel;
	private NextStepPanel nextStepPanel;
	private JoinServerPanel joinServerPanel;
	private GamePanel gamePanel;
	private GamingOptionPanel gamingOptionPanel;
	private LeaveGamePanel leaveGamePanel;

	/* ---------- CODICI IDENTIFICATVI DEI PANNELLI ---------- */

	/** &Egrave; lo Splash Screen. */
	public static final int ANIMATION_PANEL = 0;
	/** &Egrave; il pannello principale. */
	public static final int MAIN_PANEL = 1;
	/** &Egrave; il pannello delle opzioni. */
	public static final int OPTION_PANEL = 2;
	/** &Egrave; il pannello del Server. */
	public static final int START_SERVER_PANEL = 3;
	/**  */
	public static final int NEXT_STEP_PANEL = 4;
	/** &Egrave; il pannello del Client. */
	public static final int JOIN_SERVER_PANEL = 5;
	/** &Egrave; il pannello di gioco. */
	public static final int GAME_PANEL = 6;
	/** &Egrave; il pannello delle opzioni da cui si accede dal game panel */
	public static final int GAMING_OPTION_PANEL = 7;
	/** &Egrave; il pannello di conferma dal quale ï¿½ possibile uscire dal gioco */
	public static final int LEAVE_GAME_PANEL = 8;

	/* ------------------------------------------------------- */

	/**
	 * &Egrave; l'ascissa centrale dello schermo, in funzione della risoluzione
	 * dello schermo.
	 */
	public static final int CENTER_X = (Toolkit.getDefaultToolkit()
			.getScreenSize().width / 2);
	/**
	 * &Egrave; l'ordinata centrale dello schermo, in funzione della risoluzione
	 * dello schermo.
	 */
	public static final int CENTER_Y = (Toolkit.getDefaultToolkit()
			.getScreenSize().height / 2);

	/** &Egrave; il controller dell'interfaccia grafica */
	private GUIFullScreenController guiController;
	/** &Egrave; il controller del Tank */
	private TankController tankController;

	/**
	 * Crea una finestra a tutto schermo. Questa finestra visualizza i diversi
	 * men&ugrave; in pannelli separati.
	 * 
	 * @param gConf
	 *            la configurazione grafica di default
	 */
	public TankBattleFrame(GraphicsConfiguration gConf) {
		super("Tank Battle Arena", gConf);
		// setta l'icona del programma
		icon = ImageLoader.getInstance().getIcon();
		setIconImage(icon);
		// acquisisce il controller della GUI
		guiController = new GUIFullScreenController(this);
		addKeyListener(guiController);
		// setta il pannello
		animationPanel();
		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public GUIFullScreenController getController() {
		return guiController;
	}

	/**
	 * Lo splash screen.
	 */
	private void animationPanel() {
		currentPanel = ANIMATION_PANEL;
		animationPanel = new AnimationPanel(this);
		setContentPane(animationPanel);
		validate();
		new Thread(animationPanel).start();
	}

	/**
	 * Il pannello principale.
	 */
	public void mainPanel() {
		currentPanel = MAIN_PANEL;
		if (mainPanel == null) {
			mainPanel = new MainPanel(this);
		}
		setContentPane(mainPanel);
		validate();
	}

	/**
	 * Il pannello da cui ï¿½ possibile settare le opzioni dell'ambiente grafico.
	 */
	public void optionPanel() {
		currentPanel = OPTION_PANEL;
		if (optionPanel == null) {
			optionPanel = new OptionPanel(this);
		}
		setContentPane(optionPanel);
		validate();
	}

	/**
	 * Il pannello da cui vengono settati i parametri di gioco e viene avviato
	 * il server.
	 */
	public void startServerPanel() {
		newGame = true;
		currentPanel = START_SERVER_PANEL;
		if (startServerPanel == null) {
			startServerPanel = new StartServerPanel(this);
		}
		setContentPane(startServerPanel);
		validate();
	}

	public void nextStepPanel() {
		currentPanel = NEXT_STEP_PANEL;
		if (nextStepPanel == null) {
			nextStepPanel = new NextStepPanel(this);
		}
		setContentPane(nextStepPanel);
		validate();
	}

	/**
	 * Il pannello da cui vengono cercati i server a cui agganciarsi per poter
	 * giocare.
	 */
	public void joinServerPanel() {
		currentPanel = JOIN_SERVER_PANEL;
		if (joinServerPanel == null) {
			joinServerPanel = new JoinServerPanel(this);
		}
		setContentPane(joinServerPanel);
		validate();
	}

	/**
	 * Il pannello di gioco, su cui avviene lo svolgimento delle azioni.
	 */
	public void gamePanel() {
		fromGamePanel = true;
		currentPanel = GAME_PANEL;
		if (newGame) {
			gamePanel = new GamePanel(null);
			tankController = new TankController(TankBattleGame.getTank(), TankBattleGame.getOutputStream());
			newGame = false;
		}
		setContentPane(gamePanel);
		removeKeyListener(guiController);
		addKeyListener(tankController);
		validate();
	}

	public void gamingOptionPanel() {
		currentPanel = GAMING_OPTION_PANEL;
		if (gamingOptionPanel == null) {
			gamingOptionPanel = new GamingOptionPanel(this);
		}
		setContentPane(gamingOptionPanel);
		if (fromGamePanel) {
			removeKeyListener(tankController);
			addKeyListener(guiController);
		}
		validate();
	}

	public void leaveGamePanel() {
		fromGamePanel = false;
		currentPanel = LEAVE_GAME_PANEL;
		leaveGamePanel = new LeaveGamePanel(this);
		setContentPane(leaveGamePanel);
		validate();
	}

	/**
	 * Restituisce il pannello correntemente selezionato.
	 * 
	 * @return l'identificatore del pannello
	 */
	public int getCurrentPanelState() {
		return currentPanel;
	}

}
