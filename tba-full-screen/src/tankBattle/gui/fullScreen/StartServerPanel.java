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

package tankBattle.gui.fullScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * 
 * @author Simone Spaccarotella
 * 
 *         E' il pannello che visualizza le scelte possibili che si possono
 *         effettuare in modalità server. Da questo pannello è possibile:
 *         scegliere la modalità di gioco, far partire il server, tornare al
 *         pannello precedente.
 * 
 */
public final class StartServerPanel extends TankBattlePanel {

	private int[] currentSubMenu;
	private int indexSubMenu;
	private boolean subMenuSelected;

	/* sono i riferimenti alle immagini da visualizzare */
	private Image[] gameImages;
	private Image game;
	private Image[] nextStepImages;
	private Image nextStep;
	private Image[] backImages;
	private Image back;
	private Image[] typeImages;
	private Image[] teamImages;
	private Image[] freeForAllImages;
	private Image[] modeImages;
	private Image[] deathMatchImages;
	private Image[] scoreMatchImages;
	private Image[] mapImages;
	private Image[] desertImages;
	private Image[] glacierImages;
	private Image[] grasslandImages;
	private Image[] metalArenaImages;
	private Image type;
	private Image team;
	private Image freeForAll;
	private Image mode;
	private Image deathMatch;
	private Image scoreMatch;
	private Image map;
	private Image desert;
	private Image glacier;
	private Image grassland;
	private Image metalArena;

	/* sono gli identificativi dei menù */
	public static final int GAME = 0;
	public static final int NEXT_STEP = 1;
	public static final int BACK = 2;
	public static final int TYPE = 0;
	public static final int MODE = 1;
	public static final int MAP = 2;
	public static final int TEAM = 0;
	public static final int FREE_FOR_ALL = 1;
	public static final int DEATH_MATCH = 0;
	public static final int SCORE_MATCH = 1;
	public static final int DESERT = 0;
	public static final int GLACIER = 1;
	public static final int GRASSLAND = 2;
	public static final int METAL_ARENA = 3;

	/**
	 * Crea il pannello {@code StartServerPanel} e setta le impostazioni di
	 * default
	 * 
	 * @param frame
	 *            è il riferimento alla finestra principale
	 */
	public StartServerPanel(TankBattleFrame frame) {
		super(frame);
		currentMenu = GAME;
		currentSubMenu = new int[3];
		currentSubMenu[0] = TEAM;
		currentSubMenu[1] = DEATH_MATCH;
		currentSubMenu[2] = DESERT;
		indexSubMenu = 0;
		subMenuSelected = false;
		initMenu();
	}

