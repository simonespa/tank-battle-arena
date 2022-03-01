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

package tankBattle.utilities;

import java.awt.Color;
import java.io.Serializable;

/**
 * Rappresenta lo stato del gioco. Un'istanza di questa classe può essere
 * memorizzata su file e resa persistente, per poter essere utilizzata
 * successivamente.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GameProperties implements Serializable {

	private static final long serialVersionUID = -7483017430812781834L;
	public String tankName;
	public Color tankColor;
	public boolean debugMode;

}