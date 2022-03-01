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

package tankBattle.utilities;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * E' una classe di utilità, che serve a caricare le immagini e i suoni del
 * gioco in modo trasparente all'utente.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class ImageLoader implements LocalImageLoadable {

	/** il riferimento all'istanza di questa classe */
	private static ImageLoader imageLoader;
	/** flag booleano che sta ad indicare se le immagini sono già state caricate */
	private static boolean imagesLoaded = false;;
	/** è il path delle immagini */
	private String path;

	// ////////////////////////// RIFERIMENTI ALLE IMMAGINI DEL GIOCO
	// ////////////////////////////

	/* ---------- TANK ---------- */
	/** il corpo del tank */
	private static Image tankBody;
	/** la torretta */
	private static Image tankTurret;
	/* -------------------------- */

	/* --------- SCENE --------- */
	/** il metallo */
	private static Image[] metal;
	/** il ghiaccio */
	private static Image[] ice;
	/** il prato */
	private static Image[] grass;
	/** la sabbia */
	private static Image[] sand;
	/** i detriti lasciati dall'esplosione del tank */
	private static Image explodeDebris;
	/** il muretto di mattoni */
	private static Image[] brickWall;
	/* -------------------------- */

	/* -------- EXPLOSION ------- */
	/** l'esplosione che si verifica quando il proiettile colpisce un oggetto */
	private static Image[] explosion1;
	/** l'esplosione del tank */
	private static Image[] explosion2;
	/* -------------------------- */

	/* ------ GUI WINDOWED ------ */
	private static Image icon;
	private static Image background;
	private static Image banner;
	private static Icon newGameMenu;
	private static Icon startServerMenu;
	private static Icon joinServerMenu;
	private static Icon exitMenu;
	private static Icon controllerMenu;
	private static Icon toFullScreenMenu;
	private static Icon[] lookAndFeelMenu;
	private static Icon checkedMenu;
	private static Icon[] musicMenu;
	private static Icon[] soundEffectMenu;
	private static Icon aboutMenu;
	/* ---------------------------- */

	/* ------ GUI FULL SCREEN ------ */
	private static Image tankBattleArena;
	private static Image theWar;
	private static Image tank1;
	private static Image tank2;
	private static Image[] joinServer;
	private static Image[] startServer;
	private static Image[] option;
	private static Image[] exit;
	private static Image[] toWindowed;
	private static Image[] musicOn;
	private static Image[] musicOff;
	private static Image[] soundEffectOn;
	private static Image[] soundEffectOff;
	private static Image[] returnToGame;
	private static Image[] leaveGame;
	private static Image leaveQuestion;
	private static Image[] yes;
	private static Image[] no;
	private static Image[] game;
	private static Image[] start;
	private static Image[] nextStep;
	private static Image[] back;
	private static Image[] type;
	private static Image[] mode;
	private static Image[] map;
	private static Image[] team;
	private static Image[] freeForAll;
	private static Image[] deathMatch;
	private static Image[] scoreMatch;
	private static Image[] desert;
	private static Image[] glacier;
	private static Image[] grassland;
	private static Image[] metalArena;

	/* ------------------------------ */

	// ///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Costruttore privato per evitare che questa classe venga istanziata più
	 * volte dall'esterno. Questa soluzione implementa il pattern "Singleton".
	 */
	private ImageLoader() {
	}

	/**
	 * Restituisce l'unica istanza di questa classe che è possibile istanziare.
	 * Successive invocazioni di questo metodo, restituiranno sempre un
	 * riferimento allo stesso oggetto.
	 * 
	 * @return l'istanza del {@code ResourceLoader}
	 */
	public static ImageLoader getInstance() {
		if (imageLoader == null) {
			imageLoader = new ImageLoader();
		}
		return imageLoader;
	}

	/**
	 * Carica le immagini del gioco
	 */
	public void load() {
		if (!imagesLoaded) {
			loadGUIWindowedImages();
			loadGUIFullScreenImages();
			loadTank();
			loadMetal();
			loadIce();
			loadGrass();
			loadSand();
			loadBrickWall();
			loadExplodeDebris();
			loadExplosions();
			imagesLoaded = true;
		}
	}

	/**
	 * Carica le immagini del tank
	 */
	private void loadTank() {
		tankBody = new ImageIcon(imagesPath + "tank/tankBody.png").getImage();
		tankTurret = new ImageIcon(imagesPath + "tank/tankTurret.png")
				.getImage();
	}

	/**
	 * Carica le immagini del pavimento di metallo
	 */
	private void loadMetal() {
		metal = new Image[5];
		for (int i = 0; i < 5; i++) {
			metal[i] = new ImageIcon(imagesPath + "ground/metal-" + i + ".png")
					.getImage();
		}
	}

	/**
	 * Carica le immagini del fondo ghiacciato
	 */
	private void loadIce() {
		ice = new Image[1];
		ice[0] = new ImageIcon(imagesPath + "ground/ice.png").getImage();
	}

	/**
	 * Carica le immagini del prato
	 */
	private void loadGrass() {
		grass = new Image[1];
		grass[0] = new ImageIcon(imagesPath + "ground/grass.png").getImage();
	}

	private void loadSand() {
		sand = new Image[1];
		sand[0] = new ImageIcon(imagesPath + "ground/sand.png").getImage();
	}

	/**
	 * Carica le immagini dei detriti
	 */
	private void loadExplodeDebris() {
		explodeDebris = new ImageIcon(imagesPath + "ground/explodeDebris.png")
				.getImage();
	}

	/**
	 * Carica le immagini delle esplosioni
	 */
	private void loadExplosions() {
		/*
		 * sono le immagini che vengono visualizzate quando un proiettile
		 * colpisce un tank
		 */
		explosion1 = new Image[17];
		for (int i = 0; i < 17; i++) {
			explosion1[i] = new ImageIcon(imagesPath + "explosion/explosion1-"
					+ (i + 1) + ".png").getImage();
		}
		/* sono le immagini che vengono visualizzate quando esplode un tank */
		explosion2 = new Image[71];
		for (int i = 0; i < 71; i++) {
			explosion2[i] = new ImageIcon(imagesPath + "explosion/explosion2-"
					+ (i + 1) + ".png").getImage();
		}
	}

	/**
	 * Carica le immagini del muro
	 */
	private void loadBrickWall() {
		brickWall = new Image[2];
		for (int i = 0; i < 2; i++) {
			brickWall[0] = new ImageIcon(imagesPath + "wall/brickWall-" + i
					+ ".png").getImage();
		}
	}

	/**
	 * 
	 */
	private void loadGUIWindowedImages() {
		String path = imagesPath + "guiWindowed/";
		setImagesPath("guiWindowed/");
		icon = load("icon.png");
		background = load("background.png");
		banner = load("banner.png");
		newGameMenu = new ImageIcon(path + "newGame.png");
		startServerMenu = new ImageIcon(path + "startServer.png");
		joinServerMenu = new ImageIcon(path + "joinServer.png");
		exitMenu = new ImageIcon(path + "exit.png");
		controllerMenu = new ImageIcon(path + "controller.png");
		toFullScreenMenu = new ImageIcon(path + "toFullScreen.png");
		lookAndFeelMenu = new Icon[3];
		lookAndFeelMenu[0] = new ImageIcon(path + "lookAndFeelBlack.png");
		lookAndFeelMenu[1] = new ImageIcon(path + "lookAndFeelBlue.png");
		lookAndFeelMenu[2] = new ImageIcon(path + "lookAndFeelWhite.png");
		checkedMenu = new ImageIcon(path + "checked.png");
		musicMenu = new Icon[2];
		musicMenu[0] = new ImageIcon(path + "musicOff.png");
		musicMenu[1] = new ImageIcon(path + "musicOn.png");
		soundEffectMenu = new Icon[2];
		soundEffectMenu[0] = new ImageIcon(path + "soundEffectOff.png");
		soundEffectMenu[1] = new ImageIcon(path + "soundEffectOn.png");
		aboutMenu = new ImageIcon(path + "about.png");
	}

	private void loadGUIFullScreenImages() {
		setImagesPath("guiFullScreen/");
		tankBattleArena = load("tankBattleArena.png");
		theWar = load("theWar.png");
		tank1 = load("tank1.png");
		tank2 = load("tank2.png");
		joinServer = new Image[2];
		joinServer[0] = load("joinServerNo.png");
		joinServer[1] = load("joinServerSi.png");
		startServer = new Image[2];
		startServer[0] = load("startServerNo.png");
		startServer[1] = load("startServerSi.png");
		option = new Image[2];
		option[0] = load("optionNo.png");
		option[1] = load("optionSi.png");
		exit = new Image[2];
		exit[0] = load("exitNo.png");
		exit[1] = load("exitSi.png");
		toWindowed = new Image[2];
		toWindowed[0] = load("toWindowedNo.png");
		toWindowed[1] = load("toWindowedSi.png");
		musicOn = new Image[2];
		musicOn[0] = load("musicOnNo.png");
		musicOn[1] = load("musicOnSi.png");
		musicOff = new Image[2];
		musicOff[0] = load("musicOffNo.png");
		musicOff[1] = load("musicOffSi.png");
		soundEffectOn = new Image[2];
		soundEffectOn[0] = load("soundEffectOnNo.png");
		soundEffectOn[1] = load("soundEffectOnSi.png");
		soundEffectOff = new Image[2];
		soundEffectOff[0] = load("soundEffectOffNo.png");
		soundEffectOff[1] = load("soundEffectOffSi.png");
		returnToGame = new Image[2];
		returnToGame[0] = load("returnToGameNo.png");
		returnToGame[1] = load("returnToGameSi.png");
		leaveGame = new Image[2];
		leaveGame[0] = load("leaveGameNo.png");
		leaveGame[1] = load("leaveGameSi.png");
		leaveQuestion = load("leaveQuestion.png");
		yes = new Image[2];
		yes[0] = load("yesNo.png");
		yes[1] = load("yesSi.png");
		no = new Image[2];
		no[0] = load("noNo.png");
		no[1] = load("noSi.png");
		game = new Image[2];
		game[0] = load("gameNo.png");
		game[1] = load("gameSi.png");
		start = new Image[2];
		start[0] = load("startNo.png");
		start[1] = load("startSi.png");
		nextStep = new Image[2];
		nextStep[0] = load("nextStepNo.png");
		nextStep[1] = load("nextStepSi.png");
		back = new Image[2];
		back[0] = load("backNo.png");
		back[1] = load("backSi.png");
		type = new Image[2];
		type[0] = load("typeNo.png");
		type[1] = load("typeSi.png");
		mode = new Image[2];
		mode[0] = load("modeNo.png");
		mode[1] = load("modeSi.png");
		map = new Image[2];
		map[0] = load("mapNo.png");
		map[1] = load("mapSi.png");
		team = new Image[2];
		team[0] = load("teamNo.png");
		team[1] = load("teamSi.png");
		freeForAll = new Image[2];
		freeForAll[0] = load("freeForAllNo.png");
		freeForAll[1] = load("freeForAllSi.png");
		deathMatch = new Image[2];
		deathMatch[0] = load("deathMatchNo.png");
		deathMatch[1] = load("deathMatchSi.png");
		scoreMatch = new Image[2];
		scoreMatch[0] = load("scoreMatchNo.png");
		scoreMatch[1] = load("scoreMatchSi.png");
		desert = new Image[2];
		desert[0] = load("desertNo.png");
		desert[1] = load("desertSi.png");
		glacier = new Image[2];
		glacier[0] = load("glacierNo.png");
		glacier[1] = load("glacierSi.png");
		grassland = new Image[2];
		grassland[0] = load("grasslandNo.png");
		grassland[1] = load("grasslandSi.png");
		metalArena = new Image[2];
		metalArena[0] = load("metalArenaNo.png");
		metalArena[1] = load("metalArenaSi.png");
	}

	private void setImagesPath(String directory) {
		path = imagesPath + directory;
	}

	private Image load(String fileName) {
		return new ImageIcon(path + fileName).getImage();
	}

	public Image getIcon() {
		while (!imagesLoaded)
			;
		return icon;
	}

	public Image getBackground() {
		while (!imagesLoaded)
			;
		return background;
	}
	
	public Image getBanner() {
		while (!imagesLoaded)
			;
		return banner;
	}

	public Image getTankBody(Color color) {
		while (!imagesLoaded)
			;
		tankBody = getNewColoredImage(tankBody, color);
		return tankBody;
	}

	public Image getTankBody(int red, int green, int blue) {
		while (!imagesLoaded)
			;
		tankBody = getNewColoredImage(tankBody, red, green, blue);
		return tankBody;
	}

	public Image getTankTurret(Color color) {
		while (!imagesLoaded)
			;
		tankTurret = getNewColoredImage(tankTurret, color);
		return tankTurret;
	}

	public Image getTankTurret(int red, int green, int blue) {
		while (!imagesLoaded)
			;
		tankTurret = getNewColoredImage(tankTurret, red, green, blue);
		return tankTurret;
	}

	public Image[] getMetalGround() {
		while (!imagesLoaded)
			;
		return metal;
	}

	public Image[] getIcyGround() {
		while (!imagesLoaded)
			;
		return ice;
	}

	public Image[] getGrassGround() {
		while (!imagesLoaded)
			;
		return grass;
	}

	public Image[] getSandGround() {
		while (!imagesLoaded)
			;
		return sand;
	}

	public Image[] getBrickWall() {
		while (!imagesLoaded)
			;
		return brickWall;
	}

	public Image getExplodeDebris() {
		while (!imagesLoaded)
			;
		return explodeDebris;
	}

	public Image[] getExplosion1() {
		while (!imagesLoaded)
			;
		return explosion1;
	}

	public Image[] getExplosion2() {
		while (!imagesLoaded)
			;
		return explosion2;
	}

	public Icon getGUIWindowedImage(String image) {
		while (!imagesLoaded)
			;
		if (image.equalsIgnoreCase("newGame")) {
			return newGameMenu;
		} else if (image.equalsIgnoreCase("startServer")) {
			return startServerMenu;
		} else if (image.equalsIgnoreCase("joinServer")) {
			return joinServerMenu;
		} else if (image.equalsIgnoreCase("exit")) {
			return exitMenu;
		} else if (image.equalsIgnoreCase("controller")) {
			return controllerMenu;
		} else if (image.equalsIgnoreCase("toFullScreen")) {
			return toFullScreenMenu;
		} else if (image.equalsIgnoreCase("lookAndFeelBlack")) {
			return lookAndFeelMenu[0];
		} else if (image.equalsIgnoreCase("lookAndFeelBlue")) {
			return lookAndFeelMenu[1];
		} else if (image.equalsIgnoreCase("lookAndFeelWhite")) {
			return lookAndFeelMenu[2];
		} else if (image.equalsIgnoreCase("checked")) {
			return checkedMenu;
		} else if (image.equalsIgnoreCase("musicOff")) {
			return musicMenu[0];
		} else if (image.equalsIgnoreCase("musicOn")) {
			return musicMenu[1];
		} else if (image.equalsIgnoreCase("soundEffectOff")) {
			return soundEffectMenu[0];
		} else if (image.equalsIgnoreCase("soundEffectOn")) {
			return soundEffectMenu[1];
		} else if (image.equalsIgnoreCase("about")) {
			return aboutMenu;
		} else {
			return null;
		}
	}

	public Image getGUIFullScreenImage(String image) {
		while (!imagesLoaded)
			;
		if (image.equalsIgnoreCase("tankBattleArena")) {
			return tankBattleArena;
		} else if (image.equalsIgnoreCase("theWar")) {
			return theWar;
		} else if (image.equalsIgnoreCase("tank1")) {
			return tank1;
		} else if (image.equalsIgnoreCase("tank2")) {
			return tank2;
		} else if (image.equalsIgnoreCase("joinServerNo")) {
			return joinServer[0];
		} else if (image.equalsIgnoreCase("joinServerSi")) {
			return joinServer[1];
		} else if (image.equalsIgnoreCase("startServerNo")) {
			return startServer[0];
		} else if (image.equalsIgnoreCase("startServerSi")) {
			return startServer[1];
		} else if (image.equalsIgnoreCase("optionNo")) {
			return option[0];
		} else if (image.equalsIgnoreCase("optionSi")) {
			return option[1];
		} else if (image.equalsIgnoreCase("exitNo")) {
			return exit[0];
		} else if (image.equalsIgnoreCase("exitSi")) {
			return exit[1];
		} else if (image.equalsIgnoreCase("toWindowedNo")) {
			return toWindowed[0];
		} else if (image.equalsIgnoreCase("toWindowedSi")) {
			return toWindowed[1];
		} else if (image.equalsIgnoreCase("musicOnNo")) {
			return musicOn[0];
		} else if (image.equalsIgnoreCase("musicOnSi")) {
			return musicOn[1];
		} else if (image.equalsIgnoreCase("musicOffNo")) {
			return musicOff[0];
		} else if (image.equalsIgnoreCase("musicOffSi")) {
			return musicOff[1];
		} else if (image.equalsIgnoreCase("soundEffectOnNo")) {
			return soundEffectOn[0];
		} else if (image.equalsIgnoreCase("soundEffectOnSi")) {
			return soundEffectOn[1];
		} else if (image.equalsIgnoreCase("soundEffectOffNo")) {
			return soundEffectOff[0];
		} else if (image.equalsIgnoreCase("soundEffectOffSi")) {
			return soundEffectOff[1];
		} else if (image.equalsIgnoreCase("returnToGameNo")) {
			return returnToGame[0];
		} else if (image.equalsIgnoreCase("returnToGameSi")) {
			return returnToGame[1];
		} else if (image.equalsIgnoreCase("leaveGameNo")) {
			return leaveGame[0];
		} else if (image.equalsIgnoreCase("leaveGameSi")) {
			return leaveGame[1];
		} else if (image.equalsIgnoreCase("leaveQuestion")) {
			return leaveQuestion;
		} else if (image.equalsIgnoreCase("yesNo")) {
			return yes[0];
		} else if (image.equalsIgnoreCase("yesSi")) {
			return yes[1];
		} else if (image.equalsIgnoreCase("noNo")) {
			return no[0];
		} else if (image.equalsIgnoreCase("noSi")) {
			return no[1];
		} else if (image.equalsIgnoreCase("gameNo")) {
			return game[0];
		} else if (image.equalsIgnoreCase("gameSi")) {
			return game[1];
		} else if (image.equalsIgnoreCase("startNo")) {
			return start[0];
		} else if (image.equalsIgnoreCase("startSi")) {
			return start[1];
		} else if (image.equalsIgnoreCase("nextStepNo")) {
			return nextStep[0];
		} else if (image.equalsIgnoreCase("nextStepSi")) {
			return nextStep[1];
		} else if (image.equalsIgnoreCase("backNo")) {
			return back[0];
		} else if (image.equalsIgnoreCase("backSi")) {
			return back[1];
		} else if (image.equalsIgnoreCase("typeNo")) {
			return type[0];
		} else if (image.equalsIgnoreCase("typeSi")) {
			return type[1];
		} else if (image.equalsIgnoreCase("modeNo")) {
			return mode[0];
		} else if (image.equalsIgnoreCase("modeSi")) {
			return mode[1];
		} else if (image.equalsIgnoreCase("mapNo")) {
			return map[0];
		} else if (image.equalsIgnoreCase("mapSi")) {
			return map[1];
		} else if (image.equalsIgnoreCase("teamNo")) {
			return team[0];
		} else if (image.equalsIgnoreCase("teamSi")) {
			return team[1];
		} else if (image.equalsIgnoreCase("freeForAllNo")) {
			return freeForAll[0];
		} else if (image.equalsIgnoreCase("freeForAllSi")) {
			return freeForAll[1];
		} else if (image.equalsIgnoreCase("deathMatchNo")) {
			return deathMatch[0];
		} else if (image.equalsIgnoreCase("deathMatchSi")) {
			return deathMatch[1];
		} else if (image.equalsIgnoreCase("scoreMatchNo")) {
			return scoreMatch[0];
		} else if (image.equalsIgnoreCase("scoreMatchSi")) {
			return scoreMatch[1];
		} else if (image.equalsIgnoreCase("desertNo")) {
			return desert[0];
		} else if (image.equalsIgnoreCase("desertSi")) {
			return desert[1];
		} else if (image.equalsIgnoreCase("glacierNo")) {
			return glacier[0];
		} else if (image.equalsIgnoreCase("glacierSi")) {
			return glacier[1];
		} else if (image.equalsIgnoreCase("grasslandNo")) {
			return grassland[0];
		} else if (image.equalsIgnoreCase("grasslandSi")) {
			return grassland[1];
		} else if (image.equalsIgnoreCase("metalArenaNo")) {
			return metalArena[0];
		} else if (image.equalsIgnoreCase("metalArenaSi")) {
			return metalArena[1];
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param images
	 * @param color
	 * @return
	 */
	private Image[] getNewColoredImages(Image[] images, Color color) {
		if (color == null || images == null) {
			return images;
		} else {
			Image[] coloredImage = new Image[images.length];
			for (int i = 0; i < coloredImage.length; i++) {
				coloredImage[i] = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(images[i].getSource(),
								new ColorFilter(color)));
			}
			return coloredImage;
		}
	}

	/**
	 * Restituisce l'immagine passata come parametro del colore passato
	 * anch'esso come parametro.
	 * 
	 * @param image
	 *            immagine da colorare
	 * @param color
	 *            il colore che avrà la nuova immagine
	 * @return restituisce una nuova immagine colorata di {@code color}
	 */
	private Image getNewColoredImage(Image image, Color color) {
		if (color == null || image == null) {
			return image;
		} else {
			return Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), new ColorFilter(
							color)));
		}
	}

	/**
	 * Restituisce l'immagine passata come parametro del colore che viene fuori
	 * dalla combinazione dei tre interi passati anch'essi come parametri.
	 * 
	 * @param image
	 *            immagine da colorare
	 * @param red
	 *            componente di rosso
	 * @param green
	 *            componente di verde
	 * @param blue
	 *            componente di blu
	 * @return restituisce una nuova immagine colorata
	 */
	private Image getNewColoredImage(Image image, int red, int green, int blue) {
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(image.getSource(), new ColorFilter(red,
						green, blue)));
	}

}