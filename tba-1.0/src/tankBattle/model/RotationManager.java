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

package tankBattle.model;

/**
 * Questa classe gestisce le rotazioni di un componente su se stesso. Il punto
 * di riposo, ovvero l'angolo zero, è la direzione NORD
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class RotationManager {

	/** verso nord */
	public static final int NORTHWARD = 48;
	/** verso sud */
	public static final int SOUTHWARD = 16;
	/** verso ovest */
	public static final int WESTWARD = 32;
	/** verso est */
	public static final int EASTWARD = 0;

	/** indica il numero di spiazzamenti della rotazione */
	private static final int NUMBER_OF_ANGLE = 64;
	/** è l'indice dell'array che contiene gli spiazzamenti */
	private int currentAngle;
	/** contiene tutti gli spiazzamenti necessari ad una rotazione completa */
	private double[] angles;

	/**
	 * Crea un gestore delle rotazioni.
	 */
	public RotationManager() {
		currentAngle = 0;
		angles = new double[NUMBER_OF_ANGLE];
		// è la variabile che divisa per 10 esprimerà l'angolo in radianti
		double count = 0;
		for (int i = 0; i < NUMBER_OF_ANGLE; i++) {
			if (i == 33)
				count--;
			angles[i] = count / 10;
			count++;
		}
		// tutti questi sono solo degli accorgimenti tecnici
		// per poter dare fluidità alla rotazione
		angles[16] = 1.57;
		angles[32] = 3.14;
		angles[33] = 3.2;
		angles[48] = 4.71;
	}

	/**
	 * Restituisce l'ampiezza dell'angolo di rotazione rispetto al punto di
	 * riposo.
	 * 
	 * @return è l'angolo di rotazione del componente
	 */
	public double getAngle() {
		return angles[currentAngle];
	}

	/**
	 * Restituisce l'indice dell'angolo corrente.
	 * 
	 * @return un intero che si riferisce all'indice dell'angolo corrente.
	 */
	public int getCurrentAngle() {
		return currentAngle;
	}

	/**
	 * Setta l'indice che rappresenta l'angolo corrente.
	 * 
	 * @param curAngle
	 */
	public void setCurrentAngle(int curAngle) {
		if (curAngle >= 0 && curAngle < NUMBER_OF_ANGLE) {
			currentAngle = curAngle;
		} else {
			currentAngle = 0;
		}
	}

	/**
	 * Incrementa la rotazione dell'angolo in senso antiorario.
	 */
	public void rotateAnticlockwise() {
		currentAngle = (currentAngle - 1);
		if (currentAngle == -1) {
			currentAngle = NUMBER_OF_ANGLE - 1;
		}
	}

	/**
	 * Incrementa la rotazione dell'angolo in senso orario.
	 */
	public void rotateClockwise() {
		currentAngle = (currentAngle + 1) % NUMBER_OF_ANGLE;
	}

	/**
	 * Setta il verso dell'oggetto in uno dei quattro punti cardinali
	 * principali, ovvero: NORD, SUD, OVEST, EST.
	 * 
	 * @param towards
	 *            può assumere uno dei seguenti valori:
	 *            RotationManager.NORTHWARD, RotationManager.SOUTHWARD,
	 *            RotationManager.WESTWARD, RotationManager.EASTWARD. Se viene
	 *            passato un valore diverso, verrà lanciata un'eccezione del
	 *            tipo {@link IllegalArgumentException}
	 */
	public void locate(int towards) {
		if (towards < 0 || towards >= NUMBER_OF_ANGLE) {
			throw new IllegalArgumentException(
					"Passed argument isn't a legal cardinal point");
		} else {
			currentAngle = towards;
		}
	}

}