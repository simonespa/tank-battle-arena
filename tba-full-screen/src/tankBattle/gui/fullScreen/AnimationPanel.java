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

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import tankBattle.utilities.ImageLoader;

/**
 * 
 * @author Simone Spaccarotella
 * 
 *         E' il primo pannello che viene visualizzato quando viene fatta
 *         partire la finestra principale. Rappresenta lo splash screen del
 *         gioco. Questo pannello è sostanzialmente un Thread, il quale
 *         visualizza l'animazione a schermo.
 * 
 */
public final class AnimationPanel extends TankBattlePanel implements Runnable {

	/* sono i riferimenti alle immagini visualizzate */
	private Image tankBattleArena;
	private Image theWar;
	private Image tank1;
	private Image tank2;
	/*
	 * E' il valore del canale alpha. Questo valore rappresenta la percentuale
	 * di trasparenza dell'immagine.
	 */
	private float alpha;

	/**
	 * Crea un pannello animato.
	 * 
	 * @param frame
	 *            il riferimento alla finestra principale
	 */
	public AnimationPanel(TankBattleFrame frame) {
		super(frame);
		alpha = 0.0f;
	}

	@Override
	protected void loadImages() {
		tankBattleArena = ImageLoader.getInstance().getGUIFullScreenImage(
				"tankBattleArena");
		theWar = ImageLoader.getInstance().getGUIFullScreenImage("theWar");
		tank1 = ImageLoader.getInstance().getGUIFullScreenImage("tank1");
		tank2 = ImageLoader.getInstance().getGUIFullScreenImage("tank2");
	}

	@Override
	public void run() {
		// 
		for (int i = 0; i < 9; i++) {
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// ridisegna il pannello
			repaint();
			// incrementa il valore percentuale del canale alpha
			alpha += 0.1;
		}
		// visualizza l'immagine finale per un secondo
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// richiama il pannello principale
		frame.mainPanel();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		// imposta il canale alpha
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				alpha));
		g.drawImage(tankBattleArena, getX(tankBattleArena), getY(
				tankBattleArena, -200), null);
		g.drawImage(tank1, getX(tank1, -100), getY(tank1), null);
		g.drawImage(tank2, getX(tank2, +100), getY(tank2), null);
		g.drawImage(theWar, getX(theWar), getY(theWar, +200), null);
	}

}
