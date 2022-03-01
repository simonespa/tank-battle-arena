package com.simone.console.state;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Questa classe rappresenta lo stato persistente dell'interfaccia grafica. La
 * sua istanza, mantengono lo stato fintanto che il programma &egrave; in
 * esecuzione. Una volta terminato il programma, viene salvata su file e
 * caricata successivamente, al prossimo riavvio.
 * 
 * @author Simone Spaccarotella
 * 
 */
final class GUIProperties implements Serializable {

	/* ID seriale di questa classe */
	private static final long serialVersionUID = 8546672332101148119L;
	/* le dimensioni della finestra principale */
	private int width;
	private int height;
	/* il colore di sfondo */
	private Color backgroundColor;
	/* il colore del testo */
	private Color foregroundColor;
	/* il font del testo */
	private Font font;
	/* il nome del look and feel utilizzato */
	private String lookAndFeelName;
	/* la struttura dati che mappa i look and feel istallati */
	private static Map<String, String> lookAndFeelMap;

	/**
	 * Setta il colore di sfondo.
	 * 
	 * @param color
	 *            l'oggetto che rappresenta il colore.
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	/**
	 * Setta il colore di sfondo.
	 * 
	 * @param color
	 *            un intero che rappresenta il codice del colore.
	 */
	public void setBackgroundColor(int color) {
		backgroundColor = new Color(color);
	}

	/**
	 * Restituisce il colore di sfondo.
	 * 
	 * @return un oggetto di tipo colore.
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Setta il colore del testo.
	 * 
	 * @param color
	 *            un oggetto di tipo colore.
	 */
	public void setForegroundColor(Color color) {
		foregroundColor = color;
	}

	/**
	 * Setta il colore del testo.
	 * 
	 * @param color
	 *            un intero che rappresenta il codice del colore.
	 */
	public void setForegroundColor(int color) {
		foregroundColor = new Color(color);
	}

	/**
	 * Restituisce il colore del testo.
	 * 
	 * @return un oggetto di tipo colore.
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Setta il font del testo.
	 * 
	 * @param font
	 *            il font che viene impostato al testo.
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Restituisce il font del testo.
	 * 
	 * @return restituisce un oggetto di tipo {@link java.awt.Font}
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Setta la dimensione della finestra principale.
	 * 
	 * @param width
	 *            la largezza in pixel
	 * @param height
	 *            l'altezza in pixel
	 */
	public void setWindowDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Restituisce la larghezza della finestra.
	 * 
	 * @return la larghezza in pixel
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Restituisce l'altezza in pixel.
	 * 
	 * @return l'altezza in pixel.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Inizializza la mappa dei look and feel disponibili
	 */
	public void checkLookAndFeel() {
		// acquisisce il look and feel di default
		LookAndFeelInfo[] istalledLookAndFeel = UIManager
				.getInstalledLookAndFeels();
		lookAndFeelMap = new HashMap<String, String>();
		for (LookAndFeelInfo info : istalledLookAndFeel) {
			lookAndFeelMap.put(info.getName(), info.getClassName());
		}
		// se il look and feel non � contenuto nella mappa
		if (!lookAndFeelMap.containsKey(lookAndFeelName)) {
			// acquisisce il primo della lista
			Set<String> keySet = lookAndFeelMap.keySet();
			lookAndFeelName = keySet.iterator().next();
		}
	}

	/**
	 * Setta il look and feel.
	 * 
	 * @param name
	 *            il nome del look and feel.
	 */
	public void setLookAndFeel(String name) {
		if (lookAndFeelMap.containsKey(name)) {
			lookAndFeelName = name;
		} else {
			lookAndFeelName = lookAndFeelMap.keySet().iterator().next();
		}
	}

	/**
	 * Restituisce il nome del look and feel.
	 * 
	 * @return una stringa che rappresenta il nome del look and feel.
	 */
	public String getLookAndFeelName() {
		// se il look and feel non è contenuto nella mappa
		if (!lookAndFeelMap.containsKey(lookAndFeelName)) {
			// acquisisce il primo della lista
			Set<String> keySet = lookAndFeelMap.keySet();
			lookAndFeelName = keySet.iterator().next();
		}
		return lookAndFeelName;
	}

	/**
	 * Restituisce la classe del Look and Feel.
	 * 
	 * @return una stringa che rappresenta il nome qualificato della classe.
	 */
	public String getLookAndFeelClass() {
		// se il look and feel non è contenuto nella mappa
		if (!lookAndFeelMap.containsKey(lookAndFeelName)) {
			// acquisisce il primo della lista
			Set<String> keySet = lookAndFeelMap.keySet();
			lookAndFeelName = keySet.iterator().next();
		}
		// e ne restituisce la classe
		return lookAndFeelMap.get(lookAndFeelName);
	}

	/**
	 * Restituisce la mappa dei look and feel istallati sul pc.
	 * 
	 * @return una mappa di look and feel.
	 */
	public Map<String, String> getLookAndFeelMap() {
		return lookAndFeelMap;
	}
	
	public void setDefaultState() {
		GUIProperties g = new GUIProperties();
		g.checkLookAndFeel();
		g.setBackgroundColor(Color.black);
		g.setForegroundColor(Color.green);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setLookAndFeel("Window");
		g.setWindowDimension(300, 300);
		File file = new File("config/guiProperties.init");
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(g);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}