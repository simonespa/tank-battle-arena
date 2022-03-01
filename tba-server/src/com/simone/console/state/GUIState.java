package com.simone.console.state;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Questa classe &egrave; ha alcuni metodi statici, accessibili da tutte le
 * classi che hanno bisogno dei suoi servizi.
 * 
 * @author Simone Spaccarotella
 * 
 */
public final class GUIState {

	private static GUIProperties guiProperties;

	/**
	 * Inizializza i campi di questa classe, caricando da file i parametri.
	 */
	public static void init() {
		load();
	}

	/**
	 * Carica da file i parametri della configurazione grafica.
	 */
	private static void load() {
		File file = new File("config/guiProperties.init");
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(new FileInputStream(file));
			guiProperties = (GUIProperties) input.readObject();
			guiProperties.checkLookAndFeel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (input != null) {
					input = null;
				}
			}
		}
	}

	/**
	 * Salva su file i parametri della configurazione grafica.
	 */
	public static void save() {
		File file = new File("config/guiProperties.init");
		ObjectOutputStream output = null;
		try {
			output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(guiProperties);
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {

				} finally {
					if (output != null) {
						output = null;
					}
				}
			}
		}
	}

	/**
	 * Restituisce il colore di background.
	 * 
	 * @return restituisce un oggetto di tipo {@link java.awt.Color}
	 */
	public static Color getBackgroundColor() {
		return guiProperties.getBackgroundColor();
	}

	/**
	 * Restituisce il colore del testo.
	 * 
	 * @return restituisce un oggetto di tipo {@link java.awt.Color}
	 */
	public static Color getForegroundColor() {
		return guiProperties.getForegroundColor();
	}

	/**
	 * Restituisce il font del testo.
	 * 
	 * @return un oggetto di tipo {@link java.awt.Font}
	 */
	public static Font getFont() {
		return guiProperties.getFont();
	}

	/**
	 * Restituisce il look and feel.
	 */
	public static String getLookAndFeel() {
		return guiProperties.getLookAndFeelClass();
	}

	/**
	 * Setta il colore dello sfondo, ricevendo in input un oggetto colore.
	 * 
	 * @param color
	 *            l'oggetto che rappresenta il colore.
	 */
	public static void setBackgroundColor(Color color) {
		guiProperties.setBackgroundColor(color);
	}

	/**
	 * Setta il colore dello sfondo, ricevendo in input un intero.
	 * 
	 * @param color
	 *            un intero che esprime il codice del colore.
	 */
	public static void setBackgroundColor(int color) {
		guiProperties.setBackgroundColor(color);
	}

	/**
	 * Setta il colore del testo, ricevendo in input un oggetto colore.
	 * 
	 * @param color
	 *            l'oggetto che rappresenta il colore.
	 */
	public static void setForegroundColor(Color color) {
		guiProperties.setForegroundColor(color);
	}

	/**
	 * Setta il colore del testo, ricevendo in input un intero.
	 * 
	 * @param color
	 *            un intero che esprime il codice del colore.
	 */
	public static void setForegroundColor(int color) {
		guiProperties.setForegroundColor(color);
	}

	/**
	 * Setta il font del testo.
	 * 
	 * @param font
	 *            un oggetto di tipo {@link java.awt.Font}
	 */
	public static void setFont(Font font) {
		guiProperties.setFont(font);
	}

	/**
	 * Setta la dimensione della finestra principale.
	 * 
	 * @param width
	 *            la larghezza della finestra.
	 * @param height
	 *            l'altezza della finestra.
	 */
	public static void setWindowDimension(int width, int height) {
		guiProperties.setWindowDimension(width, height);
	}

	/**
	 * Restituisce la larghezza della finestra.
	 * 
	 * @return la larghezza della finestra.
	 */
	public static int getWidth() {
		return guiProperties.getWidth();
	}

	/**
	 * Restituisce l'altezza della finestra.
	 * 
	 * @return l'altezza della finestra.
	 */
	public static int getHeight() {
		return guiProperties.getHeight();
	}

	/**
	 * Restituisce il testo di prova che comparirà nella casella di testo di
	 * prova.
	 * 
	 * @return restituisce una stringa
	 */
	public static String getFontText() {
		File file = new File("config/fontText.init");
		BufferedReader input = null;
		String text = System.getProperty("line.separator") + " ";
		try {
			// acquisisce lo stream di input agganciato al file
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			// legge il file riga per riga
			while ((line = input.readLine()) != null) {
				/*
				 * aggiunge la riga letta ad una stringa complessiva. Viene
				 * aggiunto alla fine un carattere di fine linea
				 */
				text += " " + line + System.getProperty("line.separator");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (input != null) {
					input = null;
				}
			}
		}
		/*
		 * restituisce la stringa totale eliminando il fine linea all'inizio e
		 * ala fine
		 */
		return text;
	}

}