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

package tankBattle.gui.windowed;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tankBattle.utilities.ImageLoader;

/**
 * Rappresenta il tab che visualizza i credit di gioco. Nella fattispecie,
 * questo tab visualizza un'animazione, con la scritta dell'autore del gioco.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class TabCredits extends JPanel implements Runnable {

	/* riferimenti alle immagini visualizzate nel pannello */
	private Image tank1;
	private Image tank2;
	private Image[] explosionImages;
	private Image explosionImage;
	private Image writtenBy;
	private Image simoneSpaccarotella;

	/** il titolo del tab */
	private String title;
	/** l'icona del tab */
	private Icon icon;

	private boolean run;
	private boolean move;
	private boolean explosion;
	private boolean poweredBy;

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private int refresh;
	private float opacity;

	/**
	 * Crea un tab che visualizza i credit di gioco.
	 * 
	 * @param title
	 *            il titolo del tab.
	 */
	public TabCredits(String title) {
		super(true);
		// setta il titolo
		this.title = title;
		// imposta a true il flag del thread
		run = true;
		// setta i flag che dicono al pannello cosa disegnare
		move = true;
		explosion = false;
		poweredBy = false;
		// imposta il valore di refresh
		refresh = 20;
		// carica le immagini
		explosionImages = ImageLoader.getInstance().getExplosion2();
		tank1 = ImageLoader.getInstance().getGUIFullScreenImage("tank1");
		tank2 = ImageLoader.getInstance().getGUIFullScreenImage("tank2");
		writtenBy = new ImageIcon("resources/images/guiWindowed/writtenBy.png")
				.getImage();
		simoneSpaccarotella = new ImageIcon(
				"resources/images/guiWindowed/simoneSpaccarotella.png")
				.getImage();
		// carica l'icona del tab
		icon = new ImageIcon("resources/images/guiWindowed/icon.png");
		// setta il background
		setBackground(Color.BLACK);
	}

	@Override
	public void run() {
		while (run) {
			move();
			explosion();
			poweredBy();
		}
	}

	/**
	 * fa scontrare i tank
	 */
	private void move() {
		move = true;
		x1 = -200;
		y1 = 200;
		x2 = 600;
		y2 = 200;
		while (run) {
			repaint();
			try {
				Thread.sleep(refresh);
			} catch (InterruptedException e) {
			}
			x1 += 5;
			x2 -= 5;
			if (x1 == 160) {
				run = false;
			}
		}
		run = true;
		move = false;
	}

	/**
	 * fa esplodere i tank
	 */
	private void explosion() {
		explosion = true;
		for (int i = 0; i < explosionImages.length; i++) {
			explosionImage = explosionImages[i];
			repaint();
			try {
				Thread.sleep(refresh);
			} catch (InterruptedException e) {
			}
		}
		explosion = false;
	}

	/**
	 * visualizza il nome del programmatore di questo gioco.
	 */
	private void poweredBy() {
		poweredBy = true;
		for (float i = 0; i <= 10; i++) {
			opacity = i / 10;
			repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		for (float i = 10; i >= 0; i--) {
			opacity = i / 10;
			repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		poweredBy = false;
	}

	/**
	 * Stoppa il thread
	 */
	public void stop() {
		run = false;
	}

	/**
	 * Restituisce il titolo.
	 * 
	 * @return il titolo del tab.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Restituisce l'icona del tab.
	 * 
	 * @return l'icona del tab.
	 */
	public Icon getIcon() {
		return icon;
	}

	@Override
	protected void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		Graphics2D g = (Graphics2D) graphic;
		if (move) {
			g.drawImage(tank1, x1, y1, 100, 100, null);
			g.drawImage(tank2, x2, y2, 100, 100, null);
		} else if (explosion) {
			g.drawImage(explosionImage, 150, 150, 180, 180, null);
		} else if (poweredBy) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					opacity));
			g.drawImage(simoneSpaccarotella, 130, 180, 300, 100, null);
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				opacity));
		g.drawImage(writtenBy, 110, 130, 200, 50, null);
	}

}