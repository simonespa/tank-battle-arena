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
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import tankBattle.model.game.Collider;
import tankBattle.model.game.TankBattleGame;
import tankBattle.net.ClientServerCapable;
import tankBattle.utilities.ImageLoader;
import tankBattle.utilities.TankEngineProperties;
import tankBattle.utilities.TankProperties;
import tankBattle.utilities.Utility;
import tankBattle.view.AbstractTankView;
import tankBattle.view.TankBulletView;
import tankBattle.view.TankTurretView;

/**
 * Questa classe rappresenta il componente principale di tutto il gioco. Questo
 * componente &egrave; l'attore principale, ovvero il "Tank". Una volta
 * istanziato, bisogna registrarlo, come tutti i componenti daltronde, ad un
 * observer, il quale ridisegner&agrave; il suo stato sul monitor. Va anche
 * registrato ad un controller, dal quale sar&agrave; possibile controllarlo.
 * 
 * Dopo aver istanziato questo componente, &egrave; essenziale farlo partire, in
 * modo da accendere il motore interno, che gestisce i suoi spostamenti.
 * 
 * Il motore &egrave; un thread che gestisce i movimenti del Tank, in base ai
 * parametri del terreno, e quindi ne gestisce l'accelerazione, la frenata e la
 * velocità massima.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class Tank extends ActorGameComponent implements Serializable,
		Runnable, Moveable, Revolving, Shooter, ClientServerCapable {

	/* il numero seriale */
	private static final long serialVersionUID = 9045191248233259900L;

	/* la mappa degli observer di questo componente */
	private Map<String, AbstractTankView> observers;
	/* flag boolean che serve ad inizializzare la vista del tank la prima volta */
	private boolean firstTime;
	/* è l'etichetta che visualizza il punteggio del tank */
	private JLabel updater;

	// //////////////////////////// MOTORE //////////////////////////
	/** il thread che simula il motore del Tank */
	private Thread engine;
	/** il flag che indica se il motore &egrave; acceso o spento */
	private boolean run;
	/** il flag che indica se il Tank &egrave; in accelerazione */
	private boolean speedUp;
	/** la velocità */
	private float speed;
	/** la velocità massima che il Tank puï¿½ raggiungere */
	private float maxSpeed;
	/** l'accelerazione */
	private float acceleration;
	/** la decelerazione, ovvero una accelerazione negativa */
	private float deceleration;
	/** rappresenta i giri del motore */
	private int engineRevolution;
	// /////////////////////////////////////////////////////////////////

	/** la torretta dalla quale vengono sparati i proiettili */
	private Turret turret;

	/** indica se il tank &egrave; visibile al nemico */
	private boolean visible;
	/**
	 * se true, quando il tank sarà invisibile, verrà disegnato semi
	 * trasparente, altrimenti verrà disegnato completamente trasparente
	 */
	private boolean locale;
	/** indica se il tank &egrave; corazzato */
	private boolean armour;
	/** l'energia residua */
	private int energy;
	/** lo stato corrente di moto in cui si trova il tank **/
	private int currentMotionState;
	/** definisce se &egrave; stato premuto il tasto di marcia avanti o indietro */
	private int direction;
	/** indica che il tank &egrave; stato ucciso */
	private boolean killed;

	/** indica che il motore si trova nello stato "vai avanti" */
	public static final int AHEAD = 1;
	/** indica che il motore si trova nello stato "neutro" (in folle) */
	public static final int NEUTER = 0;
	/** indica che il motore si trova nello stato "vai indietro" */
	public static final int BACK = -1;

	/** definisce il corpo del tank, dal punto di vista dell'observer */
	public static final String BODY = "body";
	/** definisce la torretta del tank, dal punto di vista dell'observer */
	public static final String TURRET = "turret";
	/** definisce i proiettili che vengono sparati dal tank, dal punto di vista dell'observer */
	public static final String BULLET = "bullet";

	/**
	 * Crea un tank di nome {@code name} di colore {@code color}.
	 * 
	 * @param color
	 *            il colore che assumerï¿½ il tank. Di default ï¿½ blu.
	 * @param name
	 *            il nome del tank
	 */
	public Tank(Color color, String name) {
		this(0, 0, color, name);
	}

	/**
	 * Crea un tank di nome {@code name} di colore {@code color}, con una
	 * posizione {@code (x, y)}.
	 * 
	 * @param x
	 *            l'ascissa della posizione
	 * @param y
	 *            l'ordinata della posizione
	 * @param color
	 *            il colore del tank
	 * @param name
	 *            il nome del tank
	 */
	public Tank(double x, double y, Color color, String name) {
		super(x, y, color, name);
	}

	@Override
	protected void initProperties() {
		TankProperties tankProperties = loadTankProperties();
		TankEngineProperties properties = tankProperties
				.getConfiguration(TankBattleGame.getMap());
		run = false;
		speedUp = false;
		speed = 0.0f;
		maxSpeed = properties.maxSpeed;
		acceleration = properties.acceleration;
		deceleration = properties.deceleration;
		engineRevolution = properties.engineRevolution;
		/* inizializza le proprietÃ  */
		visible = true;
		armour = false;
		energy = 120;
		currentMotionState = NEUTER;
		direction = NEUTER;
		// inizializza gli observers
		observers = new HashMap<String, AbstractTankView>();
	}

	/**
	 * Restituisce un oggetto serializzabile che contiene i parametri di
	 * inizializzazione del tank.
	 * 
	 * @return un oggetto di tipo {@link tankBattle.utilities.TankProperties}
	 */
	private TankProperties loadTankProperties() {
		File file = new File("config/tank.init");
		ObjectInputStream input = null;
		TankProperties properties = null;
		try {
			input = new ObjectInputStream(new FileInputStream(file));
			properties = (TankProperties) input.readObject();
		} catch (FileNotFoundException e) {
			Utility.logException(this, e);
		} catch (IOException e) {
			Utility.logException(this, e);
		} catch (ClassNotFoundException e) {
			Utility.logException(this, e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Utility.logException(this, e);
				}
			}
		}
		return properties;
	}

	@Override
	protected void initView() {
		view = ImageLoader.getInstance().getTankBody(null);
		setDimension(38, 38);
		view = ImageLoader.getInstance().getTankBody(color);
		turret = new Turret(getCenterX(), getCenterY(), color);
	}

	/**
	 * Registra gli "observer" presso il tank.
	 * 
	 * @param name
	 *            il tipo di observer. Puï¿½ assumere solo due valori: BODY e
	 *            TURRET.
	 * @param observer
	 *            l'observer.
	 */
	public void registerObserver(String name, AbstractTankView observer) {
		observers.put(name, observer);
		if (name.equals(TURRET)) {
			turret.registerTurretObserver((TankTurretView) observer);
		} else if (name.equals(BULLET)) {
			turret.registerBulletObserver((TankBulletView) observer);
		}
	}

	/**
	 * Notifica gli observer.
	 */
	public void notifyObservers() {
		if (observers.get(BODY) != null && observers.get(TURRET) != null
				&& observers.get(BULLET) != null) {
			observers.get(BODY).updateObserver();
			observers.get(TURRET).updateObserver();
			observers.get(BULLET).updateObserver();
		}
	}

	/**
	 * Notifica l'observer specificato dal nome.
	 * 
	 * @param name
	 *            il nome dell'observer.
	 */
	public void notifyObserver(String name) {
		AbstractTankView obj = observers.get(name);
		if (obj != null) {
			obj.updateObserver();
		}
	}

	/**
	 * Setta il nuovo ogetto che aggiornerà lo stato del punteggio del tank.
	 * 
	 * @param label
	 *            l'etichetta che visualizza l'energia.
	 */
	public void registerScoreObserver(JLabel label) {
		updater = label;
	}

	/**
	 * Aggiorna il punteggio del tank sull'updater.
	 */
	private void updateScore() {
		if (updater != null)
			updater.setText(String.valueOf(energy));
	}

	@Override
	public void setID(String id) {
		super.setID(id);
	}

	@Override
	public void setStartPositionToward(int cardinalPoint) {
		collisionArea.locate(cardinalPoint);
		turret.setStartPositionToward(cardinalPoint);
	}

	/**
	 * Setta l'indice dell'angolo attuale.
	 * 
	 * @param curAngle
	 */
	public void setCurrentAngle(int curAngle) {
		collisionArea.setCurrentAngle(curAngle);
		notifyObserver(BODY);
	}

	/**
	 * Restituisce l'indice del angolo attuale.
	 * 
	 * @return un intero che rappresenta l'indice dell'angolo corrente.
	 */
	public int getCurrentAngle() {
		return collisionArea.getCurrentAngle();
	}

	/**
	 * Setta il parametro di visibilitï¿½ in base allo stato dell'oggetto tank e
	 * in funzione dei bonus acquisiti.
	 * 
	 * @param visible
	 *            flag booleano. Se {@code false} il tank risulterï¿½ invisibile
	 */
	public void setVisible(boolean visible) {
		/*
		 * prima di mettere visible a true, bisogna controllare se il tank ha
		 * ricevuto il potere da un bonus trovato nella mappa
		 */
		this.visible = visible;
		notifyObservers();
	}

	/**
	 * Restituisce lo stato di visibilitï¿½ del tank.
	 * 
	 * @return true se il tank ï¿½ visibile
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Setta lo stato di protezione del tank.
	 * 
	 * @param armour
	 *            se armour ï¿½ true allora il tank ï¿½ corazzato
	 */
	public void setArmour(boolean armour) {
		this.armour = armour;
	}

	/**
	 * Restituisce lo stato di protezione del tank.
	 * 
	 * @return true se il tank ï¿½ corazzato
	 */
	public boolean isArmour() {
		return armour;
	}

	/**
	 * Restituisce lo stato di vita del tank.
	 * 
	 * @return true se il tank ï¿½ stato ucciso mediante il metodo {@code
	 *         kill()}
	 */
	public boolean isKilled() {
		return killed;
	}

	/**
	 * Incrementa l'energia in base ai bonus acquisiti sulla mappa.
	 * 
	 * @param power
	 *            la quantitï¿½ d'incremento
	 */
	public void increaseEnergy(int power) {
		/*
		 * prima di aumentare l'energia bisogna vedere che tipo di bonus è stato
		 * ricevuto
		 */
	}

	/**
	 * Decrementa l'energia in base ai colpi ricevuti.
	 * 
	 * @param power
	 *            la quantitï¿½ di decremento
	 */
	public void decreaseEnergy(int power) {
		energy -= power;
		if (energy < 0) {
			energy = 0;
		}
		updateScore();
		if (energy == 0) {
			kill();
		}
	}

	/**
	 * Restituisce l'energia.
	 * 
	 * @return la quantit&agrave; di energia residua
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * E' il cuore del thread. Il motore regola lo spostamento fornendo
	 * un'accelerazione e una decelerazione costante.
	 */
	@Override
	public void run() {
		while (run) {
			manageRevolution(engineRevolution);
			if (speedUp) {
				if (move()) {
					accelerate(acceleration);
					notifyObservers();
				} else {
					speed = 0.0F;
				}
			} else {
				if (move()) {
					decelerate(deceleration);
					notifyObservers();
				} else {
					speed = 0.0F;
				}
			}
		}
		while (speed > 0) {
			if (move()) {
				decelerate(deceleration);
				notifyObservers();
				manageRevolution(engineRevolution);
			} else {
				speed = 0.0F;
			}
		}
	}

	/**
	 * Setta la posizione del tank, muovendolo in una nuova posizione. Se la
	 * posizione di destinazione ï¿½ occupata da un altro componente, lo
	 * spostamento non andrï¿½ a buon fine, e il tank resterï¿½ nello stesso
	 * punto di partenza.
	 */
	private boolean move() {
		double x = getX();
		double y = getY();
		if (currentMotionState == AHEAD) {
			x += (speed * Math.cos(getAngle()));
			y += (speed * Math.sin(getAngle()));
		} else if (currentMotionState == BACK) {
			x -= (speed * Math.cos(getAngle()));
			y -= (speed * Math.sin(getAngle()));
		}
		Rectangle2D rTemp = new Rectangle2D.Double(x, y, getWidth(),
				getHeight());
		if (Collider.canMove(id, rTemp)) {
			setPosition(x, y);
			turret.setPosition(x, y);
			TankBattleGame.printToOutputStream(MOVE + ":" + x + ":" + y + "@"
					+ id);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Accelera il tank.
	 * 
	 * @param delta
	 *            ï¿½ la quantitï¿½ di accelerazione positiva (accelerazione)
	 */
	private void accelerate(float delta) {
		if ((speed = (speed + delta)) > maxSpeed)
			speed = maxSpeed;
	}

	/**
	 * Decelera il tank.
	 * 
	 * @param delta
	 *            ï¿½ la quantitï¿½ di accelerazione negativa (decelerazione)
	 */
	private void decelerate(float delta) {
		// se mentre sto andando in una direzione premo il tasto
		// per andare nella direzione opposta, allora aumenta il fattore
		// di decelerazione "delta"
		if (currentMotionState != direction) {
			delta = delta * 2;
		}
		if ((speed = (speed - delta)) < 0)
			speed = 0;
	}

	@Override
	public synchronized void goAhead() {
		// setta il flag che indica la direzione
		direction = AHEAD;
		// solo se la velocitï¿½ ï¿½ pari a zero puï¿½ essere cambiato lo stato
		// di moto corrente e settare il flag di accelerazione a true
		if (speed == 0.0) {
			speedUp = true;
			currentMotionState = AHEAD;
			synchronized (this) {
				notifyAll();
			}
		}
	}

	@Override
	public synchronized void goBack() {
		direction = BACK;
		if (speed == 0.0) {
			speedUp = true;
			currentMotionState = BACK;
			synchronized (this) {
				notifyAll();
			}
		}
	}

	/**
	 * Accende il motore del tank.
	 */
	public void start() {
		run = true;
		speedUp = false;
		killed = false;
		firstTime = true;
		if (engine == null) {
			engine = new Thread(this);
			engine.start();
		}
	}

	/**
	 * Inizializza la vista nella posizione iniziale, solo la prima volta.
	 */
	public void initFirstTimePosition() {
		if (firstTime) {
			goAhead();
			slowDown();
			firstTime = false;
		}
	}

	/**
	 * Setta il parametro {@code locale}. Questo parametro determina il tipo di
	 * rappresentazione del tank quando &egrave; trasparente.
	 * 
	 * @param locale
	 *            se true, allora il tank si trova sull'host, altrimenti
	 *            &egrave; un tank remoto.
	 */
	public void setLocale(boolean locale) {
		this.locale = locale;
	}

	/**
	 * Restituisce il parametro {@code locale}. Questo parametro determina il
	 * tipo di rappresentazione del tank quando &egrave; trasparente.
	 * 
	 * @return {@code true} se il tank si trova sull'host, {@code false} se il
	 *         tank &egrave; in remoto.
	 */
	public boolean isLocale() {
		return locale;
	}

	/**
	 * Uccide il tank
	 */
	public void kill() {
		run = false;
		synchronized (this) {
			notifyAll();
		}
		killed = true;
		TankBattleGame.removeTank(id);
	}

	/**
	 * Attiva il flag di decelerazione.
	 */
	public synchronized void slowDown() {
		speedUp = false;
	}

	/**
	 * Restituisce un riferimento alla torretta.
	 * 
	 * @return la torretta del Tank.
	 */
	public Turret getTurret() {
		return turret;
	}

	/**
	 * Gestisce i tempi morti del motore, mettendolo opportunamente a riposo.
	 * 
	 * @param indica
	 *            il numero di giri del motore.
	 */
	private void manageRevolution(int revolution) {
		try {
			// se la velocitï¿½ ï¿½ pari a zero mette in attesa il motore
			if (speed == 0.0) {
				synchronized (this) {
					wait();
				}
				// altrimenti lo fa dormire per "revolution" millisecondi
			} else {
				Thread.sleep(revolution);
			}
		} catch (InterruptedException e) {
			Utility.logException(e.getMessage());
		}
	}

	@Override
	public synchronized void turnLeft() {
		collisionArea.rotateAnticlockwise();
		notifyObserver(BODY);
	}

	@Override
	public synchronized void turnRight() {
		collisionArea.rotateClockwise();
		notifyObserver(BODY);
	}

	public synchronized void rotateTurretLeft() {
		turret.turnLeft();
		notifyObserver(TURRET);
	}

	public synchronized void rotateTurretRight() {
		turret.turnRight();
		notifyObserver(TURRET);
	}

	@Override
	public double getAngle() {
		return collisionArea.getAngle();
	}

	@Override
	public synchronized void layMine() {

	}

	@Override
	public synchronized void shoot() {
		turret.shoot();
	}

}