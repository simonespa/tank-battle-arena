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

package tankBattle.gui.fullScreen.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import tankBattle.controller.ControllerAdapter;
import tankBattle.gui.fullScreen.GamingOptionPanel;
import tankBattle.gui.fullScreen.LeaveGamePanel;
import tankBattle.gui.fullScreen.MainPanel;
import tankBattle.gui.fullScreen.NextStepPanel;
import tankBattle.gui.fullScreen.OptionPanel;
import tankBattle.gui.fullScreen.StartServerPanel;
import tankBattle.gui.fullScreen.TankBattleFrame;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.SoundManager;

/** 
 * 
 * E' il controller della GUI Full Screen. Questa classe gestisce tutti gli
 * eventi generati dai menù della grafica.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class GUIFullScreenController extends ControllerAdapter {

	private TankBattleFrame mainWindow;

	/**
	 * Crea un controller, e passa il riferimento della finestra che conterrà i
	 * pannelli da gestire.
	 * 
	 * @param frame
	 *            la finestra principale
	 */
	public GUIFullScreenController(TankBattleFrame frame) {
		this.mainWindow = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("JTextField:nameArea")) {
			NextStepPanel panel = (NextStepPanel) mainWindow.getContentPane();
			TankBattleGame.setTankName(panel.getTankName());
			panel.setTextFieldEnabled(false);
			mainWindow.requestFocus();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int currentPanel = mainWindow.getCurrentPanelState();
		if (currentPanel == TankBattleFrame.MAIN_PANEL) {
			mainPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.OPTION_PANEL) {
			optionPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.START_SERVER_PANEL) {
			startServerPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.NEXT_STEP_PANEL) {
			nextStepPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.JOIN_SERVER_PANEL) {
			joinServerPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.GAME_PANEL) {
			gamePanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.GAMING_OPTION_PANEL) {
			gamingOptionPanel(e.getKeyCode());
		} else if (currentPanel == TankBattleFrame.LEAVE_GAME_PANEL) {
			leaveGamePanel(e.getKeyCode());
		}
	}

	/**
	 * Gestisce il pannello principale.
	 * 
	 * @param keyCode
	 *            il codice del tasto premuto
	 */
	private void mainPanel(int keyCode) {
		MainPanel panel = (MainPanel) mainWindow.getContentPane();
		if (keyCode == KeyEvent.VK_DOWN) {
			panel.selectDown();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_UP) {
			panel.selectUp();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_ENTER) {
			int currentMenu = panel.getCurrentMenu();
			if (currentMenu == MainPanel.START_SERVER) {
				mainWindow.startServerPanel();
			} else if (currentMenu == MainPanel.JOIN_SERVER) {
				mainWindow.joinServerPanel();
			} else if (currentMenu == MainPanel.OPTION) {
				mainWindow.optionPanel();
			} else if (currentMenu == MainPanel.EXIT) {
				/*
				 * quando si esce dal gioco vengono salvate su file le
				 * impostazioni di gioco dell'interfaccia grafica.
				 */
				TankBattleGame.saveProperties();
				SoundManager.player().closeAll();
				System.exit(0);
			}
		}
	}

	/**
	 * Gestisce il pannello delle opzioni.
	 * 
	 * @param keyCode
	 *            il codice del tasto premuto.
	 */
	private void optionPanel(int keyCode) {
		OptionPanel panel = (OptionPanel) mainWindow.getContentPane();
		if (keyCode == KeyEvent.VK_DOWN) {
			panel.selectDown();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_UP) {
			panel.selectUp();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_ENTER) {
			int currentMenu = panel.getCurrentMenu();
			if (currentMenu == OptionPanel.TO_WINDOWED) {
				toWindowed();
			} else if (currentMenu == OptionPanel.MUSIC) {
				setMusic();
				panel.refreshMusicMenu();
				panel.repaint();
			} else if (currentMenu == OptionPanel.SOUND_EFFECT) {
				setSoundEffect();
				panel.refreshSoundEffectMenu();
				panel.repaint();
			} else if (currentMenu == OptionPanel.BACK) {
				mainWindow.mainPanel();
			}
		}
	}

	/**
	 * Gestisce il pannello del server.
	 * 
	 * @param keyCode
	 *            il codice del tasto premuto
	 */
	private void startServerPanel(int keyCode) {
		// acquisisce il pannello
		StartServerPanel panel = (StartServerPanel) mainWindow.getContentPane();
		// se viene premuto il tasto freccia "up"
		if (keyCode == KeyEvent.VK_UP) {
			// viene selezionato il menù in alto
			panel.selectUp();
			// viene ridisegnato il pannello
			panel.repaint();
			// se viene premuto il tasto freccia "down"
		} else if (keyCode == KeyEvent.VK_DOWN) {
			// viene selezionato il menù in basso
			panel.selectDown();
			panel.repaint();
			// se viene premuto il tasto freccia "left"
		} else if (keyCode == KeyEvent.VK_LEFT) {
			// ci troviamo sul menù "game" e il pannello è selezionato
			if (panel.getCurrentMenu() == StartServerPanel.GAME
					&& panel.isSelectedSubMenu()) {
				// seleziona il sotto menù di sinistra
				panel.selectLeft();
				panel.repaint();
			}
			// fa la stessa cosa per il sotto menù di destra
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			if (panel.getCurrentMenu() == StartServerPanel.GAME
					&& panel.isSelectedSubMenu()) {
				panel.selectRight();
				panel.repaint();
			}
			// se viene premuto il tasto "invio/enter"
		} else if (keyCode == KeyEvent.VK_ENTER) {
			// se ci troviamo sul menù "game"
			if (panel.getCurrentMenu() == StartServerPanel.GAME) {
				// viene "selezionato/deselezionato"
				panel.selectDeselectSubMenu();
				panel.game();
				panel.repaint();
				// se ci troviamo sul menù "next step"
			} else if (panel.getCurrentMenu() == StartServerPanel.NEXT_STEP) {
				panel.storeTypeModeMap();
				mainWindow.nextStepPanel();
			} else if (panel.getCurrentMenu() == StartServerPanel.BACK) {
				// stoppa il server
				TankBattleGame.stopServer();
				// passa il controllo al pannello principale
				mainWindow.mainPanel();
			}
		}
	}

	private void nextStepPanel(int keyCode) {
		NextStepPanel panel = (NextStepPanel) mainWindow.getContentPane();
		if (keyCode == KeyEvent.VK_DOWN) {
			panel.selectDown();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_UP) {
			panel.selectUp();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_ENTER) {
			int currentMenu = panel.getCurrentMenu();
			if (currentMenu == NextStepPanel.NAME) {
				// abilità il componente JTextField
				panel.setTextFieldEnabled(true);
				// richiede il focus per il componente JTextfield
				panel.requestTextFieldFocus();
			} else if (currentMenu == NextStepPanel.START) {
				TankBattleGame.startServer();
				mainWindow.gamePanel();
			} else if (currentMenu == NextStepPanel.BACK) {
				mainWindow.startServerPanel();
			}
		}
	}

	private void joinServerPanel(int keyCode) {

	}

	private void gamePanel(int keyCode) {

	}

	private void gamingOptionPanel(int keyCode) {
		GamingOptionPanel panel = (GamingOptionPanel) mainWindow
				.getContentPane();
		if (keyCode == KeyEvent.VK_DOWN) {
			panel.selectDown();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_UP) {
			panel.selectUp();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_ENTER) {
			int currentMenu = panel.getCurrentMenu();
			if (currentMenu == GamingOptionPanel.MUSIC) {
				setMusic();
				panel.refreshMusicOption();
				panel.repaint();
			} else if (currentMenu == GamingOptionPanel.SOUND_EFFECT) {
				setSoundEffect();
				panel.refreshSoundEffectOption();
				panel.repaint();
			} else if (currentMenu == GamingOptionPanel.RETURN_TO_GAME) {
				mainWindow.gamePanel();
			} else if (currentMenu == GamingOptionPanel.LEAVE_GAME) {
				mainWindow.leaveGamePanel();
			}
		}
	}

	private void leaveGamePanel(int keyCode) {
		LeaveGamePanel panel = (LeaveGamePanel) mainWindow.getContentPane();
		if (keyCode == KeyEvent.VK_LEFT) {
			panel.select();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_RIGHT) {
			panel.select();
			panel.repaint();
		} else if (keyCode == KeyEvent.VK_ENTER) {
			int currentMenu = panel.getCurrentMenu();
			if (currentMenu == LeaveGamePanel.YES) {
				mainWindow.startServerPanel();
			} else if (currentMenu == LeaveGamePanel.NO) {
				mainWindow.gamingOptionPanel();
			}
		}
	}

	/**
	 * Passa dalla modalità "Full Screen" alla modalità "Windowed"
	 */
	private void toWindowed() {
		TankBattleGame.toWindowed();
	}

	/**
	 * Attiva e disattiva la musica.
	 */
	private void setMusic() {
		int music = TankBattleGame.getMusicState();
		if (music == SoundManager.ON) {
			TankBattleGame.setMusicState(SoundManager.OFF);
		} else if (music == SoundManager.OFF) {
			TankBattleGame.setMusicState(SoundManager.ON);
		}
	}

	/**
	 * Attiva e disattiva gli effetti sonori.
	 */
	private void setSoundEffect() {
		int soundEffect = TankBattleGame.getSoundEffectState();
		if (soundEffect == SoundManager.ON) {
			TankBattleGame.setSoundEffectState(SoundManager.OFF);
		} else if (soundEffect == SoundManager.OFF) {
			TankBattleGame.setSoundEffectState(SoundManager.ON);
		}
	}

	@Override
	public void pause() {
		mainWindow.gamingOptionPanel();
	}

}
