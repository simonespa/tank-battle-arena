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

package tankBattle.model.game;

import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.Vector;

import tankBattle.model.component.Bullet;
import tankBattle.model.component.GameComponent;
import tankBattle.model.component.Tank;
import tankBattle.net.ClientServerCapable;
import tankBattle.utilities.SoundManager;

/**
 * Questa classe contiene dei metodi statici che possono essere invocati in modo
 * sincronizzato dai componenti di gioco. &Egrave; la classe deputata alla
 * gestione delle collisioni tra i componenti.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Collider implements ClientServerCapable {

	private static int minX;
	private static int minY;
	private static int maxX;
	private static int maxY;

	private static Vector<Tank> tanks;
	private static Vector<GameComponent> obstacles;

	/**
	 * Inizializza lo stato del Collider.
	 */
	public static void init() {
		minX = 0;
		minY = 0;
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		maxX = toolkit.getScreenSize().width;
		maxY = toolkit.getScreenSize().height;
		maxY = (int) (maxY - (maxY * 0.1)) - 60;
		tanks = new Vector<Tank>();
		obstacles = new Vector<GameComponent>();
	}

	/**
	 * Restituisce l'ascissa massima del pannello di gioco.
	 * 
	 * @return un intero che rappresenta la x.
	 */
	public static int getMaximumX() {
		return maxX;
	}

	/**
	 * Restituisce l'ordinata massima del pannello di gioco.
	 * 
	 * @return un intero che rappresenta la y.
	 */
	public static int getMaximumY() {
		return maxY;
	}

	/**
	 * Verifica la possibilitï¿½ di movimento del tank. Se il tank collide con
	 * qualche oggetto nella mappa, non va piï¿½ avanti.
	 * 
	 * @param id
	 *            l'ID univoco del tank.
	 * @param r1
	 *            l'area di collisione del tank.
	 * @return {@code true} se il tank può muoversi.
	 * @see tankBattle.model.CollisionArea
	 */
	public static synchronized boolean canMove(String id, Rectangle2D r1) {
		if (!checkInside(r1)) {
			return false;
		} else {
			for (Tank t : tanks) {
				if (id != t.getID()) {
					Rectangle2D r2 = t.getArea();
					if (r1.intersects(r2)) {
						return false;
					}
				}
			}
			for (GameComponent c : obstacles) {
				Rectangle2D r2 = c.getArea();
				if (r1.intersects(r2)) {
					return false;
				}
			}
			return true;
		}
	}

	/*
	 * Controlla che il movimento dell'oggetto avvenga all'interno del riquadro
	 * di gioco.
	 */
	private static boolean checkInside(Rectangle2D r) {
		if (r.getX() < minX || r.getY() < minY
				|| r.getX() > maxX - r.getWidth()
				|| r.getY() > maxY - r.getHeight()) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Verifica che il quadrilatero si trovi all'interno del rettangolo di
	 * gioco.
	 */
	private static boolean isInside(Rectangle2D r) {
		if (r.getX() < minX || r.getY() < minY
				|| r.getX() + r.getWidth() > maxX - r.getWidth()
				|| r.getY() + r.getHeight() > maxY - r.getHeight()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Verifica la possibilitï¿½ di movimento del proiettile. Se il proiettile
	 * collide con qualche oggetto nella mappa, esplode.
	 * 
	 * @param b
	 *            il proiettile di cui si vuole verificare lo spostamento.
	 * @return {@code true} se il proiettile puï¿½ muoversi.
	 */
	public static synchronized boolean canMoveBullet(Bullet b) {
		Rectangle2D r1 = b.getArea();
		if (!isInside(r1)) {
			SoundManager.player().bulletVsObstacle();
			return false;
		} else {
			for (Tank t : tanks) {
				if (!t.getID().equals(b.getID())) {
					Rectangle2D r2 = t.getArea();
					if (r1.intersects(r2)) {
						t.decreaseEnergy(b.getPower());
						SoundManager.player().bulletVsTank();
						return false;
					}
				}
			}
			for (GameComponent c : obstacles) {
				Rectangle2D r2 = c.getArea();
				if (r1.intersects(r2)) {
					SoundManager.player().bulletVsObstacle();
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Aggiunge il tank alla lista.
	 * 
	 * @param tank
	 *            il tank che partecipa al gioco.
	 */
	public static synchronized void addTank(Tank tank) {
		Random r = new Random(System.nanoTime());
		boolean done = true;
		boolean intersection = false;
		// itera fino a trovare la posizione corretta
		while (done) {
			if (isInside(tank.getArea())) {
				// controlla che il tank non collida con nessun altro tank
				for (Tank t : tanks) {
					// se c'è una sovrapposizione allora viene posto a true il
					// flag
					// intersection, che permette all'algoritmo di reiterare
					// nuovamente
					if (tank.getArea().intersects(t.getArea())) {
						intersection = true;
						break;
					}
				}
				// se non si ï¿½ verificata una intersezione
				if (!intersection) {
					// controlla che il tank non collida con nessun ostacolo
					// della
					// mappa
					for (GameComponent c : obstacles) {
						// se c'ï¿½ una sovrapposizione allora viene posto a
						// true il
						// flag
						// intersection, che permette all'algoritmo di reiterare
						// nuovamente
						if (tank.getArea().intersects(c.getArea())) {
							intersection = true;
							break;
						}
					}
				}
			} else {
				intersection = true;
			}
			// se non c'ï¿½ stata nessuna intersezione
			if (!intersection) {
				// mette il flag dell'iterazione a false
				// in modo tale da terminarla
				done = false;
			} else {
				// setta una nuova posizione
				tank.setPosition(r.nextInt(maxX), r.nextInt(maxY));
			}
			intersection = false;
		}
		// infine aggiunge l'elemento
		// nella posizione desiderata
		tanks.addElement(tank);
	}

	/**
	 * Aggiunge un ostacolo alla mappa.
	 * 
	 * @param obstacle
	 *            l'ostacolo della mappa.
	 */
	public static void addObstacle(GameComponent obstacle) {
		obstacles.addElement(obstacle);
	}

	/**
	 * Restituisce la lista dei tank in gioco.
	 * 
	 * @return un {@link java.util.Vector Vector<}
	 *         {@link tankBattle.model.component.Tank Tank>}
	 */
	public static Vector<Tank> getTankList() {
		return tanks;
	}

	/**
	 * Restituisce la lista degli ostacoli presenti nella mappa.
	 * 
	 * @return un {@link java.util.Vector Vector<}
	 *         {@link tankBattle.model.component.GameComponent GameComponent>}
	 */
	public static Vector<GameComponent> getObstacleList() {
		return obstacles;
	}

	/**
	 * Rimuove il {@link tankBattle.model.component.Tank Tank} specificato nei
	 * parametri.
	 * 
	 * @param tank
	 *            ï¿½ il tank che dev'essere eliminato dalla lista di gioco.
	 */
	public static void removeTank(Tank tank) {
		tanks.removeElement(tank);
	}

	/**
	 * Rimuove tutti i tank presenti nella lista di gioco.
	 */
	public static void removeAllTanks() {
		tanks.removeAllElements();
	}

	/**
	 * Rimuove il {@link tankBattle.model.component.GameComponent GameComponent}
	 * specificato nei parametri.
	 * 
	 * @param obstacle
	 *            il componente da eliminare dalla mappa.
	 */
	public static void removeObstacle(GameComponent obstacle) {
		obstacles.removeElement(obstacle);
	}

	/**
	 * Rimuove tutti i componenti presenti nella mappa.
	 */
	public static void removeAllObstacles() {
		obstacles.removeAllElements();
		obstacles = new Vector<GameComponent>();
	}

}
