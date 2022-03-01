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

/**
 * Rappresenta l'elemento "map" del file XML. Questo oggetto
 * contiene una lista di oggetti di tipo "wall" i quali saranno
 * 
 * @author Simone Spaccarotella
 *
 */
public class Map {

	private String id;
	private String srcImage;
	private String width;
	private String height;
	private ArrayList<Wall> walls;
	
	public Map(String id) {
		this(id, "", "", "");
		walls = new ArrayList<Wall>();
	}
	
	public Map(String id, String srcImage, String width, String height) {
		this.id = id;
		this.srcImage = srcImage;
		this.width = width;
		this.height = height;
	}
	
	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}
	
	public void setWidth(String width) {
		this.width = width;
	}
	
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getID() {
		return id;
	}
	
	public String getSrcImage() {
		return srcImage;
	}
	
	public String getWidth() {
		return width;
	}
	
	public String getHeight() {
		return height;
	}
	
	public int getWallSize() {
		return walls.size();
	}
	
	public Wall getWall(int i) {
		return walls.get(i);
	}
	
	public void addWall(Wall wall) {
		walls.add(wall);
	}
	
	public void addWall(String x, String y) {
		walls.add(new Wall(x, y));
	}
	
}
