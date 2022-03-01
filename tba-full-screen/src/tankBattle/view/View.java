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

import javax.swing.JPanel;

/**
 * Questa classe è l'antenata di tutta la gerarchia delle view.
 * In questo programma, una view è fondamentalmente un pannello
 * trasparente, il quale disegna il componente a cui è associato.
 * Ogni view rappresenta un nodo della gerarchia di visualizzazione.
 * Il nodo radice è rappresentato dalla classe
 * {@link tankBattle.view.RootComponentView RootComponentView}, a cui
 * verranno agganciati tutti i nodi secondari. Questo nodo radice
 * è un pannello con layer che gestisce la sovrapposizione.
 * 
 * @author Simone Spaccarotella
 *
 */
public abstract class View extends JPanel {

	/* la larghezza della superficie di gioco */
	protected int width;
	/* l'altezza della superficie di gioco */
	protected int height;

	/**
	 * Inizializza i parametri generali della view.
	 * 
	 * @param width la larghezza della superficie di gioco.
	 * @param height l'altezza della superficie di gioco.
	 */
	public View(int width, int height) {
		super(null, true);
		this.width = width;
		this.height = height;
		setIgnoreRepaint(true);
		setBounds(0, 0, width, height);
	}

}
