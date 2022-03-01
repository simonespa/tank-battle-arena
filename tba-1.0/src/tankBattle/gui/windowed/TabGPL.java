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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import tankBattle.model.game.TankBattleGame;

/**
 * Rappresenta il tab che visualizza un file HTML. Nella fattispecie, questo tab
 * visualizzerà il documento che contiene la licenza GPL.
 * 
 * @author Simone Spaccarotella.
 * 
 */
public class TabGPL extends JScrollPane {

	private JEditorPane editor;
	private String title;
	private Icon gplIcon;

	/** Standard MIME per il content type "testo semplice" */
	public static final String TEXT_PLAIN = "text/plain";
	/** Standard MIME per il content type "html" */
	public static final String TEXT_HTML = "text/html";

	/**
	 * Crea un nuovo tab.
	 * 
	 * @param title
	 *            il titolo del tab.
	 */
	public TabGPL(String title) {
		super();
		this.title = title;
		gplIcon = new ImageIcon("licence/gplv3.png");
		initEditor();
		setViewportView(editor);
	}

	/**
	 * Restituisce il titolo del tab.
	 * 
	 * @return una stringa che corrisponde al titolo del tab.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Restituisce l'icona.
	 * 
	 * @return l'icona del tab.
	 */
	public Icon getIcon() {
		return gplIcon;
	}

	/**
	 * Inizializza l'editor.
	 */
	private void initEditor() {
		editor = TankBattleGame.getGPLEditorPane();
	}

}
