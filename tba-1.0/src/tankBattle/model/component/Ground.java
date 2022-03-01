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

package tankBattle.model.component;

import java.awt.Image;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import tankBattle.model.game.Collider;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.xmlParser.Map;
import tankBattle.utilities.xmlParser.Parser;

/**
 * Questa classe astratta, rappresenta la categoria dei ground che &egrave;
 * possibile istanziare. Estendendo questa classe &egrave; possibile creare un
 * nuovo ground, che verr&agrave; poi utilizzato come mappa nel gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class Ground {

	protected Image[] views;
	protected Map map;

	/**
	 * Crea un nuovo ground generico.
	 */
	public Ground() {
		initViews();
		setMap();
	}

	/**
	 * Restituisce le texture del ground.
	 * 
	 * @return una lista di immagini.
	 */
	public Image[] getViews() {
		return views;
	}

	/**
	 * Restituisce la mappa.
	 * 
	 * @return una mappa, al cui interno &egrave; memorizzato lo schema dei
	 *         muretti.
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Inizializza la vista.
	 */
	protected abstract void initViews();

	/**
	 * Setta le mappe.
	 */
	private void setMap() {
		Parser parser = new Parser("config/map/map.xml");
		Document document = null;
		try {
			document = parser.parse();
			parser.createTree(document);
			map = parser.getMap(TankBattleGame.getMapID());
			if (map != null) {
				for (int i = 0; i < map.getWallSize(); i++) {
					Collider.addObstacle(map.getWall(i));
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
