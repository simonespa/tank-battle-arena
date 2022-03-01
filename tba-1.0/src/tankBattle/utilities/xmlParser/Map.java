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

package tankBattle.utilities.xmlParser;

import java.util.ArrayList;

import tankBattle.model.component.Wall;

/**
 * Rappresenta l'elemento "map" del file XML. Questo oggetto contiene una lista
 * di oggetti di tipo "wall" i quali saranno
 * 
 * @author Simone Spaccarotella
 * 
 */
public class Map {

	private String id;
	private String srcImage;
	private int width;
	private int height;
	private ArrayList<Wall> walls;

	/**
	 * Crea una mappa vuota.
	 * 
	 * @param id
	 *            l'ID univoco della mappa.
	 */
	public Map(String id) {
		this.id = id;
		walls = new ArrayList<Wall>();
	}

	/**
	 * Crea una mappa non vuota, contenente almeno un muretto.
	 * 
	 * @param id
	 *            l'ID univoco della mappa.
	 * @param srcImage
	 *            l'URL del'file che contiene l'immagine del muretto.
	 * @param width
	 *            la larghezza del muretto.
	 * @param height
	 *            l'altezza del muretto.
	 */
	public Map(String id, String srcImage, String width, String height) {
		this.id = id;
		this.srcImage = srcImage;
		this.width = new Integer(width);
		this.height = new Integer(height);
	}

	/**
	 * Setta il nome del file che contiene l'immagine del muretto.
	 * 
	 * @param srcImage
	 *            il path del file.
	 */
	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}

	/**
	 * Setta la larghezza.
	 * 
	 * @param width
	 *            la larghezza.
	 */
	public void setWidth(String width) {
		this.width = new Integer(width);
	}

	/**
	 * Setta l'altezza.
	 * 
	 * @param height
	 *            l'altezza.
	 */
	public void setHeight(String height) {
		this.height = new Integer(height);
	}

	/**
	 * Restituisce l'ID.
	 * 
	 * @return l'ID univoco.
	 */
	public String getID() {
		return id;
	}

	/**
	 * Restituisce il nome del file, completo di path assoluto.
	 * 
	 * @return il nome del file.
	 */
	public String getSrcImage() {
		return srcImage;
	}

	/**
	 * Restituisce la larghezza.
	 * 
	 * @return la larghezza.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Restituisce l'altezza.
	 * 
	 * @return l'altezza.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Restituisce il numero di muretti presenti nella mappa.
	 * 
	 * @return la dimensione della lista dei muretti.
	 */
	public int getWallSize() {
		return walls.size();
	}

	/**
	 * Restituisce una mappa dato un intero.
	 * 
	 * @param i
	 *            l'indice della lista delle mappe.
	 * @return una mappa identificata dall'indice passato in input.
	 */
	public Wall getWall(int i) {
		return walls.get(i);
	}

	/**
	 * Aggiunge un muretto alla mappa.
	 * 
	 * @param wall
	 *            il muretto.
	 */
	public void addWall(Wall wall) {
		walls.add(wall);
	}

	/**
	 * Aggiunge un muretto data una coppia di coordinate.
	 * 
	 * @param x
	 *            l'ascissa del muretto.
	 * @param y
	 *            l'ordinata del muretto.
	 */
	public void addWall(String x, String y) {
		walls.add(new Wall(new Integer(x), new Integer(y), width, height));
	}

}