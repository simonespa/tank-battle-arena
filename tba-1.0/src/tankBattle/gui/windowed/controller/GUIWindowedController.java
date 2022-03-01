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

package tankBattle.gui.windowed.controller;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import tankBattle.controller.ControllerAdapter;
import tankBattle.gui.ChatPanel;
import tankBattle.gui.TankPanel;
import tankBattle.gui.windowed.AboutDialog;
import tankBattle.gui.windowed.ChooseTankWindow;
import tankBattle.gui.windowed.ChooseTeamWindow;
import tankBattle.gui.windowed.ConnectionWindow;
import tankBattle.gui.windowed.GameWindow;
import tankBattle.gui.windowed.JoinServerPanel;
import tankBattle.gui.windowed.JoinServerWindow;
import tankBattle.gui.windowed.MainWindow;
import tankBattle.gui.windowed.OptionWindow;
import tankBattle.gui.windowed.StartServerWindow;
import tankBattle.model.game.Collider;
import tankBattle.model.game.TankBattleGame;
import tankBattle.net.Endpoint;
import tankBattle.utilities.SoundManager;
import tankBattle.view.RootComponentView;

/**
 * L'istanza di questa classe si occupa della gestione degli eventi legati
 * strettamente all'interfaccia grafica del gioco.
 * 
 * @author Simone Spaccarotella
 */
public final class GUIWindowedController extends ControllerAdapter {

	private MainWindow mainWindow;
	private GameWindow gameWindow;
	private RootComponentView rootView;
	private TankPanel tankPanel;
	private ChatPanel chatPanel;
	private OptionWindow optionWindow;
	private StartServerWindow startServerWindow;
	private JoinServerWindow joinServerWindow;
	private ChooseTankWindow chooseTankWindow;
	private ConnectionWindow connectionWindow;

	/**
	 * Crea un controller per l'interfaccia grafica "Windowed". L'istanza di
	 * questa classe sarï¿½ l'unico oggetto controller per questa GUI, e
	 * gestirï¿½ tutti gli eventi che si possono verificare. Per fare ciï¿½,
	 * questa classe eredita da una classe Adapter, la quale implementa a corpo
	 * vuoto tutte le interfaccie disponibili per la gestione degli eventi. In
	 * questo modo possiamo utilizzare un solo ascoltatore, che gestisce ogni
	 * tipo di evento generato.
	 * 
	 * @param window
	 *            il riferimento alla finestra principale
	 * @see tankBattle.controller.ControllerAdapter
	 */
	public GUIWindowedController(MainWindow window) {
		mainWindow = window;
	}

	/**
	 * Setta la finestra di gioco.
	 * 
	 * @param gameWindow
	 *            la finestra di gioco.
	 */
	public void setGameWindow(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
		rootView = gameWindow.getGamePanel().getRootComponentView();
		tankPanel = gameWindow.getGamePanel().getStatePanel().getTankPanel();
		chatPanel = gameWindow.getGamePanel().getStatePanel().getChatPanel();
	}

	/**
	 * Questo metodo gestisce gli eventi generati.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equalsIgnoreCase("start server")) {
			startServer(event);
		} else if (command.equals("back server")) {
			backServer();
		} else if (command.equals("back to choose")) {
			backToChoose();
		} else if (command.equals("back to main")) {
			backToMain();
		} else if (command.equals("next to join")) {
			nextToJoin();
		} else if (command.equals("refresh")) {
			refresh();
		} else if (command.equals("Choose")) {
			chooseColor();
		} else if (command.equals("choose tank")) {
			chooseTank();
		} else if (command.equals("connectTo")) {
			connectTo();
		} else if (command.equals("Exit")) {
			exit();
		} else if (command.equalsIgnoreCase("To full screen")) {
			toFullScreen();
		} else if (command.equalsIgnoreCase("lookAndFeel")) {
			setLookAndFeel(event);
		} else if (command.equals("Music ON") || command.equals("Music OFF")) {
			setMusic(event);
		} else if (command.equals("Sound Effect ON")
				|| command.equals("Sound Effect OFF")) {
			setSoundEffect(event);
		} else if (command.equals("About")) {
			about();
		} else if (command.equals("Return to Game")) {
			returnGame();
		} else if (command.equals("Leave Game")) {
			leaveGame();
		} else if (command.equals("debug")) {
			debug(event);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			TankBattleGame.sendMessage(chatPanel.flush());
			gameWindow.requestFocus();
		}
	}

	/**
	 * Effettua delle operazioni di setup prima di chiudere la finestra che ha
	 * generato questo evento.
	 ****/
	@Override
	public void windowClosing(WindowEvent e) {
		// acquisisce il riferimento alla finestra
		Window window = e.getWindow();
		// controlla che tipo di finestra ï¿½
		if (window.getName().equals("MainWindow")) {
			TankBattleGame.closeGUI();
		} else if (window.getName().equals("StartServerWindow")) {
			if (!mainWindow.isVisible()) {
				mainWindow.setVisible(true);
			}
			mainWindow.setMenuEnabled(true);
			TankBattleGame.stopServer();
		} else if (window.getName().equals("JoinServerWindow")) {
			backToChoose();
		} else if (window.getName().equals("OptionWindow")) {
			gameWindow.setVisible(true);
		}
	}

