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

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/**
 * Questa classe &egrave; un campo di testo parametrizzato, nel quale &egrave;
 * possibile inserire soltanto numeri e il carattere spazio. Viene utilizzato
 * per inserire un numero di porta, che corrisponder&agrave; al numero di porta
 * del server.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class PortInputTextField extends JFormattedTextField {

	/**
	 * Crea un campo di testo, il quale verificherà la correttezza dell'input
	 * inserito.
	 * 
	 * @throws ParseException
	 *             nel caso in cui ci fosse un errore di parserizzazione
	 *             dell'input.
	 */
	public PortInputTextField() throws ParseException {
		super(new MaskFormatter("*****"));
		MaskFormatter mask = (MaskFormatter) getFormatter();
		mask.setValidCharacters("' 0123456789");
		setColumns(5);
		Font font = new Font("Verdana", Font.PLAIN, 18);
		setFont(font);
		setForeground(Color.WHITE);
		setHorizontalAlignment(JFormattedTextField.CENTER);
		setOpaque(false);
	}

}
