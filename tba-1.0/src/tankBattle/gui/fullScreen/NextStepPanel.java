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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.JTextField;

import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * 
 * @author Simone Spaccarotella
 * 
 */
public class NextStepPanel extends TankBattlePanel {

	private JTextField nameArea;
	private Font font;

	private Image[] startImages;
	private Image[] backImages;
	private Image start;
	private Image back;

	public static final int NAME = 0;
	public static final int START = 1;
	public static final int BACK = 2;
	private final int numMenu = 3;

	private TankBattleFrame frame;

	public NextStepPanel(TankBattleFrame frame) {
		super(frame);
		this.frame = frame;
		setTextField();
		currentMenu = NAME;
		name();
	}

	public void setTextFieldEnabled(boolean flag) {
		nameArea.setEnabled(flag);
	}

	public void requestTextFieldFocus() {
		nameArea.requestFocus();
	}

	public String getTankName() {
		return nameArea.getText();
	}

	private void setTextField() {
		font = new Font(Font.SANS_SERIF, Font.BOLD, 70);
		nameArea = new JTextField(TankBattleGame.getTankName());
		nameArea.setActionCommand("JTextField:nameArea");
		nameArea.addActionListener(frame.getController());
		nameArea.setEnabled(false);
		nameArea.setFont(font);
		nameArea.setBackground(Color.ORANGE);
		nameArea.setDisabledTextColor(Color.GRAY);
		nameArea.setHorizontalAlignment(JTextField.CENTER);
		nameArea.setBounds(getX(null, -250), getY(null, -160), 500, 100);
		add(nameArea);
	}

	@Override
	protected void loadImages() {
		ImageLoader loader = ImageLoader.getInstance();
		startImages = new Image[2];
		startImages[0] = loader.getGUIFullScreenImage("startNo");
		startImages[1] = loader.getGUIFullScreenImage("startSi");
		backImages = new Image[2];
		backImages[0] = loader.getGUIFullScreenImage("backNo");
		backImages[1] = loader.getGUIFullScreenImage("backSi");
	}

	@Override
	public void selectUp() {
		currentMenu--;
		if (currentMenu < 0) {
			currentMenu = BACK;
			back();
		} else if (currentMenu == START) {
			start();
		} else if (currentMenu == NAME) {
			name();
		}
	}

	@Override
	public void selectDown() {
		currentMenu = (currentMenu + 1) % numMenu;
		if (currentMenu == NAME) {
			name();
		} else if (currentMenu == START) {
			start();
		} else if (currentMenu == BACK) {
			back();
		}
	}

	private void name() {
		start = startImages[0];
		back = backImages[0];
	}

	private void start() {
		start = startImages[1];
		back = backImages[0];
	}

	private void back() {
		start = startImages[0];
		back = backImages[1];
	}

	private void setFrameColor(Graphics2D g) {
		if (currentMenu == NAME) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.GRAY);
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		setFrameColor(g);
		// dichiara un forma rettangolare
		Rectangle2D rectangle = new Rectangle2D.Double();
		// il frame piï¿½ esterno.
		rectangle.setRect(getX(null, -257), getY(null, -167), 514, 114);
		g.draw(rectangle);
		rectangle.setRect(getX(null, -255), getY(null, -165), 510, 110);
		g.draw(rectangle);
		g.drawImage(start, getX(start), getY(start), null);
		g.drawImage(back, getX(back), getY(back, +90), null);
	}

}