	/**
	 * Gestisce gli eventi doppio click sulle righe della tabella
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// se è stato fatto doppio click
		if (e.getClickCount() == 2) {
			// chiude le due finestre
			joinServerWindow.dispose();
			chooseTankWindow.dispose();
			// acquisisce la tabella
			JTable target = (JTable) e.getSource();
			// prende la linea selezionata
			int row = target.getSelectedRow();
			// acquisisce la lista dei server
			Vector<Endpoint> serverList = TankBattleGame.getServerList();
			Endpoint endpoint = serverList.elementAt(row);
			TankBattleGame.connectClientTo(endpoint);
		}
	}

	/**
	 * Quando la finestra diventa attiva (acquisisce il focus), bisogna
	 * riaggiornare lo stato della grafica.
	 ****/
	@Override
	public void windowActivated(WindowEvent e) {
		mainWindow.setMusic();
		mainWindow.setSoundEffect();
	}

	/**
	 * Gestisce gli eventi generati dalla perdita del focus delle combo box.
	 */
	@Override
	public void focusLost(FocusEvent e) {
		Object object = e.getSource();
		// se l'oggetto ï¿½ il campo di testo in cui inserire il nome
		if (object instanceof JTextField) {
			// fai il cast dell'oggetto
			JTextField text = (JTextField) object;
			// memorizza il nome del tank
			TankBattleGame.setTankName(text.getText());
			// se invece l'oggetto ï¿½ una delle 3 combobox che
			// elencano la modalitï¿½ di gioco
		} else if (object instanceof JComboBox) {
			// effettua il cast dell'oggetto
			JComboBox combo = (JComboBox) object;
			String componentName = combo.getName();
			// controlla quale dei tre combo box ha perso il focus
			// e memorizza il relativo valore
			if (componentName.equals("JComboBox.Type")) {
				TankBattleGame.setType((String) combo.getSelectedItem());
			} else if (componentName.equals("JComboBox.Mode")) {
				TankBattleGame.setMode((String) combo.getSelectedItem());
			} else if (componentName.equals("JComboBox.Map")) {
				TankBattleGame.setMap((String) combo.getSelectedItem());
			}
		}
	}

	/**
	 * Metodo ereditato dall'Controller Adapter. Questo metodo mette in pausa il
	 * gioco, effettuando le opportune operazioni di setup.
	 */
	@Override
	public void pause() {
		gameWindow.setVisible(false);
		optionWindow = new OptionWindow(this, mainWindow
				.getGraphicsConfiguration());
		SoundManager.player().pause();
	}

	/**
	 * Passa il controllo alla barra di stato.
	 * 
	 * &Egrave; possibile inviare un messaggio di chat a tutti i componenti del
	 * gioco, grazie all'area di testo.
	 */
	public void chat() {
		chatPanel.requestFocus();
	}

	/**
	 * Riceve il messaggio e lo stampa sulla console.
	 * 
	 * @param message
	 *            il messaggio da visualizzare.
	 */
	public void receiveMessage(String message) {
		chatPanel.printConsole(message);
	}

