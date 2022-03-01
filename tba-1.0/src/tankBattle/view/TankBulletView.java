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

package tankBattle.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

import tankBattle.model.component.Bullet;
import tankBattle.model.component.LeadBullet;
import tankBattle.model.component.Tank;
import tankBattle.model.component.Turret;
import tankBattle.utilities.SoundManager;

/**
 * Visualizza il movimento del proiettile.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class TankBulletView extends AbstractTankView {

	/** il riferimento al tank */
	private Tank tank;
	/** il riferimento alla torretta del tank */
	private Turret turret;
	/** la lista dei proiettili ancora in volo */
	private LinkedList<Bullet> bulletList;
	/** segnala se un proiettile è stato esploso */
	private boolean explosion = false;
	/** il thread che visualizza l'esplosione del proiettile */
	private BulletExplosion bulletExplosion;
	/**
	 * indica il numero di proiettili che possono essere sparati
	 * contemporaneamente
	 */
	private static final int parallelNumberOfBullet = 6;

	/**
	 * Crea una vista alla quale viene delegato il compito di visualizzare i
	 * proiettili sparati dal tank.
	 * 
	 * @param width
	 *            la larghezza del pannello di gioco.
	 * @param height
	 *            l'altezza del pannello di gioco.
	 * @param tank
	 *            il riferimento al tank.
	 */
	public TankBulletView(int width, int height, Tank tank) {
		super(width, height);
		this.tank = tank;
		turret = tank.getTurret();
		bulletList = new LinkedList<Bullet>();
	}

	@Override
	public void updateObserver() {
		repaint();
	}

	/**
	 * Spara un proiettile. Questo metodo permette di sparare 6 proiettili
	 * contemporaneamente, dopodiché bisogna attendere che un proiettile esploda
	 * per poter sparare il successivo.
	 */
	public synchronized void shoot() {
		if (bulletList.size() < parallelNumberOfBullet) {
			Bullet b = new LeadBullet(tank.getID(), turret.getCenterX(), turret
					.getCenterY(), turret.getAngle(), this);
			bulletList.addLast(b);
			new Thread(b).start();
			SoundManager.player().shoot();
		}
	}

	/**
	 * Elimina il proiettile sparato dalla lista dei proiettili attivi.
	 */
	public synchronized void killBullet() {
		Bullet b = bulletList.removeFirst();
		explosion = true;
		bulletExplosion = new BulletExplosion((Graphics2D) getGraphics(), b,
				this);
		bulletExplosion.start();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		synchronized (this) {
			for (Bullet b : bulletList) {
				g.fillOval((int) b.getX(), (int) b.getY(), 5, 5);
			}
		}
	}

}
