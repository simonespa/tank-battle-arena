/*
 * Copyright  2008 Simone Spaccarotella
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Rappresenta lo stato dell'interfaccia grafica. Un'istanza di questa classe
 * pu� essere memorizzata su file e resa persistente, per poter essere
 * utilizzata successivamente.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GUIProperties implements Serializable {

	private static final long serialVersionUID = -2303312364731169221L;
	public int gui;
	public int music;
	public int soundEffect;
	private String lookAndFeelName;

	/* la struttura dati che mappa i look and feel istallati */
	private Map<String, String> lookAndFeelMap;

	/** imposta la GUI a finestre */
	public static final int WINDOWED = 2;
	/** imposta la GUI a tutto schermo */
	public static final int FULL_SCREEN = 3;

	/**
	 * inizializza il look and feel.
	 */
	public void initLookAndFeelMap() {
		// acquisisce il look and feel di default
		LookAndFeelInfo[] istalledLookAndFeel = UIManager
				.getInstalledLookAndFeels();
		lookAndFeelMap = new HashMap<String, String>();
		for (LookAndFeelInfo info : istalledLookAndFeel) {
			lookAndFeelMap.put(info.getName(), info.getClassName());
		}
	}

	/**
	 * Setta il look and feel.
	 * 
	 * @param name
	 *            il nome del look and feel.
	 */
	public void setLookAndFeel(String name) {
		if (lookAndFeelMap.containsKey(name)) {
			lookAndFeelName = name;
		} else {
			lookAndFeelName = lookAndFeelMap.keySet().iterator().next();
		}
	}

	/**
	 * Restituisce il nome del look and feel.
	 * 
	 * @return una stringa che rappresenta il nome del look and feel.
	 */
	public String getLookAndFeelName() {
		// se il look and feel non è contenuto nella mappa
		if (!lookAndFeelMap.containsKey(lookAndFeelName)) {
			// acquisisce il primo della lista
			Set<String> keySet = lookAndFeelMap.keySet();
			lookAndFeelName = keySet.iterator().next();
		}
		return lookAndFeelName;
	}

	/**
	 * Restituisce la classe del Look and Feel.
	 * 
	 * @return una stringa che rappresenta il nome qualificato della classe.
	 */
	public String getLookAndFeelClass() {
		// se il look and feel non è contenuto nella mappa
		if (!lookAndFeelMap.containsKey(lookAndFeelName)) {
			// acquisisce il primo della lista
			Set<String> keySet = lookAndFeelMap.keySet();
			lookAndFeelName = keySet.iterator().next();
		}
		// e ne restituisce la classe
		return lookAndFeelMap.get(lookAndFeelName);
	}

	/**
	 * Restituisce la mappa dei look and feel istallati sul pc.
	 * 
	 * @return una mappa di look and feel.
	 */
	public Map<String, String> getLookAndFeelMap() {
		return lookAndFeelMap;
	}

}