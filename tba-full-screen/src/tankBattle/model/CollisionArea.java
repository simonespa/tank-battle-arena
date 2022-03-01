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

import java.awt.geom.Rectangle2D;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class CollisionArea {

	/** l'area del rettangolo */
	private Rectangle2D area;
	/** il gestore delle rotazioni */
	private RotationManager angle;

	/**
	 * Crea un'area di collisione, ovvero una figura quadrangolare che servirà a
	 * delimitare la presenza di un componente e a gestire le collisioni con
	 * altri componenti del gioco.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 * @param width
	 *            la larghezza del componente
	 * @param height
	 *            l'altezza del componente
	 */
	public CollisionArea(double x, double y, double width, double height) {
		area = new Rectangle2D.Double(x, y, width, height);
		angle = new RotationManager();
	}

	/**
	 * Setta la posizione.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 */
	public void setPosition(double x, double y) {
		area.setRect(x, y, getWidth(), getHeight());
	}

	/**
	 * Restituisce l'ascissa.
	 * 
	 * @return l'ascissa della posizione
	 */
	public double getX() {
		return area.getX();
	}

	/**
	 * Restituisce l'ordinata.
	 * 
	 * @return l'ordinata della posizione
	 */
	public double getY() {
		return area.getY();
	}

	/**
	 * Restituisce l'ascissa del punto centrale dell'area di collisione.
	 * 
	 * @return l'ascissa del punto centrale
	 */
	public double getCenterX() {
		return area.getCenterX();
	}

	/**
	 * Restituisce l'ordinata del punto centrale dell'area di collisione.
	 * 
	 * @return l'ordinata del punto centrale
	 */
	public double getCenterY() {
		return area.getCenterY();
	}

	/**
	 * Setta la dimensione del componente.
	 * 
	 * @param width
	 *            la larghezza
	 * @param height
	 *            l'altezza
	 */
	public void setDimension(double width, double height) {
		area.setRect(getX(), getY(), width, height);
	}

	/**
	 * Restituisce la larghezza del componente.
	 * 
	 * @return la larghezza del componente
	 */
	public double getWidth() {
		return area.getWidth();
	}

	/**
	 * Restituisce l'altezza del componente.
	 * 
	 * @return l'altezza del componente
	 */
	public double getHeight() {
		return area.getHeight();
	}

	/**
	 * Ruota il componente in senso orario.
	 */
	public void rotateClockwise() {
		angle.rotateClockwise();
	}

	/**
	 * Ruota il componente in senso antiorario.
	 */
	public void rotateAnticlockwise() {
		angle.rotateAnticlockwise();
	}

	/**
	 * Posiziona il componente nella direzione di uno dei quattro punti
	 * cardinali principali.
	 * 
	 * @see RotationManager
	 * @param towards
	 *            uno dei quattro punti cardinali principali. Può essere:
	 *            RotationManager.NORTHWARD, RotationManager.SOUTHWARD,
	 *            RotationManager.WESTWARD, RotationManager.EASTWARD
	 */
	public void locate(int towards) {
		angle.locate(towards);
	}

	/**
	 * Restituisce l'angolo di rotazione del componente.
	 * 
	 * @return l'angolo di rotazione
	 */
	public double getAngle() {
		return angle.getAngle();
	}
	
	/**
	 * Setta l'indice dell'angolo corrente.
	 * 
	 * @param curAngle un intero che rappresenta l'indice dell'angolo corrente.
	 */
	public void setCurrentAngle(int curAngle) {
		angle.setCurrentAngle(curAngle);
	}
	
	/**
	 * Restituisce l'indice dell'angolo corrente.
	 * 
	 * @return un intero che rappresenta l'indice dell'angolo corrente.
	 */
	public int getCurrentAngle() {
		return angle.getCurrentAngle();
	}

	/**
	 * Restituisce l'area dell'oggetto.
	 * 
	 * @return un oggetto Rectangle2D.
	 */
	public Rectangle2D getArea() {
		return area;
	}

	/**
	 * Restituisce una copia dell'istanza di questo oggetto.
	 */
	@Override
	public CollisionArea clone() {
		return new CollisionArea(area.getX(), area.getY(), area.getWidth(),
				area.getHeight());
	}

}
