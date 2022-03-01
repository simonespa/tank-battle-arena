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
import java.awt.Rectangle;

import tankBattle.utilities.ImageLoader;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public class LeaveGamePanel extends TankBattlePanel {

	private Image[] yesImages;
	private Image[] noImages;
	private Image yes;
	private Image no;
	private Image leaveQuestion;

	public static final int NO = 0;
	public static final int YES = 1;
	public final int numMenu = 2;

	public LeaveGamePanel(TankBattleFrame frame) {
		super(frame);
		currentMenu = NO;
		no();
	}

	@Override
	protected void loadImages() {
		ImageLoader loader = ImageLoader.getInstance();
		leaveQuestion = loader.getGUIFullScreenImage("leaveQuestion");
		yesImages = new Image[2];
		yesImages[0] = loader.getGUIFullScreenImage("yesNo");
		yesImages[1] = loader.getGUIFullScreenImage("yesSi");
		noImages = new Image[2];
		noImages[0] = loader.getGUIFullScreenImage("noNo");
		noImages[1] = loader.getGUIFullScreenImage("noSi");
	}

	public void select() {
		if (currentMenu == NO) {
			currentMenu = YES;
			yes();
		} else if (currentMenu == YES) {
			currentMenu = NO;
			no();
		}
	}

	private void yes() {
		yes = yesImages[1];
		no = noImages[0];
	}

	private void no() {
		yes = yesImages[0];
		no = noImages[1];
	}

	private void drawFrame(Graphics2D g) {
		Rectangle rect = new Rectangle();
		// rect.setRect(x, y, width, height);
		g.setColor(Color.ORANGE);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		drawFrame(g);
		g.drawImage(leaveQuestion, getX(leaveQuestion),
				getY(leaveQuestion, -60), null);
		g.drawImage(yes, getX(yes, -60), getY(yes, +60), null);
		g.drawImage(no, getX(no, +60), getY(no, +60), null);
	}

}
