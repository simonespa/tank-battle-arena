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

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import tankBattle.model.CollisionArea;
import tankBattle.utilities.StringsProperty;
import tankBattle.utilities.Utility;

/**
 * E' la prima classe nella gerarchia dei componenti di Tank Battle. Questa
 * classe sta ad indicare che tutti i componenti del gioco sono dei {@code
 * GameComponent}. Da questa classe derivano poi tante altre classi, e man mano
 * che si scende nella gerarchia sono sempre più dettagliate e arricchite da
 * nuovi attributi e metodi che ne definiscono la propria funzione
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class GameComponent {

	/** è l'area di collisione del componente */
	protected CollisionArea collisionArea;
	/** è il tipo di componente. Restituisce il nome della classe */
	protected String type;
	/** è il numero seriale della singola istanza */
	protected String id;
	/** è il contatore globale delle istanze di questa classe */
	protected static int numberOfInstance = 0;

	/**
	 * Crea un componente di gioco con posizione {@code (0, 0}, ovvero
	 * nel'angolo superiore sinistro del pannello di gioco e dimensione vuota.
	 */
	public GameComponent() {
		this(0, 0, 0, 0);
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public GameComponent(double x, double y) {
		this(x, y, 0, 0);
	}

	/**
	 * Crea un componente di gioco con posizione {@code (x, y)}, larghezza
	 * {@code width} ed altezza {@code height}.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 * @param width
	 *            la larghezza
	 * @param height
	 *            l'altezza
	 */
	public GameComponent(double x, double y, double width, double height) {
		checkConstructorParameter(x, y, width, height);
		collisionArea = new CollisionArea(x, y, width, height);
		setType();
		initProperties();
		numberOfInstance++;
	}

	/**
	 * Controlla che i parametri passati al costruttore non siano negativi.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 * @param w
	 *            la larghezza
	 * @param h
	 *            l'altezza
	 */
	private void checkConstructorParameter(double x, double y, double w,
			double h) {
		try {
			// se uno dei parametri passati è negativo
			if (x < 0 || y < 0 || w < 0 || h < 0) {
				// lancia un'eccezione
				throw new IllegalArgumentException(
						"position and dimension can't be negative");
			}
		} catch (IllegalArgumentException e) {
			// e la gestisce nello stesso metodo
			Utility.logException(this, e);
		}
	}

	/**
	 * Questo metodo inizializza i campi della classe. Viene dichiarato astratto
	 * in modo tale che ogni sottoclasse possa scegliere come implementare
	 * questo metodo. In questo modo è possibile inizializzare i campi da file
	 * oppure dalla rete o in modo statico.
	 */
	protected abstract void initProperties();

	/**
	 * Inizializza le proprietà della classe da file.
	 * 
	 * @param properties
	 *            la mappa delle proprietà
	 */
	protected Map<String, String> getPropertiesFromFile() {
		// crea la mappa delle proprietà
		Map<String, String> properties = new HashMap<String, String>();
		// crea il descrittore del file
		File file = new File("config/component.properties");
		BufferedReader input = null;
		try {
			String key;
			String value;
			String line;
			// acquisisce lo stream di input del file
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			// acquisisce il nome della classe che invoca questo metodo
			String className = getClassName();
			// scorre il file fino ad arrivare alla prima stringa di proprietà
			// dell'oggetto che invoca il metodo
			do {
				line = input.readLine();
			} while (!line.startsWith(className));
			// scorre tutte le proprietà riguardanti l'oggetto e salta i
			// commenti
			while (line.startsWith(className) || line.startsWith("#")) {
				// la stringa è un commento lo salta e legge la riga successiva
				if (line.startsWith("#")) {
					line = input.readLine();
					continue;
				}
				// acquisisce la stringa di proprietà
				StringsProperty.acquireProperty(line);
				// restituisce la chiave
				key = StringsProperty.getKey();
				// restituisce il valore
				value = StringsProperty.getValue();
				// setta la proprietà
				properties.put(key, value);
				// legge la riga successiva
				line = input.readLine();
			}
		} catch (FileNotFoundException e) {
			Utility.logException(e);
		} catch (IOException e) {
			Utility.logException(e);
		} finally {
			try {
				// chiude lo stream di input
				input.close();
			} catch (IOException e) {
				Utility.logException(e);
			}
		}
		return properties;
	}

	/**
	 * Restituisce il nome della classe che invoca il metodo.
	 * 
	 * @return il nome della classe
	 */
	protected String getClassName() {
		// acquisisce il nome canonico della classe
		String canonicalName = this.getClass().getCanonicalName();
		// splitta la stringa con il "."
		String[] split = canonicalName.split("\\.");
		// acquisisce l'ultima stringa che corrisponde al nome della classe
		// senza
		// il nome dei vari package, e lo restituisce
		return split[split.length - 1];
	}

	/**
	 * Setta il tipo, ovvero, il nome della classe.
	 */
	private void setType() {
		type = getClassName();
	}

	/**
	 * Restituisce il tipo.
	 * 
	 * @return il tipo di oggetto
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setta l'ID.
	 * 
	 * @param id
	 *            l'identificativo dell'oggetto
	 */
	protected void setID(String id) {
		this.id = id;
	}

	/**
	 * Restituisce l'ID dell'istanza della classe.
	 * 
	 * @return l'ID dell'istanza
	 */
	public String getID() {
		return id;
	}

	/**
	 * Restituisce il numero delle istanze create
	 * 
	 * @return un intero che rappresenta il numero delle istanze create.
	 */
	public static int getNumberOfIstance() {
		return numberOfInstance;
	}

	/**
	 * Setta la posizione dell'area di collisione.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 */
	public void setPosition(double x, double y) {
		collisionArea.setPosition(x, y);
	}

	/**
	 * Restituisce l'ascissa della posizione.
	 * 
	 * @return l'ascissa della posizione
	 */
	public double getX() {
		return collisionArea.getX();
	}

	/**
	 * Restituisce l'ordinata della posizione.
	 * 
	 * @return l'ordinata della posizione
	 */
	public double getY() {
		return collisionArea.getY();
	}

	/**
	 * Restituisce la mediana dell'ascissa rispetto all'area di collisione.
	 * 
	 * @return la mediana dell'ascissa rispetto all'area di collisione
	 */
	public double getCenterX() {
		return collisionArea.getCenterX();
	}

	/**
	 * Restituisce la mediana dell'ordinata rispetto all'area di collisione.
	 * 
	 * @return la mediana dell'ordinata rispetto all'area di collisione
	 */
	public double getCenterY() {
		return collisionArea.getCenterY();
	}

	/**
	 * Restituisce il rettangolo dell'area di collisione.
	 * 
	 * @return un oggetto Rectangle2D
	 */
	public Rectangle2D getArea() {
		return collisionArea.getArea();
	}

	/**
	 * Setta la dimensione dell'area di collisione.
	 * 
	 * @param width
	 *            la larghezza
	 * @param height
	 *            l'altezza
	 */
	protected void setDimension(double width, double height) {
		collisionArea.setDimension(width, height);
	}

	/**
	 * Restituisce la larghezza dell'area di collisione.
	 * 
	 * @return la larghezza dell'area di collisione
	 */
	public double getWidth() {
		return collisionArea.getWidth();
	}

	/**
	 * Restituisce l'altezza dell'area di collisione.
	 * 
	 * @return l'altezza dell'area di collisione
	 */
	public double getHeight() {
		return collisionArea.getHeight();
	}

	/**
	 * Restituisca una copia dell'area di collisione, in modo da evitare side
	 * effects.
	 * 
	 * @return l'area di collisione
	 */
	public CollisionArea getCollisionArea() {
		return collisionArea.clone();
	}

	@Override
	protected void finalize() throws Throwable {
		numberOfInstance--;
	}

}