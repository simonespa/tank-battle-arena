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
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 
 * Questa classe astratta rappresenta un qualsiasi pannello che può essere
 * inserito nella finestra principale e visualizzato come menù.
 * 
 * @author Simone Spaccarotella
 * 
 */
public abstract class TankBattlePanel extends JPanel {

	/* indica il menù corrente */
	protected int currentMenu;
	/* è il riferimento alla finestra principale */
	protected TankBattleFrame frame;

	/**
	 * In questo costruttore vengono effettuate delle operazioni di setup.
	 * Implementando questa classe e richiamando il costruttore, queste
	 * operazioni verranno eseguite di default.
	 * 
	 * @param frame
	 *            il riferimento alla finestra principale
	 */
	public TankBattlePanel(TankBattleFrame frame) {
		super(null, true);
		this.frame = frame;
		setBackground(Color.BLACK);
		loadImages();
	}

	/**
	 * Restituisce l'identificativo del menù corrente.
	 * 
	 * @return è un intero che rappresenta l'identificativo del menù selezionato
	 */
	public int getCurrentMenu() {
		return currentMenu;
	}

	/**
	 * Carica le immagini che verranno visualizzate a video.
	 */
	protected abstract void loadImages();

	/**
	 * Scorre i menù verso l'alto.
	 */
	public void selectUp() {
	}

	/**
	 * Scorre i menù verso il basso.
	 */
	public void selectDown() {
	}

	/**
	 * Metodo di utilità. Restituisce l'ascissa del centro dello schermo in base
	 * alla risoluzione corrente e in relazione all'immagine passata come
	 * parametro.
	 * 
	 * @param image
	 *            è l'immagine che si vuole posizionare.
	 * @return un intero che rappresenta l'ascissa della posizione dell'immagine
	 */
	protected int getX(Image image) {
		if (image == null) {
			return TankBattleFrame.CENTER_X;
		} else {
			return TankBattleFrame.CENTER_X - (image.getWidth(null) / 2);
		}
	}

	/**
	 * Metodo di utilità. Restituisce l'ordinata del centro dello schermo in
	 * base alla risoluzione corrente e in relazione all'immagine passata come
	 * parametro.
	 * 
	 * @param image
	 *            è l'immagine che si vuole posizionare.
	 * @return un intero che rappresenta l'ordinata della posizione
	 *         dell'immagine.
	 */
	protected int getY(Image image) {
		if (image == null) {
			return TankBattleFrame.CENTER_Y;
		} else {
			return TankBattleFrame.CENTER_Y - (image.getHeight(null) / 2);
		}
	}

	/**
	 * Metodo di utilità. Restituisce l'ascissa del centro dello schermo in base
	 * alla risoluzione corrente e in relazione all'immagine passata come
	 * parametro. Il valore restituito sarà scostato di un certo offset, passato
	 * come parametro.
	 * 
	 * @param image
	 *            è l'immagine che si vuole posizionare.
	 * @return un intero che rappresenta l'ascissa della posizione dell'immagine
	 */
	protected int getX(Image image, int offset) {
		if (image == null) {
			return TankBattleFrame.CENTER_X + offset;
		} else {
			return TankBattleFrame.CENTER_X - (image.getWidth(null) / 2)
					+ offset;
		}
	}

	/**
	 * Metodo di utilità. Restituisce l'ordinata del centro dello schermo in
	 * base alla risoluzione corrente e in relazione all'immagine passata come
	 * parametro. Il valore restituito sarà scostato di un certo offset, passato
	 * come parametro.
	 * 
	 * @param image
	 *            è l'immagine che si vuole posizionare.
	 * @return un intero che rappresenta l'ordinata della posizione
	 *         dell'immagine.
	 */
	protected int getY(Image image, int offset) {
		if (image == null) {
			return TankBattleFrame.CENTER_Y + offset;
		} else {
			return TankBattleFrame.CENTER_Y - (image.getHeight(null) / 2)
					+ offset;
		}
	}

}
