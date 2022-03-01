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

import tankBattle.model.game.Collider;
import tankBattle.view.TankBulletView;


/**
 * Le istanze di questa classe rappresentano il proiettile
 * di piombo che viene sparato dal tank durante la sessione
 * di gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class LeadBullet extends Bullet implements Runnable {
 
	private TankBulletView observer;
	private String tankID;
	private double angle;
	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;
	
	public LeadBullet(String id, double x, double y, double angle, TankBulletView view) {
		super(x, y, WIDTH, HEIGHT, 6);
		this.angle = angle;
		this.tankID = id;
		collisionArea.setPosition(x + 15, y + 15);
		observer = view;
	}
	
	public void setPosition(double x, double y) {
		collisionArea.setPosition(x, y);
	}
	
	public synchronized double getX() {
		return collisionArea.getX();
	}
	
	public synchronized double getY() {
		return collisionArea.getY();
	}
	
	public String getID() {
		return tankID;
	}

	@Override
	public void run() {
		while( move() ) {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean move() {
		double x = getX();
		double y = getY();
		x += (Math.cos(angle));
		y += (Math.sin(angle));
		setPosition(x, y);
		if (Collider.canMoveBullet(this)) {
			observer.updateObserver();
			return true;
		} else {
			explodeBullet();
			return false;
		}
	}

	@Override
	protected void initProperties() {}

	@Override
	protected void explodeBullet() {
		observer.killBullet();
	}

}
