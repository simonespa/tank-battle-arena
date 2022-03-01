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


/**
 * Questa classe rappresenta il generico proiettile con il quale puï¿½ essere
 * armato un Tank. Questa classe puï¿½ essere estesa ed ampliata per poter
 * creare un nuovo tipo di proiettile, personalizzato.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class Bullet extends GameComponent implements Runnable {

	/* il potere distruttivo del proiettile */
	protected static int power;
	/* indica se il proiettile ï¿½ esploso contro un ostacolo */
	protected boolean alive;
	/* il riferimento alla torretta */
	protected Turret turret;

	/**
	 * Crea un proiettile.
	 * 
	 * @param pow
	 *            il potere distruttivo dell proiettile.
	 */
	public Bullet(double x, double y, int width, int height, int pow) {
		super(x, y, width, height);
		power = pow;
		alive = true;
	}

	/**
	 * Restituisce la potenza distruttiva.
	 * 
	 * @return un intero che rappresenta la potenza distruttiva del proiettile.
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Restituisce il flag boolean che rappresenta lo stato di vita o di morte.
	 * 
	 * @return {@code false} se il proiettile ï¿½ esploso contro un ostacolo.
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Setta il riferimento alla torretta dalla quale il proiettile viene
	 * esploso.
	 * 
	 * @param turret
	 *            il riferimento al componente {@code Turret}.
	 */
	public void setTurret(Turret turret) {
		this.turret = turret;
	}

	protected abstract void explodeBullet();

	/**
	 * Notifica all'observer l'avvenuto cambiamento di stato.
	 */
	protected void notifyObserver() {

	}

}