/*
 * Copyright � 2008 Simone Spaccarotella
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

package tankBattle.view;

import tankBattle.model.component.Tank;

/**
 * Questa classe rappresenta la vista del tank.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankView {

	/** la view del corpo del tank */
	private TankBodyView bodyView;
	/** la view della torretta */
	private TankTurretView turretView;
	/** la view del proiettile */
	private TankBulletView bulletView;

	/**
	 * Crea la view del tank.
	 * 
	 * @param width
	 *            la larghezza della superficie di gioco.
	 * @param height
	 *            l'altezza della superficie di gioco.
	 * @param tank
	 *            il riferimento al tank.
	 */
	public TankView(int width, int height, Tank tank) {
		bodyView = new TankBodyView(width, height, tank);
		turretView = new TankTurretView(width, height, tank);
		bulletView = new TankBulletView(width, height, tank);
		tank.registerObserver(Tank.BODY, bodyView);
		tank.registerObserver(Tank.TURRET, turretView);
		tank.registerObserver(Tank.BULLET, bulletView);
	}

	/**
	 * Restituisce la view del corpo del tank.
	 * 
	 * @return restituissce un oggetto di tipo
	 *         {@link tankBattle.view.TankBodyView TankBodyView}.
	 */
	public TankBodyView getBodyView() {
		return bodyView;
	}

	/**
	 * Restituisce la view della torretta del tank.
	 * 
	 * @return un oggetto di tipo {@link tankBattle.view.TankTurretView
	 *         TankTurretView}
	 */
	public TankTurretView getTurretView() {
		return turretView;
	}

	/**
	 * Restituisce la view del proiettile.
	 * 
	 * @return un oggetto di tipo {@link tankBattle.view.TankBulletView
	 *         TankBulletView}
	 */
	public TankBulletView getBulletView() {
		return bulletView;
	}

	/**
	 * Notifica l'update della view.
	 */
	public void update() {
		bodyView.updateObserver();
		turretView.updateObserver();
		bulletView.updateObserver();
	}

}