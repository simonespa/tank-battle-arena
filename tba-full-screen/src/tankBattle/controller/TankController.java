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

package tankBattle.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import tankBattle.model.component.Tank;
import tankBattle.model.game.TankBattleGame;
import tankBattle.net.ClientServerCapable;
import tankBattle.net.Dispatchable;
import tankBattle.utilities.StringsProperty;
import tankBattle.utilities.Utility;

/**
 * Questa classe controlla le azioni del componente
 * {@link tankBattle.model.component.Tank Tank}.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankController extends ControllerAdapter implements
		ClientServerCapable, Dispatchable<String> {

	/* il riferimento all'oggetto da controllare */
	private Tank tank;
	/* lo stream di output */
	private PrintWriter output;
	/* una string che rappresenta l'ID del tank */
	private String dominio;

	/* questi campi si riferiscono ai codici relativi ai tasti */
	private int goAhead;
	private int goBack;
	private int turnLeft;
	private int turnRight;
	private int rotateTurretLeft;
	private int rotateTurretRight;
	private int shoot;
	private int layMine;
	private int selectBullet;
	private int selectExplosive;
	private int chat;
	private int teamChat;
	private int pause;

	/**
	 * Crea un nuovo controller.
	 * 
	 * @param tank
	 *            il riferimento al tank.
	 */
	public TankController(Tank tank, PrintWriter output) {
		this.tank = tank;
		this.dominio = "@" + tank.getID();
		this.output = output;
		initProperties();
	}

	/**
	 * Setta il nuovo tasto per {@code goAhead}.
	 * 
	 * @param keyCode
	 *            codice del tasto per andare avanti.
	 */
	public void setGoAhead(int keyCode) {
		goAhead = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code goBack}.
	 * 
	 * @param keyCode
	 *            codice del tasto per andare indietro
	 */
	public void setGoBack(int keyCode) {
		goBack = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code turnLeft}.
	 * 
	 * @param keyCode
	 *            codice del tasto per girare a sinistra
	 */
	public void setTurnLeft(int keyCode) {
		turnLeft = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code turnRight}.
	 * 
	 * @param keyCode
	 *            codice del tasto per girare a destra
	 */
	public void setTurnRight(int keyCode) {
		turnRight = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code rotateTurretLeft}.
	 * 
	 * @param keyCode
	 *            codice del tasto per ruotare la torretta a sinistra
	 */
	public void setRotateTurretLeft(int keyCode) {
		rotateTurretLeft = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code rotateTurretRight}.
	 * 
	 * @param keyCode
	 *            codice del tasto per ruotare la torretta a destra
	 */
	public void setRotateTurretRight(int keyCode) {
		rotateTurretRight = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code shoot}.
	 * 
	 * @param keyCode
	 *            codice del tasto per sparare
	 */
	public void setShoot(int keyCode) {
		shoot = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code layMine}.
	 * 
	 * @param keyCode
	 *            codice del tasto per piazzare una mina
	 */
	public void setLayMine(int keyCode) {
		layMine = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code selectBullet}.
	 * 
	 * @param keyCode
	 *            codice del tasto per selezionare il tipo di proiettile
	 */
	public void setSelectBullet(int keyCode) {
		selectBullet = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code selectExplosive}.
	 * 
	 * @param keyCode
	 *            codice del tasto per selezionare il tipo di esplosivo
	 */
	public void setSelectExplosive(int keyCode) {
		selectExplosive = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code setChat}.
	 * 
	 * @param keyCode
	 *            codice del tasto per selezionare la chat visibile a tutti
	 */
	public void setChat(int keyCode) {
		chat = keyCode;
	}

	/**
	 * Setta il nuovo tasto per {@code teamChat}.
	 * 
	 * @param keyCode
	 *            codice del tasto per selezionare la chat visibile solo al
	 *            team.
	 */
	public void setTeamChat(int keyCode) {
		teamChat = keyCode;
	}

	/**
	 * Inizializza i campi della classe.
	 */
	public void initProperties() {
		Map<String, String> properties = getPropertiesFromFile();
		goAhead = Integer.parseInt(properties.get("goAhead"));
		goBack = Integer.parseInt(properties.get("goBack"));
		turnLeft = Integer.parseInt(properties.get("turnLeft"));
		turnRight = Integer.parseInt(properties.get("turnRight"));
		rotateTurretLeft = Integer.parseInt(properties.get("rotateTurretLeft"));
		rotateTurretRight = Integer.parseInt(properties
				.get("rotateTurretRight"));
		shoot = Integer.parseInt(properties.get("shoot"));
		layMine = Integer.parseInt(properties.get("layMine"));
		selectBullet = Integer.parseInt(properties.get("selectBullet"));
		selectExplosive = Integer.parseInt(properties.get("selectExplosive"));
		chat = Integer.parseInt(properties.get("chat"));
		teamChat = Integer.parseInt(properties.get("teamChat"));
		pause = Integer.parseInt(properties.get("pause"));
	}

	/**
	 * Restituisce una mappa di proprietà che serviranno ad inizializzare i
	 * campi di questa classe.
	 * 
	 * @return una mappa di proprietà
	 */
	private Map<String, String> getPropertiesFromFile() {
		// crea la mappa delle proprietà
		Map<String, String> properties = new HashMap<String, String>();
		// crea il descrittore del file
		File file = new File("config/game.properties");
		BufferedReader input = null;
		try {
			String key;
			String value;
			String line;
			// acquisisce lo stream di input del file
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			// acquisisce il nome della classe che invoca questo metodo
			String className = getClassName();
			// scorre il file fino ad arrivare alla prima stringa di proprietà
			// dell'oggetto che invoca il metodo
			do {
				line = input.readLine();
			} while (!line.startsWith(className));
			// scorre tutte le proprietà riguardanti l'oggetto e salta i
			// commenti
			while (line.startsWith(className) || line.startsWith("#")) {
				// la stringa è un commento lo salta e legge la riga successiva
				if (line.startsWith("#")) {
					line = input.readLine();
					continue;
				}
				// acquisisce la stringa di proprietà
				StringsProperty.acquireProperty(line);
				// restituisce la chiave
				key = StringsProperty.getKey();
				// restituisce il valore
				value = StringsProperty.getValue();
				// setta la proprietà
				properties.put(key, value);
				// legge la riga successiva
				line = input.readLine();
			}
		} catch (FileNotFoundException e) {
			Utility.logException(e);
		} catch (IOException e) {
			Utility.logException(e);
		} finally {
			try {
				// chiude lo stream di input
				input.close();
			} catch (IOException e) {
				Utility.logException(e);
			}
		}
		return properties;
	}

	/**
	 * Restituisce il nome della classe che invoca il metodo.
	 * 
	 * @return il nome della classe
	 */
	protected String getClassName() {
		// acquisisce il nome canonico della classe
		String canonicalName = this.getClass().getCanonicalName();
		// splitta la stringa con il "."
		String[] split = canonicalName.split("\\.");
		// acquisisce l'ultima stringa che corrisponde al nome della classe
		// senza
		// il nome dei vari package, e lo restituisce
		return split[split.length - 1];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// acquisisce il codice del tasto premuto
		int keyCode = e.getKeyCode();
		// controlla che tasto è stato premuto ed esegue
		// il comando relativo
		if (keyCode == goAhead) {
			tank.goAhead();
			dispatch(MOVE + ":" + tank.getX() + ":" + tank.getY() + dominio);
		} else if (keyCode == goBack) {
			tank.goBack();
			dispatch(MOVE + ":" + tank.getX() + ":" + tank.getY() + dominio);
		} else if (keyCode == turnLeft) {
			tank.turnLeft();
			dispatch(TURN_BODY + ":" + tank.getCurrentAngle() + dominio);
		} else if (keyCode == turnRight) {
			tank.turnRight();
			dispatch(TURN_BODY + ":" + tank.getCurrentAngle() + dominio);
		} else if (keyCode == rotateTurretLeft) {
			tank.rotateTurretLeft();
			dispatch(TURN_TURRET + ":" + tank.getTurret().getCurrentAngle()
					+ dominio);
		} else if (keyCode == rotateTurretRight) {
			tank.rotateTurretRight();
			dispatch(TURN_TURRET + ":" + tank.getTurret().getCurrentAngle()
					+ dominio);
		} else if (keyCode == shoot) {
			tank.shoot();
			dispatch(SHOOT + dominio);
		} else if (keyCode == pause) {
			pause();
		} else if (keyCode == chat) {
			chat();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// acquisisce il codice del tasto rilasciato
		int keyCode = e.getKeyCode();
		// se il tasto rilasciato è il comando per far andare
		// avanti o indietro il tank, allora il tank rallenta
		if (keyCode == goAhead || keyCode == goBack) {
			tank.slowDown();
		}
	}

	/**
	 * Mette il gioco in pausa, passando il controllo alla finestra di opzioni.
	 */
	@Override
	protected void pause() {
		TankBattleGame.pause();
	}
	
	/*
	 * Passa il controllo alla barra di stato
	 */
	private void chat() {
		TankBattleGame.chat();
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void dispatch(String obj) {
		output.println(obj);
		output.flush();
	}

}