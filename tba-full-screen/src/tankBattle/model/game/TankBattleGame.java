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

package tankBattle.model.game;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JFrame;

import tankBattle.controller.TankController;
import tankBattle.gui.GamePanel;
import tankBattle.gui.GameStatePanel;
import tankBattle.gui.fullScreen.OptionPanel;
import tankBattle.gui.fullScreen.TankBattleFrame;
import tankBattle.gui.fullScreen.controller.GUIFullScreenController;
import tankBattle.gui.windowed.ConsolePanel;
import tankBattle.gui.windowed.GameWindow;
import tankBattle.gui.windowed.JoinServerPanel;
import tankBattle.gui.windowed.MainWindow;
import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.component.Tank;
import tankBattle.net.ClientServerCapable;
import tankBattle.net.Endpoint;
import tankBattle.net.client.Client;
import tankBattle.net.server.Server;
import tankBattle.utilities.GUIProperties;
import tankBattle.utilities.GameProperties;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.SoundManager;
import tankBattle.utilities.Utility;

/**
 * Questa classe rappresenta il gioco vero e proprio. Da qui &egrave; possibile gestire
 * sia l'interfaccia grafica che le propriet&agrave; del gioco stesso.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class TankBattleGame implements ClientServerCapable {

	/* --------------- CONFIGURAZIONI GRAFICHE E PROPRIETA' --------------- */
	// la configurazione grafica di default
	private static GraphicsConfiguration gConf;
	// il valore selezionato nelle combo box "type", "mode", "map"
	private static String type;
	private static String mode;
	private static String map;
	// indica se si sta giocando in modalitï¿½ "server" o "client"
	private static String gameMode;
	// le proprietï¿½ del gioco
	private static GameProperties gameProperties;
	// le proprietï¿½ della GUI
	private static GUIProperties guiProperties;
	/* -------------------------------------------------------------------- */

	/* ---------------------- ELEMENTI DELLA GUI -------------------------- */
	// la finestra principale
	private static MainWindow guiWindowed;
	// il controller della GUI Windowed
	private static GUIWindowedController windowedController;

	// la finestra principale della GUI Full Screen
	private static TankBattleFrame guiFullScreen;
	// il controller della GUI Full Screen
	private static GUIFullScreenController fullSreenController;

	// la finestra di gioco
	private static GameWindow gameWindow;
	// il pannello di stato
	private static GameStatePanel statePanel;
	// il pannello di gioco
	private static GamePanel gamePanel;
	// il controller di gioco
	private static TankController tankController;

	// la console del client,
	private static ConsolePanel consolePanel;
	// il pannello del client.
	private static JoinServerPanel joinServerPanel;
	/* -------------------------------------------------------------------- */

	private static Client client;
	private static PrintWriter outputStream;
	private static Server server;

	private static Tank tank;
	private static boolean localTankSetted;
	private static Vector<Tank> enemy;
	private static String team;
	private static int numberOfTank;

	/**
	 * Inizializza il gioco.
	 * 
	 * @param gConf
	 *            la configurazione grafica di default
	 */
	public static void init(GraphicsConfiguration gConf) {
		// carica le risorse di gioco (immagini e suoni)
		ImageLoader.getInstance().load();
		// carica le risorse audio se richiamato per la prima volta
		SoundManager.player();
		// inizializza il gestore delle collisioni
		Collider.init();
		// inizializza la lista dei nemici
		enemy = new Vector<Tank>();
		// inizializza il contatore dei tank in gioco
		numberOfTank = 0;
		localTankSetted = false;
		// carica le proprietï¿½ della GUI e del gioco da file
		guiProperties = (GUIProperties) loadProperties("gui.init");
		guiProperties.initLookAndFeelMap();
		gameProperties = (GameProperties) loadProperties("game.init");
		// acquisisce la configurazione grafica
		TankBattleGame.gConf = gConf;
		// inizializza questi campi
		type = "- select type -";
		mode = "- select mode -";
		map = "- select map -";
	}

	/**
	 * Avvia l'interfaccia grafica appropriata.
	 */
	public static void startGUI() {
		if (guiProperties.gui == GUIProperties.WINDOWED) {
			guiWindowed = new MainWindow(gConf);
			windowedController = guiWindowed.getController();
		} else if (guiProperties.gui == GUIProperties.FULL_SCREEN) {
			guiFullScreen = new TankBattleFrame(gConf);
			fullSreenController = guiFullScreen.getController();
		}
		if (guiProperties.music == SoundManager.ON) {
			SoundManager.player().playMenu();
		}
	}

	/**
	 * Chiude il gioco.
	 */
	public static void closeGUI() {
		saveProperties();
		SoundManager.player().closeAll();
	}

	/**
	 * Restituisce la configurazione grafica di default.
	 * 
	 * @return &egrave; un oggetto {@code GraphicsConfiguration}
	 */
	public static GraphicsConfiguration getGraphicsConfiguration() {
		return gConf;
	}

	/**
	 * Imposta la musica ad ON/OFF.
	 * 
	 * @param musicState
	 *            puï¿½ assumere solo due valori: ON o OFF
	 */
	public static void setMusicState(int musicState) {
		if (musicState == SoundManager.OFF) {
			guiProperties.music = musicState;
			SoundManager.player().stopMenu();
		} else if (musicState == SoundManager.ON) {
			guiProperties.music = musicState;
			try {
				SoundManager.player().playMenu();
			} catch (OutOfMemoryError err) {
				Utility.logException(err);
				System.gc();
			}
		} else {
			try {
				throw new IllegalArgumentException(
						"The only value allowed are: ON, OFF");
			} catch (IllegalArgumentException e) {
				Utility.logException(e);
			}
		}
	}

	/**
	 * Restituisce un valore che identifica lo stato della musica.
	 * 
	 * @return un intero che puï¿½ assumere due valori possibili: ON/OFF (1/0).
	 */
	public static int getMusicState() {
		return guiProperties.music;
	}

	/**
	 * Imposta gli effetti sonori ad ON/OFF
	 * 
	 * @param soundEffectState
	 *            puï¿½ assumere solo due valori: ON o OFF
	 */
	public static void setSoundEffectState(int soundEffectState) {
		if (soundEffectState == SoundManager.OFF) {
			guiProperties.soundEffect = soundEffectState;
		} else if (soundEffectState == SoundManager.ON) {
			guiProperties.soundEffect = soundEffectState;
		} else {
			try {
				throw new IllegalArgumentException(
						"The only value allowed are: ON, OFF");
			} catch (IllegalArgumentException e) {
				Utility.logException(e);
			}
		}
	}

	/**
	 * Restituisce un valore che identifica lo stato degli effetti sonori.
	 * 
	 * @return un intero che puï¿½ assumere due valori possibili: ON/OFF (1/0).
	 */
	public static int getSoundEffectState() {
		return guiProperties.soundEffect;
	}

	/**
	 * Setta lo stato della GUI.
	 * 
	 * @param gui
	 *            ï¿½ un intero che assume due valori: WINDOWED o FULL_SCREEN.
	 */
	public static void setGUI(int gui) {
		if (gui != GUIProperties.WINDOWED || gui != GUIProperties.FULL_SCREEN) {
			try {
				throw new IllegalArgumentException(
						"The only value allowed are: WINDOWED, FULL_SCREEN");
			} catch (IllegalArgumentException e) {
				Utility.logException(e);
			}
		} else {
			guiProperties.gui = gui;
		}
	}

	/**
	 * Restituisce l'identificativo dell'interfaccia grafica.
	 * 
	 * @return un intero che identifica l'interfaccia grafica in uso.
	 */
	public static int getGUI() {
		return guiProperties.gui;
	}

	/**
	 * Setta il Look and Feel.
	 * 
	 * @param lookAndFeel
	 *            il nome del look and feel.
	 */
	public static void setLookAndFeel(String lookAndFeel) {
		guiProperties.setLookAndFeel(lookAndFeel);
	}

	/**
	 * Restituisce il nome Look and Feel.
	 * 
	 * @return il look and feel del gioco.
	 */
	public static String getLookAndFeelName() {
		return guiProperties.getLookAndFeelName();
	}

	/**
	 * Restituisce la classe del Look and Feel.
	 * 
	 * @return il look and feel del gioco.
	 */
	public static String getLookAndFeelClass() {
		return guiProperties.getLookAndFeelClass();
	}

	/**
	 * Setta la modalitï¿½ debbugging a ON/OFF. La modalitï¿½ debbugging permette o
	 * meno la visualizzazione di una serie di console, nelle quali verranno
	 * visualizzati i messaggi di stato della connessione.
	 * 
	 * @param debugMode
	 *            true se si vuole visualizzare la console di debug.
	 */
	public static void setDebugMode(boolean debugMode) {
		gameProperties.debugMode = debugMode;
	}

	/**
	 * Restituisce la modalitï¿½ di debug.
	 * 
	 * @return true se la modalitï¿½ debbugging ï¿½ attivata, false altrimenti.
	 */
	public static boolean getDebugMode() {
		return gameProperties.debugMode;
	}

	/**
	 * Mette in pausa il gioco, richiamando opportunamente i metodi dei relativi
	 * controller.
	 */
	public static void pause() {
		if (guiProperties.gui == GUIProperties.WINDOWED) {
			windowedController.pause();
		} else if (guiProperties.gui == GUIProperties.FULL_SCREEN) {
			fullSreenController.pause();
		}
	}
	
	/**
	 * Sposta il focus della finestra di gioco
	 * alla barra di stato
	 */
	public static void chat() {
		if (guiProperties.gui == GUIProperties.WINDOWED) {
			windowedController.chat();
		}
	}

	/**
	 * Passa dalla modalitï¿½ "Windowed" alla modalitï¿½ "Full Screen"
	 */
	public static void toFullScreen() {
		guiProperties.gui = GUIProperties.FULL_SCREEN;
		if (guiFullScreen == null) {
			guiFullScreen = new TankBattleFrame(gConf);
			fullSreenController = guiFullScreen.getController();
		} else {
			OptionPanel panel = (OptionPanel) guiFullScreen.getContentPane();
			panel.refreshMenu();
			guiFullScreen.setVisible(true);
		}
		guiWindowed.setVisible(false);
	}

	/**
	 * Passa dalla modalitï¿½ "Full Screen" alla modalitï¿½ "Windowed"
	 */
	public static void toWindowed() {
		guiProperties.gui = GUIProperties.WINDOWED;
		if (guiWindowed == null) {
			guiWindowed = new MainWindow(gConf);
			windowedController = guiWindowed.getController();
		} else {
			guiWindowed.setVisible(true);
		}
		guiFullScreen.setVisible(false);
	}

	/**
	 * Setta il valore della combo box "type".
	 * 
	 * @param type
	 *            ï¿½ l'identificativo del valore della combo box.
	 */
	public static void setType(String type) {
		TankBattleGame.type = type;
	}

	/**
	 * Restituisce il valore della combo box "type".
	 * 
	 * @return una stringa che identifica il valore della combo box.
	 */
	public static String getType() {
		return type;
	}

	/**
	 * Setta il valore della combo box "mode".
	 * 
	 * @param type
	 *            ï¿½ l'identificativo del valore della combo box.
	 */
	public static void setMode(String mode) {
		TankBattleGame.mode = mode;
	}

	/**
	 * Restituisce il valore della combo box "mode".
	 * 
	 * @return una stringa che identifica il valore della combo box.
	 */
	public static String getMode() {
		return mode;
	}

	/**
	 * Setta il valore della combo box "map".
	 * 
	 * @param type
	 *            ï¿½ l'identificativo del valore della combo box.
	 */
	public static void setMap(String map) {
		TankBattleGame.map = map;
	}

	/**
	 * Restituisce il valore della combo box "map".
	 * 
	 * @return una stringa che identifica il valore della combo box.
	 */
	public static String getMap() {
		return map;
	}

	/**
	 * Setta la modalitï¿½ di gioco (client/server)
	 * 
	 * @param gameMode
	 *            una stringa.
	 */
	public static void setGameMode(String gameMode) {
		TankBattleGame.gameMode = gameMode;
	}

	/**
	 * Restituisce la modalitï¿½ di gioco (client/server)
	 * 
	 * @return una stringa.
	 */
	public static String getGameMode() {
		return gameMode;
	}

	/**
	 * Restituisce il nome del host locale.
	 * 
	 * @return una stringa che identifica il nome del host locale.
	 */
	public static String getLocalName() {
		String name = System.getenv("USERDOMAIN");
		name += ":";
		name += System.getProperty("user.name");
		return name;
	}

	/**
	 * Restituisce il riferimento alla finestra principale della GUI "Windowed";
	 * 
	 * @return la finestra principale.
	 */
	public static MainWindow getMainWindow() {
		return guiWindowed;
	}

	/**
	 * Restituisce il riferimento dell'unica finestra principale della GUI
	 * "Full Screen";
	 * 
	 * @return la finestra principale.
	 */
	public static TankBattleFrame getGUIFullScreen() {
		return guiFullScreen;
	}

	/**
	 * Setta le proprietï¿½ della GUI.
	 * 
	 * @param g
	 *            un oggetto serializzabbile di tipo
	 *            {@link tankBattle.utilities.GUIProperties GUIProperties}
	 */
	public static void setGUIProperties(GUIProperties g) {
		guiProperties = g;
	}

	/**
	 * Setta le proprietï¿½ di gioco.
	 * 
	 * @param g
	 *            un oggetto serializzabbile di tipo
	 *            {@link tankBattle.utilities.GameProperties GameProperties}
	 */
	public static void setGameProperties(GameProperties g) {
		gameProperties = g;
	}

	/**
	 * Restituisce un flag booleano che indica se ï¿½ stata effettuata una scelta
	 * nella combo box di nome "type".
	 * 
	 * @return true se ï¿½ stato selezionato un valore nella combo box.
	 */
	public static boolean typeIsNotNull() {
		return !type.equals("- select type -");
	}

	/**
	 * Restituisce un flag booleano che indica se ï¿½ stata effettuata una scelta
	 * nella combo box di nome "mode".
	 * 
	 * @return true se ï¿½ stato selezionato un valore nella combo box.
	 */
	public static boolean modeIsNotNull() {
		return !mode.equals("- select mode -");
	}

	/**
	 * Restituisce un flag booleano che indica se ï¿½ stata effettuata una scelta
	 * nella combo box di nome "map".
	 * 
	 * @return true se ï¿½ stato selezionato un valore nella combo box.
	 */
	public static boolean mapIsNotNull() {
		return !map.equals("- select map -");
	}

	/**
	 * Crea la finestra di gioco.
	 * 
	 * @param output
	 *            il socket di output da passare al controller.
	 */
	public static void startGameWindow(PrintWriter output) {
		gameWindow = new GameWindow(windowedController, gConf, output);
		/*
		 * Se si vuole fare in full screen basta aggiungere qui le seguenti
		 * linee di codice:
		 */
		
		 GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		 device.setFullScreenWindow(gameWindow);
		 device.setDisplayMode(new DisplayMode(800, 600, 32, 60));
		
		windowedController.setGameWindow(gameWindow);
		statePanel = gameWindow.getGamePanel().getStatePanel();
		gamePanel = gameWindow.getGamePanel();
		SoundManager.player().startGame();
	}

	/**
	 * Restituisce il controller della GUI Windowed.
	 * 
	 * @return il controller della GUI.
	 */
	public static GUIWindowedController getWindowedController() {
		return windowedController;
	}

	/**
	 * Setta la console del client.
	 * 
	 * @param consolePanel
	 *            la console del client.
	 */
	public static void setConsolePanel(ConsolePanel consolePanel) {
		TankBattleGame.consolePanel = consolePanel;
	}

	/**
	 * Restituisce la console del client.
	 * 
	 * @return la console del client.
	 */
	public static ConsolePanel getConsolePanel() {
		return consolePanel;
	}

	/**
	 * Setta il pannello del client.
	 * 
	 * @param panel
	 *            il pannello del client.
	 */
	public static void setJoinServerPanel(JoinServerPanel panel) {
		joinServerPanel = panel;
	}

	/**
	 * Restituisce il pannello del client.
	 * 
	 * @return il pannello del client.
	 */
	public static JoinServerPanel getJoinServerPanel() {
		return joinServerPanel;
	}

	/* ------------------------------------------------------------------ */
	/* ------------ METODI ACCESSORI E MODIFICATORI DEL TANK ------------ */
	/* ------------------------------------------------------------------ */

	/**
	 * Invia un messaggio agli altri tank nel gioco.
	 */
	public static void sendMessage(String message) {
		outputStream.println(MESSAGE + ":" + message + "@" + tank.getID());
		outputStream.flush();
	}
	
	public static void receiveMessage(String message) {
		windowedController.receiveMessage(message);
	}
	
	/**
	 * Setta il tank di gioco.
	 */
	public static void setTank(Tank tank) {
		// incrementa il numero di tank in gioco
		numberOfTank++;
		// specifica che questo tank è locale all'applicazione
		tank.setLocale(true);
		// aggiunge il tank al collider
		Collider.addTank(tank);
		// setta il tank principale
		TankBattleGame.tank = tank;
		localTankSetted = true;
	}
	
	/**
	 * Restituisce il tank di gioco.
	 * 
	 * @return il riferimento al componente "Tank"
	 */
	public static Tank getTank() {
		return tank;
	}

	/**
	 * Setta il nome del tank con una stringa di default, che corrisponde al
	 * nome dell'host su cui gira il gioco.
	 */
	public static void setDefaultTankName() {
		setTankName(null);
	}

	/**
	 * Setta il nome del tank.
	 * 
	 * @param name
	 *            una stringa che identifica il nome del tank.
	 */
	public static void setTankName(String name) {
		if (name == null || name.equals("")) {
			gameProperties.tankName = getLocalName();
		} else {
			gameProperties.tankName = name;
		}
	}

	/**
	 * Restituisce il nome del tank.
	 * 
	 * @return una stringa che definisce il nome del tank.
	 */
	public static String getTankName() {
		return gameProperties.tankName;
	}

	/**
	 * Setta il colore del tank.
	 * 
	 * @param color
	 *            il colore del tank.
	 */
	public static void setTankColor(Color color) {
		gameProperties.tankColor = color;
	}

	/**
	 * Restituisce il colore del tank.
	 * 
	 * @return il colore del tank.
	 */
	public static Color getTankColor() {
		return gameProperties.tankColor;
	}

	/**
	 * Aggiunge un nuovo nemico alla lista.
	 * 
	 * @param tank
	 *            un nuovo tank.
	 */
	public static void addEnemy(Tank tank) {
		numberOfTank++;
		tank.setLocale(false);
		Collider.addTank(tank);
		enemy.addElement(tank);
		gamePanel.addTank(tank);
	}

	/**
	 * Rimuove un tank dalla lista.
	 * 
	 * @param id
	 *            l'identificativo univoco del tank.
	 */
	public static void removeTank(String id) {
		numberOfTank--;
		if( tank.getID().equals(id) ) {
			removeLocalTank();
		} else {
			for (Tank t : enemy) {
				if (t.getID().equals(id)) {
					Collider.removeTank(t);
					gamePanel.removeTank(t);
					enemy.removeElement(t);
					SoundManager.player().tankExplosion();
					break;
				}
			}
		}
	}
	
	/**
	 * Rimuove il tank locale
	 */
	public static void removeLocalTank() {
		if( localTankSetted ) {
			Collider.removeTank(tank);
			gamePanel.removeTank(tank);
			gamePanel.repaint();
			tank = null;
			localTankSetted = false;
			SoundManager.player().tankExplosion();
		}
	}

	/**
	 * Muove il "tank" nella posizione specificata dalle coordinate.
	 * 
	 * @param id
	 *            l'ID del tank.
	 * @param x
	 *            l'ascissa del tank.
	 * @param y
	 *            l'ordinata del tank.
	 */
	public static void move(String id, float x, float y) {
		for (Tank t : enemy) {
			if (t.getID().equals(id)) {
				t.setPosition(x, y);
				t.getTurret().setPosition(x, y);
				t.notifyObservers();
				break;
			}
		}
	}
	
	/**
	 * Gira il tank a destra o a sinistra in base all'angolo passato.
	 * 
	 * @param id l'ID del tank.
	 * @param currentAngle lo spiazzamento di rotazione.
	 */
	public static void turnBody(String id, int currentAngle) {
		for (Tank t : enemy) {
			if (t.getID().equals(id)) {
				t.setCurrentAngle(currentAngle);
				break;
			}
		}
	}
	
	/**
	 * Gira la torretta del tank a destra o a sinistra in base all'angolo passato.
	 * 
	 * @param id l'ID del tank.
	 * @param currentAngle lo spiazzamento di rotazione.
	 */
	public static void turnTurret(String id, int currentAngle) {
		for (Tank t : enemy) {
			if (t.getID().equals(id)) {
				t.getTurret().setCurrentAngle(currentAngle);
				break;
			}
		}
	}
	
	/**
	 * Fa sparare il tank identificato dall'{@code id} specificato
	 * nei parametri.
	 * 
	 * @param id l'identificativo univoco del tank.
	 */
	public static void shoot(String id) {
		for (Tank t : enemy) {
			if (t.getID().equals(id)) {
				t.shoot();
				break;
			}
		}
	}
	
	/**
	 * Setta lo stato di vibile/invisibile al tank.
	 * 
	 * @param id l'ID del tank.
	 * @param visible {@code true} se il tank &egrave; visibile.
	 */
	public static void setVisible(String id, boolean visible) {
		for (Tank t : enemy) {
			if (t.getID().equals(id)) {
				t.setVisible(visible);
				break;
			}
		}
	}

	/* ------------------------------------------------------------------ */
	/* ------------------------------------------------------------------ */

	/* ------------------------------------------------------------------ */
	/* ------------------------------ SERVER ---------------------------- */
	/* ------------------------------------------------------------------ */

	/**
	 * Fa partire il gioco.
	 */
	public static void startServerGame() {
		server = new Server();
		server.start();
		int port = server.getLocalPort();
		client = new Client("127.0.0.1", port);
		client.start();
	}
	
	public static void startServerGame(String team) {
		TankBattleGame.team = team;
		server = new Server();
		server.start();
		int port = server.getLocalPort();
		client = new Client("127.0.0.1", port);
		client.start();
	}

	/**
	 * Ferma il gioco.
	 */
	public static void stopGame() {
		numberOfTank--;
		if( gameMode.equals("server") ) {
			printToOutputStream(CLOSE_GAME + "@0");
			server.stop();
		} else {
			if( numberOfTank > 0 ) {
				printToOutputStream(DESTROY + "@" + tank.getID());
			} else {
				printToOutputStream(CLOSE_GAME + "@0");
			}
		}
		client.stopDemon();
	}

	/**
	 * Avvia il server.
	 */
	public static void startServer() {
		server = new Server();
		server.start();
	}

	/**
	 * Stoppa il server.
	 */
	public static void stopServer() {
		if (server != null) {
			server.stop();
		}
	}

	/**
	 * Stoppa i Thread istanziati per gestire i diversi client connessi.
	 */
	public static void stopThreadServers() {
		if (server != null) {
			server.stopThreadServer();
		}
	}

	/**
	 * Restituisce il numero di porta su cui ï¿½ agganciato il server.
	 * 
	 * @return il numero di porta del server.
	 */
	public static int getServerPort() {
		return server.getLocalPort();
	}

	/* ------------------------------------------------------------------ */
	/* ------------------------------------------------------------------ */

	/* ------------------------------------------------------------------ */
	/* ------------------------------ CLIENT ---------------------------- */
	/* ------------------------------------------------------------------ */

	/**
	 * Avvia la ricerca dei server.
	 */
	public static void startClient() {
		client = new Client();
		client.findServer();
	}

	/**
	 * Avvia il client connettendolo al server specificato dai parametri.
	 * 
	 * @param host
	 *            l'indirizzo dell'server.
	 * @param port
	 *            la porta del server.
	 */
	public static void startClient(String host, int port) {
		client = new Client(host, port);
		client.start();
	}
	
	/**
	 * Setta lo stream di output.
	 * 
	 * @param output lo stream di output ({@link java.io.PrintWriter}).
	 */
	public static void setOutputStream(PrintWriter output) {
		outputStream = output;
	}
	
	/**
	 * Stampa il messagge {@code message} sullo stream di output.
	 * 
	 * @param message la stringa che rappresenta il messaggio da stampare.
	 */
	public static void printToOutputStream(String message) {
		outputStream.println(message);
		outputStream.flush();
	}
	
	/**
	 * Restituisce lo stream di output.
	 * 
	 * @return lo stream di output.
	 */
	public static PrintWriter getOutputStream() {
		return outputStream;
	}

	/**
	 * Stoppa il client.
	 */
	public static void stopClient() {
		if (client != null) {
			client.stopDemon();
		}
	}

	/**
	 * Riavvia la ricerca dei server.
	 */
	public static void refreshServerList() {
		if (client != null) {
			client.stopFinder();
			client.findServer();
		}
	}

	/**
	 * Restituisce la lista dei server running sulla rete.
	 * 
	 * @return restituisce un vettore sincronizzato di
	 *         {@link tankBattle.net.Endpoint Endpoint} (
	 *         {@link java.util.Vector})
	 */
	public static Vector<Endpoint> getServerList() {
		return client.getServerList();
	}

	/**
	 * Connette il client al server specificato.
	 * 
	 * @param endpoint
	 *            contiene il socket del server specificato.
	 */
	public static void connectClientTo(Endpoint endpoint) {
		client.connectTo(endpoint);
		client.start();
	}

	/* ------------------------------------------------------------------ */
	/* ------------------------------------------------------------------ */

	/**
	 * Salva su file le proprietï¿½ della GUI e del gioco.
	 */
	public static void saveProperties() {
		File gui = new File("config/gui.init");
		File game = new File("config/game.init");
		ObjectOutputStream guiOut = null;
		ObjectOutputStream gameOut = null;
		try {
			gui.createNewFile();
			game.createNewFile();
			guiOut = new ObjectOutputStream(new FileOutputStream(gui));
			gameOut = new ObjectOutputStream(new FileOutputStream(game));
			guiOut.writeObject(guiProperties);
			gameOut.writeObject(gameProperties);
		} catch (IOException e) {
			Utility.logException(e);
		} finally {
			if (guiOut != null) {
				try {
					guiOut.close();
				} catch (IOException e) {
					Utility.logException(e);
				}
			}
			if (gameOut != null) {
				try {
					gameOut.close();
				} catch (IOException e) {
					Utility.logException(e);
				}
			}
		}
	}

	/**
	 * Carica da file passato come parametro, le proprietï¿½ dell'oggetto
	 * desiderato.
	 * 
	 * @param fileName
	 *            il nome del file da cui si vuole caricare l'oggetto con le
	 *            proprietï¿½
	 * @return restituisce un oggetto, che conterrï¿½ le proprietï¿½ desiderate
	 */
	private static Object loadProperties(String fileName) {
		File properties = new File("config/" + fileName);
		properties.exists();
		ObjectInputStream in = null;
		Object obj = null;
		try {
			in = new ObjectInputStream(new FileInputStream(properties));
			obj = in.readObject();
		} catch (FileNotFoundException e) {
			Utility.setSateException(Utility.PRINT_STACK_TRACE);
			Utility.logException(new TankBattleGame(), e);
		} catch (IOException e) {
			Utility.setSateException(Utility.PRINT_STACK_TRACE);
			Utility.logException(new TankBattleGame(), e);
		} catch (ClassNotFoundException e) {
			Utility.setSateException(Utility.PRINT_STACK_TRACE);
			Utility.logException(new TankBattleGame(), e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				Utility.setSateException(Utility.PRINT_STACK_TRACE);
				Utility.logException(new TankBattleGame(), e);
			}
		}
		return obj;
	}

}