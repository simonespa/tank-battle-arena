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

import java.io.Serializable;

/**
 * Gli oggetti di questa classe vengono serializzati e memorizzati su
 * filesystem. Essi contentono le proprietà del motore del tank in funzione del
 * terreno su cui si muovono. Variando opportunamente i parametri del motore, è
 * possibile ricreare le diverse forze di attrito che si vengono a creare su
 * diversi terreni.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankEngineProperties implements Serializable {

	/* il numero seriale univoco */
	private static final long serialVersionUID = -4505992086395930124L;

	/** la velocità massima che il Tank può raggiungere */
	public float maxSpeed;
	/** l'accelerazione */
	public float acceleration;
	/** la decelerazione, ovvero una accelerazione negativa */
	public float deceleration;
	/** rappresenta i giri del motore */
	public int engineRevolution;

}