	@Override
	protected void loadImages() {
		gameImages = new Image[2];
		gameImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"gameNo");
		gameImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"gameSi");
		nextStepImages = new Image[2];
		nextStepImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"nextStepNo");
		nextStepImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"nextStepSi");
		backImages = new Image[2];
		backImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"backNo");
		backImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"backSi");

		typeImages = new Image[2];
		typeImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"typeNo");
		typeImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"typeSi");
		teamImages = new Image[2];
		teamImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"teamNo");
		teamImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"teamSi");
		freeForAllImages = new Image[2];
		freeForAllImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"freeForAllNo");
		freeForAllImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"freeForAllSi");

		modeImages = new Image[2];
		modeImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"modeNo");
		modeImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"modeSi");
		deathMatchImages = new Image[2];
		deathMatchImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"deathMatchNo");
		deathMatchImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"deathMatchSi");
		scoreMatchImages = new Image[2];
		scoreMatchImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"scoreMatchNo");
		scoreMatchImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"scoreMatchSi");

		mapImages = new Image[2];
		mapImages[0] = ImageLoader.getInstance().getGUIFullScreenImage("mapNo");
		mapImages[1] = ImageLoader.getInstance().getGUIFullScreenImage("mapSi");
		desertImages = new Image[2];
		desertImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"desertNo");
		desertImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"desertSi");
		glacierImages = new Image[2];
		glacierImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"glacierNo");
		glacierImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"glacierSi");
		grasslandImages = new Image[2];
		grasslandImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"grasslandNo");
		grasslandImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"grasslandSi");
		metalArenaImages = new Image[2];
		metalArenaImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"metalArenaNo");
		metalArenaImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"metalArenaSi");
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		drawFrame(g);
		drawMenu(g);
	}

	/**
	 * Setta il colore del frame corrispondente al menù "game". Arancione se il
	 * corrispondente game è selezionato, grigio altrimenti.
	 */
	private void setColorGameFrame(Graphics2D g) {
		if (currentMenu == GAME) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.GRAY);
		}
	}

	/**
	 * Setta il colore dei frame dei sotto menù. Arancione se il corrispondente
	 * game è selezionato, grigio altrimenti.
	 */
	private void setColorMenuFrame(Graphics2D g, int menu) {
		if (subMenuSelected && indexSubMenu == menu) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.GRAY);
		}
	}

	/**
	 * Disegna i frame dei menù.
	 */
	private void drawFrame(Graphics2D g) {
		// dichiara un forma rettangolare
		Rectangle2D rectangle = new Rectangle2D.Double();
		// il frame più esterno.
		setColorGameFrame(g);
		rectangle.setRect(getX(game, -380), getY(null, -270), 910, 340);
		g.draw(rectangle);
		rectangle.setRect(getX(game, -378), getY(null, -268), 906, 336);
		g.draw(rectangle);

		int width1 = 208;
		int height1 = 220;
		int width2 = 204;
		int height2 = 216;
		// il frame di "type"
		setColorMenuFrame(g, TYPE);
		rectangle.setRect(getX(type, -355), getY(null, -182), width1, height1);
		g.draw(rectangle);
		rectangle.setRect(getX(type, -353), getY(null, -180), width2, height2);
		g.draw(rectangle);
		// il frame di "mode"
		setColorMenuFrame(g, MODE);
		rectangle.setRect(getX(mode, -50), getY(null, -182), width1, height1);
		g.draw(rectangle);
		rectangle.setRect(getX(mode, -48), getY(null, -180), width2, height2);
		g.draw(rectangle);
		// il frame di "map"
		setColorMenuFrame(g, MAP);
		rectangle.setRect(getX(map, 237), getY(null, -182), width1, height1);
		g.draw(rectangle);
		rectangle.setRect(getX(map, 239), getY(null, -180), width2, height2);
		g.draw(rectangle);
	}

	/**
	 * Disegna i menù sul pannello.
	 */
	private void drawMenu(Graphics2D g) {
		// disegna il menù "game"
		g.drawImage(game, getX(game), getY(game, -270), null);

		int offset = -300;
		// disegna il sotto menù "type"
		g.drawImage(type, getX(type, offset), getY(type, -180), null);
		g.drawImage(team, getX(team, offset), getY(team, -120), null);
		g.drawImage(freeForAll, getX(freeForAll, offset),
				getY(freeForAll, -80), null);
		// disegna il sotto menù "mode"
		g.drawImage(mode, getX(mode), getY(mode, -180), null);
		g.drawImage(deathMatch, getX(deathMatch), getY(deathMatch, -120), null);
		g.drawImage(scoreMatch, getX(scoreMatch), getY(scoreMatch, -80), null);
		// disegna il sotto menù "map"
		offset = 300;
		g.drawImage(map, getX(map, offset), getY(map, -180), null);
		g.drawImage(desert, getX(desert, offset), getY(desert, -120), null);
		g.drawImage(glacier, getX(glacier, offset), getY(glacier, -80), null);
		g.drawImage(grassland, getX(grassland, offset), getY(grassland, -40),
				null);
		g.drawImage(metalArena, getX(metalArena, offset), getY(metalArena),
				null);
		// disegna i menù "start" e "back"
		g.drawImage(nextStep, getX(nextStep), getY(nextStep, +130), null);
		g.drawImage(back, getX(back), getY(back, +220), null);
	}

	@Override
	public void selectUp() {
		// se i sotto menù sono selezionati
		if (subMenuSelected) {
			// scorri in alto i sotto menù
			currentSubMenu[indexSubMenu]--;
			// questo accorgimento permette un decremento modulare
			// e quindi uno scorrimento circolaredei menù.
			if (currentSubMenu[indexSubMenu] < 0) {
				if (indexSubMenu == MAP) {
					currentSubMenu[indexSubMenu] = 3;
				} else {
					currentSubMenu[indexSubMenu] = 1;
				}
			}
			game();
		} else {
			// altrimenti decrementa i menù
			currentMenu--;
			// questo accorgimento permette un decremento modulare
			// e quindi uno scorrimento circolaredei menù.
			if (currentMenu < 0) {
				currentMenu = BACK;
				back();
			} else if (currentMenu == NEXT_STEP) {
				start();
			} else if (currentMenu == GAME) {
				game();
			}
		}
	}

	@Override
	public void selectDown() {
		// se i sotto menù sono selezionati
		if (subMenuSelected) {
			// scorri in alto i sotto menù
			if (indexSubMenu == MAP) {
				currentSubMenu[indexSubMenu] = (currentSubMenu[indexSubMenu] + 1) % 4;
			} else {
				currentSubMenu[indexSubMenu] = (currentSubMenu[indexSubMenu] + 1) % 2;
			}
			game();
		} else {
			// scorri in alto i menù
			currentMenu = (currentMenu + 1) % 3;
			if (currentMenu == GAME) {
				game();
			} else if (currentMenu == NEXT_STEP) {
				start();
			} else if (currentMenu == BACK) {
				back();
			}
		}
	}

	/**
	 * Questo metodo viene richiamato solo quando ci troviamo nei sotto menù.
	 * Serve a selezionare il sotto menù più a sinistra.
	 */
	public void selectLeft() {
		indexSubMenu--;
		if (indexSubMenu < 0) {
			indexSubMenu = MAP;
			map();
		} else if (indexSubMenu == MODE) {
			mode();
		} else if (indexSubMenu == TYPE) {
			type();
		}
	}

	/**
	 * Questo metodo viene richiamato solo quando ci troviamo nei sotto menù.
	 * Serve a selezionare il sotto menù più a destra.
	 */
	public void selectRight() {
		indexSubMenu = (indexSubMenu + 1) % 3;
		if (indexSubMenu == TYPE) {
			type();
		} else if (indexSubMenu == MODE) {
			mode();
		} else if (indexSubMenu == MAP) {
			map();
		}
	}

	/**
	 * Imposta il flag booleano che indica se i sotto menù sono selezionati o
	 * meno.
	 */
	public void selectDeselectSubMenu() {
		if (subMenuSelected) {
			subMenuSelected = false;
		} else {
			subMenuSelected = true;
		}
	}

	public void storeTypeModeMap() {
		// sub menù type
		switch (currentSubMenu[TYPE]) {
		case TEAM:
			TankBattleGame.setType("Team");
			break;
		case FREE_FOR_ALL:
			TankBattleGame.setType("Free For All");
			break;
		}
		// sub menù mode
		switch (currentSubMenu[MODE]) {
		case DEATH_MATCH:
			TankBattleGame.setMode("Death Match");
			break;
		case SCORE_MATCH:
			TankBattleGame.setMode("Score Match");
			break;
		}
		// sub menù map
		switch (currentSubMenu[MAP]) {
		case DESERT:
			TankBattleGame.setMap("Desert");
			break;
		case GLACIER:
			TankBattleGame.setMap("Glacier");
			break;
		case GRASSLAND:
			TankBattleGame.setMap("Grassland");
			break;
		case METAL_ARENA:
			TankBattleGame.setMap("Metal Arena");
			break;
		}
	}

	/**
	 * Ci dice se il menù "game" è stato selezionato
	 * 
	 * @return true se il menù "game" è selezionato
	 */
	public boolean isSelectedSubMenu() {
		return subMenuSelected;
	}

	/**
	 * Restituisce il sotto meù corrente.
	 * 
	 * @return il sotto menù corrente
	 */
	public int getCurrentSubMenu() {
		return indexSubMenu;
	}

	/**
	 * Inizializza i menù
	 */
	private void initMenu() {
		game();
		team();
		deathMatch();
		desert();
	}

	/**
	 * Seleziona il menù "game"
	 */
	public void game() {
		game = gameImages[1];
		if (subMenuSelected) {
			if (indexSubMenu == TYPE) {
				type();
			} else if (indexSubMenu == MODE) {
				mode();
			} else if (indexSubMenu == MAP) {
				map();
			}
		} else {
			resetType();
			resetMode();
			resetMap();
		}
		nextStep = nextStepImages[0];
		back = backImages[0];
	}

	/**
	 * Deseleziona il menù "resetGame"
	 */
	private void resetGame() {
		game = gameImages[0];
		type = typeImages[0];
		mode = modeImages[0];
		map = mapImages[0];
	}

	/**
	 * Seleziona il menù "start"
	 */
	private void start() {
		resetGame();
		nextStep = nextStepImages[1];
		back = backImages[0];
	}

	/**
	 * Seleziona il menù "back"
	 */
	private void back() {
		resetGame();
		nextStep = nextStepImages[0];
		back = backImages[1];
	}

	/**
	 * Seleziona il sotto menù "type"
	 */
	private void type() {
		resetMode();
		resetMap();
		type = typeImages[1];
		if (currentSubMenu[indexSubMenu] == TEAM) {
			team();
		} else if (currentSubMenu[indexSubMenu] == FREE_FOR_ALL) {
			freeForAll();
		}
	}

	/**
	 * Deseleziona il sotto menù "type"
	 */
	private void resetType() {
		type = typeImages[0];
	}

	/**
	 * Seleziona il sotto menù "mode"
	 */
	private void mode() {
		resetType();
		resetMap();
		mode = modeImages[1];
		if (currentSubMenu[indexSubMenu] == DEATH_MATCH) {
			deathMatch();
		} else if (currentSubMenu[indexSubMenu] == SCORE_MATCH) {
			scoreMatch();
		}
	}

	/**
	 * Deseleziona il sotto menù "mode"
	 */
	private void resetMode() {
		mode = modeImages[0];
	}

	/**
	 * Seleziona il sotto menù "map"
	 */
	private void map() {
		resetType();
		resetMode();
		map = mapImages[1];
		if (currentSubMenu[indexSubMenu] == DESERT) {
			desert();
		} else if (currentSubMenu[indexSubMenu] == GLACIER) {
			glacier();
		} else if (currentSubMenu[indexSubMenu] == GRASSLAND) {
			grassland();
		} else if (currentSubMenu[indexSubMenu] == METAL_ARENA) {
			metalArena();
		}
	}

	/**
	 * Deseleziona il sotto menù "map"
	 */
	private void resetMap() {
		map = mapImages[0];
	}

	/* tutti i seguenti metodì servono a selezionare le voci dei sotto menù */
	private void team() {
		team = teamImages[1];
		freeForAll = freeForAllImages[0];
	}

	private void freeForAll() {
		team = teamImages[0];
		freeForAll = freeForAllImages[1];
	}

	private void deathMatch() {
		deathMatch = deathMatchImages[1];
		scoreMatch = scoreMatchImages[0];
	}

	private void scoreMatch() {
		deathMatch = deathMatchImages[0];
		scoreMatch = scoreMatchImages[1];
	}

	private void desert() {
		desert = desertImages[1];
		glacier = glacierImages[0];
		grassland = grasslandImages[0];
		metalArena = metalArenaImages[0];
	}

	private void glacier() {
		desert = desertImages[0];
		glacier = glacierImages[1];
		grassland = grasslandImages[0];
		metalArena = metalArenaImages[0];
	}

	private void grassland() {
		desert = desertImages[0];
		glacier = glacierImages[0];
		grassland = grasslandImages[1];
		metalArena = metalArenaImages[0];
	}

	private void metalArena() {
		desert = desertImages[0];
		glacier = glacierImages[0];
		grassland = grasslandImages[0];
		metalArena = metalArenaImages[1];
	}

}