	/**
	 * Fa partire il server.
	 */
	private void startServer(ActionEvent event) {
		// se l'evento è stato generato dal menu a tendina
		if (event.getSource() instanceof JMenuItem) {
			startServerWindow = new StartServerWindow(mainWindow, this);
			mainWindow.setMenuEnabled(false);
			TankBattleGame.setGameMode("server");
			/*
			 * altrimenti se è stato generato dal bottone nella finestra
			 * "Start Server"
			 */
		} else if (event.getSource() instanceof JButton) {
			// se ogni menù ha un valore valido
			if (TankBattleGame.typeIsNotNull()
					&& TankBattleGame.modeIsNotNull()
					&& TankBattleGame.mapIsNotNull()) {
				// se è stato selezionato il valore "team"
				if (TankBattleGame.getType().equalsIgnoreCase("team")) {
					/*
					 * Avvia una finestra di dialogo dalla quale è possibile
					 * scegliere una squadra. Passa il riferimento alla finestra
					 * "StartServerWindow" solo per posizionare la finestra
					 * centrata rispetto ad essa.
					 */
					ChooseTeamWindow.showWindow(this);
				} else {
					mainWindow.setMenuEnabled(true);
					mainWindow.setVisible(false);
					startServerWindow.dispose();
					startServerWindow = null;
					// fa partire il server
					TankBattleGame.startServerGame();
				}
			}
		}
	}

	/**
	 * Chiude la finestra che fa partire il server e visualizza nuovamente la
	 * finestra del menï¿½
	 */
	private void backServer() {
		startServerWindow.dispose();
		mainWindow.setVisible(true);
		mainWindow.setMenuEnabled(true);
		TankBattleGame.stopServer();
	}

	/**
	 * Apre la finestra di scelta del tank
	 */
	private void chooseTank() {
		chooseTankWindow = new ChooseTankWindow(mainWindow, this);
		mainWindow.setMenuEnabled(false);
		TankBattleGame.setGameMode("client");
	}

	private void connectTo() {
		connectionWindow = new ConnectionWindow(mainWindow, this);
	}

	/**
	 * Dopo aver effettuato la scelta del tank passa il controllo alla finestra
	 * di scelta del server
	 */
	private void nextToJoin() {
		joinServerWindow = new JoinServerWindow(mainWindow, this);
		TankBattleGame.setJoinServerPanel((JoinServerPanel) joinServerWindow
				.getContentPane());
		mainWindow.setVisible(false);
		chooseTankWindow.setVisible(false);
		TankBattleGame.startClient();
	}

	/**
	 * Chiude la finestra che fa partire il client e visualizza la finestra di
	 * scelta del tank
	 */
	private void backToChoose() {
		TankBattleGame.stopClient();
		joinServerWindow.dispose();
		if (!mainWindow.isVisible()) {
			mainWindow.setVisible(true);
		}
		chooseTankWindow.setVisible(true);
	}

	/**
	 * Chiude la finestra di scelta del tank e riporta il controllo alla
	 * finestra principale
	 */
	private void backToMain() {
		mainWindow.setVisible(true);
		mainWindow.setMenuEnabled(true);
		chooseTankWindow.dispose();
	}

	/**
	 * Aggiorna la lista dei server.
	 */
	private void refresh() {
		JoinServerPanel panel = (JoinServerPanel) joinServerWindow
				.getContentPane();
		panel.removeAllRow();
		TankBattleGame.refreshServerList();
	}

	private void chooseColor() {
		Color color = JColorChooser.showDialog(startServerWindow, mainWindow
				.getTitle()
				+ " | color chooser", null);
		TankBattleGame.setTankColor(color);
		if (startServerWindow != null) {
			startServerWindow.setButtonColor(color);
		} else if (chooseTankWindow != null) {
			chooseTankWindow.setButtonColor(color);
		}
	}

	/**
	 * Chiude il gioco.
	 */
	private void exit() {
		TankBattleGame.saveProperties(); // salva le impostazioni di sistema
		System.exit(0); // restituisce al sistema un messaggio di OK
	}

	/**
	 * Setta il testo è lo stato "selected" del menù "debug" ed imposta il
	 * relativo parametro per rendere questo stato persistente.
	 * 
	 * @param event
	 *            l'evento generato.
	 */
	private void debug(ActionEvent event) {
		JCheckBoxMenuItem source = (JCheckBoxMenuItem) event.getSource();
		if (source.isSelected()) {
			TankBattleGame.setDebugMode(true);
			source.setText("debug on");
		} else {
			TankBattleGame.setDebugMode(false);
			source.setText("debug off");
		}
	}

