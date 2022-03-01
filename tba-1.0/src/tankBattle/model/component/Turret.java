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

import java.awt.Color;
import java.io.Serializable;

import tankBattle.model.CollisionArea;
import tankBattle.utilities.ImageLoader;
import tankBattle.view.TankBulletView;
import tankBattle.view.TankTurretView;

/**
 * L'istanza di questa classe rappresenta il componente "Turret". Questo
 * componente ï¿½ la torretta del "Tank", la quale puï¿½ roteare su se stessa e
 * sparare i proiettili.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Turret extends ColoredComponent implements Serializable,
		Revolving {

	private static final long serialVersionUID = -3854431586326602417L;
	private TankTurretView turretObserver;
	private TankBulletView bulletObserver;

	/**
	 * Istanzia una torretta con una posizione centrale rispetto al Tank e con
	 * lo stesso colore.
	 * 
	 * @param x
	 *            l'ascissa della posizione centrale rispetto al Tank.
	 * @param y
	 *            l'ordinata della posizione centrale rispetto al Tank.
	 * @param color
	 *            il colore del tank.
	 */
	public Turret(double x, double y, Color color) {
		super(x, y, color);
	}

	@Override
	public void setStartPositionToward(int cardinalPoint) {
		collisionArea.locate(cardinalPoint);
	}

	@Override
	protected void initView() {
		view = ImageLoader.getInstance().getTankTurret(color);
	}

	@Override
	protected void initProperties() {
	}

	@Override
	public void turnLeft() {
		collisionArea.rotateAnticlockwise();
	}

	@Override
	public void turnRight() {
		collisionArea.rotateClockwise();
	}

	@Override
	public double getAngle() {
		return collisionArea.getAngle();
	}

	/**
	 * Setta l'indice dell'angolo corrente.
	 * 
	 * @param currentAngle
	 *            un intero che corrisponde al i-esimo spiazzamento della
	 *            torretta.
	 */
	public void setCurrentAngle(int currentAngle) {
		collisionArea.setCurrentAngle(currentAngle);
		notifyObservers();
	}

	/**
	 * Restituisce l'indice dell'angolo corrente.
	 * 
	 * @return un intero che corrisponde al i-esimo spiazzamento della torretta.
	 */
	public int getCurrentAngle() {
		return collisionArea.getCurrentAngle();
	}

	@Override
	public CollisionArea getCollisionArea() {
		return collisionArea;
	}

	/**
	 * Spara.
	 */
	public void shoot() {
		bulletObserver.shoot();
	}

	/**
	 * Notifica l'observer.
	 */
	public void notifyObservers() {
		turretObserver.updateObserver();
	}

	/**
	 * 
	 * @param observer
	 */
	public void registerTurretObserver(TankTurretView observer) {
		turretObserver = observer;
	}

	public void registerBulletObserver(TankBulletView observer) {
		bulletObserver = observer;
	}

}