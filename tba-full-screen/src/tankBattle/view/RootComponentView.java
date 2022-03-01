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

import java.util.HashMap;
import java.util.Map;

import javax.swing.JLayeredPane;

import tankBattle.model.component.Ground;
import tankBattle.model.component.Tank;

/**
 * Questa classe rappresenta il nodo principale della view. Ad esso
 * vengono agganciate tutte le viste di tutti i componenti principali.
 * E' un pannello a layer il quale permette la sovrapposizione in trasparenza
 * di tutte le view. Ogni view ha un livello assegnato ed interagisce con gli
 * altri indipendentemente dal livello occupato. La differenza di livello ha
 * influenza solo sulla visualizzazione dei componenti e non sull'interazione
 * dei componenti dal punto di vista delle collisioni.
 * 
 * @author Simone Spaccarotella.
 *
 */
public final class RootComponentView extends JLayeredPane {

	/* questa struttura dati mappa ogni componente con la rispettiva view */
	private Map<Tank, TankView> tankMap;
	private int numberOfTank;
	private int width;
	private int height;

	/**
	 * Crea il nodo principale per la visualizzazione dei componenti.
	 * 
	 * @param width la larghezza della superficie di gioco.
	 * @param height l'altezza della superficie di gioco.
	 */
	public RootComponentView(int width, int height) {
		super();
		tankMap = new HashMap<Tank, TankView>();
		numberOfTank = 10;
		this.width = width;
		this.height = height;
	}

	/**
	 * Setta il pavimento della scena.
	 * 
	 * @param model ï¿½ il modello del pavimento.
	 */
	public void setGround(Ground model) {
		GroundView view = new GroundView(width, height, model);
		add(view, new Integer(0));
	}

	/**
	 * Aggiunge un nuovo livello che rappresenta il tank.
	 * 
	 * @param model il modello del tank.
	 */
	public void addTank(Tank model) {
		TankView view = new TankView(width, height, model);
		tankMap.put(model, view);
		add(view.getBodyView(), new Integer(numberOfTank++));
		add(view.getTurretView(), new Integer(numberOfTank++));
		add(view.getBulletView(), new Integer(numberOfTank++));
		model.initFirstTimePosition();
	}

	/**
	 * Rimuove il livello del tank specificato nei parametri.
	 * 
	 * @param model il modello del tank.
	 */
	public void removeTank(Tank model) {
		TankView view = tankMap.get(model);
		remove(view.getTurretView());
		remove(view.getBodyView());
		remove(view.getBulletView());
		numberOfTank -= 3;
	}

	/**
	 * Restituisce il numero dei tank presenti nella scena.
	 * 
	 * @return un intero che rappresenta il numero dei tank presenti nella scena.
	 */
	public int getNumberOfTank() {
		return (numberOfTank - 10) / 3;
	}

	/**
	 * Forza l'update della scena, ridisegnando tutto il pannello.
	 */
	public void update() {
		repaint();
	}
}
