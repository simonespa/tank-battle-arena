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
import java.awt.Image;
import java.util.Random;
import java.util.Vector;

import tankBattle.model.component.GameComponent;
import tankBattle.model.component.Ground;
import tankBattle.model.game.Collider;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.xmlParser.Map;

/**
 * Questa classe rappresenta la view di ogni componente di tipo Ground.
 * 
 * @author Simone Spaccarotella.
 * 
 */
public final class GroundView extends View {

	/** è il riferimento ad una lista di immagini */
	private Image[] views;
	/** la lista delle posizioni che assumeranno le mattonelle */
	private int[] tile;
	/** il numero di colonne presenti nel campo di gioco */
	private int column;
	/** il numero di righe presenti nel campo di gioco */
	private int row;
	/**
	 * mappa un oggeto di tipo {@code Map}, il quale contiene una serie di
	 * {@code Wall}
	 */
	private Map map;

	/**
	 * Crea una vista per il background dell'arena.
	 * 
	 * @param width
	 *            la larghezza del pannello di gioco
	 * @param height
	 *            l'altezza del pannello di gioco
	 * @param ground
	 *            il modello che descrive il pavimento
	 */
	public GroundView(int width, int height, Ground ground) {
		super(width, height);
		views = ground.getViews();
		map = ground.getMap();
		setGround();
	}

	/**
	 * Calcola quante piastrelle devono essere utilizzate in base alla
	 * risoluzione dello schermo.
	 */
	private void setGround() {
		// determina il numero di colonne nota la larghezza
		// del monitor e la larghezza della texture
		column = width / 64;
		// se le texture non coprono per intero il monitor
		// viene aggiunta una colonna per riempire lo sfrido
		if (column % 64 != 0)
			column++;
		// stessa cosa dicasi per le righe
		row = height / 64;
		if (row % 64 != 0)
			row++;
		tile = new int[column * row];
		// questo ciclo serve ad inizializzare l'array "tile".
		// Ogni suo elemento dovrà contenere un indice, che corrisponderà
		// all'indice della texture desiderata
		Random r = new Random(System.nanoTime());
		// se la texture è solo una, si inizializza a zero l'intero array
		if (views.length == 1) {
			for (int i = 0; i < tile.length; i++) {
				tile[i] = 0;
			}
			// altrimenti si assegna un indice casuale compreso tra "0"
			// e l'indice massimo dell'array "views"
		} else {
			for (int i = 0; i < tile.length; i++) {
				tile[i] = r.nextInt(views.length);
			}
		}

	}

	/**
	 * Disegna la pavimentazione
	 */
	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		Random r = new Random(System.nanoTime());
		int index = 0;
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				g.drawImage(views[tile[index++]], 64 * i, 64 * j, null);
			}
		}
		if (map != null) {
			String imageFile = map.getSrcImage();
			if (imageFile != null) {
				Image wallImage = ImageLoader.getInstance().getImage(imageFile);
				Vector<GameComponent> obstacles = Collider.getObstacleList();
				for (GameComponent ob : obstacles) {
					g.drawImage(wallImage, (int) ob.getX(), (int) ob.getY(),
							null);
				}
			}
		}
	}

}