	/**
	 * Setta il look and feel del gioco.
	 */
	private void setLookAndFeel(ActionEvent event) {
		JMenuItem item = (JMenuItem) event.getSource();
		String lookAndFeelName = item.getName();
		TankBattleGame.setLookAndFeel(lookAndFeelName);
		mainWindow.refreshLookAndFeel();
	}

	/**
	 * Passa nuovamente il controllo alla schermata di gioco.
	 */
	private void returnGame() {
		optionWindow.dispose();
		gameWindow.setVisible(true);
		SoundManager.player().returnToGame();
	}

	/**
	 * Esce dalla schermata di gioco, chiedendo una conferma e passa il
	 * controllo alla finestra dei menï¿½.
	 */
	private void leaveGame() {
		// mostra una finestra di dialogo
		int option = JOptionPane.showConfirmDialog(optionWindow,
				"Are you really sure to escape from the game?", mainWindow
						.getTitle()
						+ " | Leave game", JOptionPane.YES_NO_OPTION);
		// se viene premuto "SI" entra nell'if
		if (option == 0) {
			// chiude la finestra di gioco
			TankBattleGame.stopGame();
			optionWindow.dispose();
			optionWindow = null;
			leave();
		}
	}

	/**
	 * Viene richiamata dal client, quando viene chiusa l'applicazione
	 */
	public void leave() {
		SoundManager.player().returnToMenu();
		TankBattleGame.stopServer();
		if (gameWindow != null) {
			gameWindow.stopRepainter();
			gameWindow.getGamePanel().getRootComponentView().removeTank(
					TankBattleGame.getTank());
			gameWindow.dispose();
			gameWindow = null;
		}
		Collider.removeAllObstacles();
		TankBattleGame.generateMapID();
		TankBattleGame.removeLocalTank();
		mainWindow.setVisible(true);
		mainWindow.setMenuEnabled(true);
		// rimuove le righe della tabella del join server se è stato creato.
		if (joinServerWindow != null) {
			JoinServerPanel panel = (JoinServerPanel) joinServerWindow
					.getContentPane();
			panel.removeAllRow();
		}
	}

	/**
	 * Restituisce la finestra principale.
	 * 
	 * @return la finestra principale.
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}

	/**
	 * Restituisce la finestra delle opzioni.
	 * 
	 * @return una finestra di tipo {@link tankBattle.gui.windowed.OptionWindow
	 *         OptionWindow}.
	 */
	public OptionWindow getOptionWindow() {
		return optionWindow;
	}

	/**
	 * Restituisce la finestra che fa partire il server.
	 * 
	 * @return una finestra di tipo
	 *         {@link tankBattle.gui.windowed.StartServerWindow
	 *         StartServerWindow}.
	 */
	public StartServerWindow getStartServerWindow() {
		return startServerWindow;
	}

	/**
	 * Passa dalla modalit&agrave; "Windowed" a la modalit&agrave; "Full Screen"
	 */
	private void toFullScreen() {
		TankBattleGame.toFullScreen();
	}

	/**
	 * Setta l'icona e il testo del menï¿½ "music" in base al reale stato di
	 * ON/OFF.
	 */
	private void setMusic(ActionEvent event) {
		int music = TankBattleGame.getMusicState();
		if (music == SoundManager.ON) {
			TankBattleGame.setMusicState(SoundManager.OFF);
		} else if (music == SoundManager.OFF) {
			TankBattleGame.setMusicState(SoundManager.ON);
		}
		if (event != null && event.getSource() instanceof JToggleButton) {
			optionWindow.setMusic();
		}
		mainWindow.setMusic();
	}

	/**
	 * Setta l'icona e il testo del menï¿½ "sound effect" in base al reale stato
	 * di ON/OFF
	 */
	private void setSoundEffect(ActionEvent event) {
		int soundEffect = TankBattleGame.getSoundEffectState();
		if (soundEffect == SoundManager.ON) {
			TankBattleGame.setSoundEffectState(SoundManager.OFF);
		} else if (soundEffect == SoundManager.OFF) {
			TankBattleGame.setSoundEffectState(SoundManager.ON);
		}
		if (event != null && event.getSource() instanceof JToggleButton) {
			optionWindow.setSoundEffect();
		}
		mainWindow.setSoundEffect();
	}

	/**
	 * Fa partire la finestra di "help"
	 */
	private void about() {
		new AboutDialog(mainWindow);
	}

}