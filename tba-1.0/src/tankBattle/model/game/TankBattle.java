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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * Questo &egrave; l'entry point di tutto il programma. Questa classe contiene
 * il main del gioco, il quale dopo aver acquisito la configurazione grafica di
 * sistema avvier&agrave; la GUI.
 * 
 * @author Simone Spaccarotella
 */
public final class TankBattle {

	/**
	 * Questo &egrave; il main del gioco. Da questo metodo &egrave; possibile
	 * inizializzare la finestra principale, dalla quale &egrave; possibile
	 * successivamente gestire il gioco.
	 * 
	 * @param args
	 *            sono i parametri passati da linea di comando.
	 */
	public static void main(String[] args) {
		// acquisisce l'ambiente grafico locale
		GraphicsEnvironment gEnv = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		// acquisisce il riferimento al monitor
		GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
		// acquisisce la configurazione grafica di default
		GraphicsConfiguration gConf = gDev.getDefaultConfiguration();
		// crea la finestra principale del gioco, passandogli la configurazione
		// grafica
		TankBattleGame.init(gConf);
		// fa partire il menù
		TankBattleGame.startGUI();
	}

}