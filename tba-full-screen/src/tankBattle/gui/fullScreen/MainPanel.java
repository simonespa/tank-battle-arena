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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import tankBattle.utilities.ImageLoader;

/**
 * E' il pannello principale. Da qui è possibile far partire il serve,
 * il client, andare alle opzioni di gioco, oppure uscire dal gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class MainPanel extends TankBattlePanel {

	private Image[] startServerImages;
	private Image[] joinServerImages;
	private Image[] optionImages;
	private Image[] exitImages;
	private Image startServer;
	private Image joinServer;
	private Image option;
	private Image exit;

	public static final int START_SERVER = 0;
	public static final int JOIN_SERVER = 1;
	public static final int OPTION = 2;
	public static final int EXIT = 3;
	private final int numMenu = 4;

	/**
	 * Crea il pannello principale.
	 * 
	 * @param frame
	 *            il riferimento alla finestra principale
	 */
	public MainPanel(TankBattleFrame frame) {
		super(frame);
		currentMenu = START_SERVER;
		startServer();
	}

	@Override
	protected void loadImages() {
		startServerImages = new Image[2];
		startServerImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"startServerNo");
		startServerImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"startServerSi");
		joinServerImages = new Image[2];
		joinServerImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"joinServerNo");
		joinServerImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"joinServerSi");
		optionImages = new Image[2];
		optionImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"optionNo");
		optionImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"optionSi");
		exitImages = new Image[2];
		exitImages[0] = ImageLoader.getInstance().getGUIFullScreenImage(
				"exitNo");
		exitImages[1] = ImageLoader.getInstance().getGUIFullScreenImage(
				"exitSi");
	}

	@Override
	public void selectUp() {
		currentMenu--;
		if (currentMenu < 0) {
			currentMenu = EXIT;
			exit();
		} else if (currentMenu == OPTION) {
			option();
		} else if (currentMenu == JOIN_SERVER) {
			joinServer();
		} else if (currentMenu == START_SERVER) {
			startServer();
		}
	}

	@Override
	public void selectDown() {
		currentMenu = (currentMenu + 1) % numMenu;
		if (currentMenu == START_SERVER) {
			startServer();
		} else if (currentMenu == JOIN_SERVER) {
			joinServer();
		} else if (currentMenu == OPTION) {
			option();
		} else if (currentMenu == EXIT) {
			exit();
		}
	}

	/**
	 * Evidenzia il menù "start server".
	 */
	private void startServer() {
		startServer = startServerImages[1];
		joinServer = joinServerImages[0];
		option = optionImages[0];
		exit = exitImages[0];
	}

	/**
	 * Evidenzia il menù "join server".
	 */
	private void joinServer() {
		startServer = startServerImages[0];
		joinServer = joinServerImages[1];
		option = optionImages[0];
		exit = exitImages[0];
	}

	/**
	 * Evidenzia il menù "option".
	 */
	private void option() {
		startServer = startServerImages[0];
		joinServer = joinServerImages[0];
		option = optionImages[1];
		exit = exitImages[0];
	}

	/**
	 * Evidenzia il menù "exit".
	 */
	private void exit() {
		startServer = startServerImages[0];
		joinServer = joinServerImages[0];
		option = optionImages[0];
		exit = exitImages[1];
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.drawImage(startServer, getX(startServer), getY(startServer, -135),
				null);
		g.drawImage(joinServer, getX(joinServer), getY(joinServer, -45), null);
		g.drawImage(option, getX(option), getY(option, +45), null);
		g.drawImage(exit, getX(exit), getY(exit, +135), null);
	}

}