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

package tankBattle.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

/**
 * Questa è una classe di utilità che permette il debbugging del programma. I
 * suoi metodi sono statici, perché non abbiamo bisogno di una sua istanza per
 * effettuare determinate operazioni. Mediante questa classe si può stampare su
 * "standard output" e su "standard error" in maniera formattata, ed è inoltre
 * possibile redirezionare l'output su file, o sulla rete, per evitare di avere
 * i messaggi sulla console.
 * 
 * @author Simone Spaccarotella
 */
public final class Utility {

	/** è la text area su cui verrà redirezionato l'output */
	private static Console console = new Console("Console");
	/** se true l'output viene redirezionato sulla text area */
	private static boolean outRedirected = false;
	/** se true l'error viene redirezionato sulla text area */
	private static boolean errRedirected = false;
	/** è il descrittore del file per lo standard Output */
	private static File outputFile;
	/** è il descrittore del file per lo standard Error */
	private static File errorFile;
	/** stream di output */
	private static PrintStream outputStream;
	/** stream di input */
	private static PrintStream errorStream;
	/** è il numero corrente di linea */
	private static int line = 0;
	/**
	 * Impone ai metodi
	 * {@link tankBattle.utilities.Utility#logException(String)
	 * logException(String)},
	 * {@link tankBattle.utilities.Utility#logException(Throwable)
	 * logException(Throwable)},
	 * {@link tankBattle.utilities.Utility#logException(Object, String)
	 * logException(Object, String)},
	 * {@link tankBattle.utilities.Utility#logException(Object, Throwable)
	 * logException(Object, Throwable)}, una formattazione mediante la quale
	 * viene stampato il solo messaggio di errore ricavato dall'eccezione.
	 */
	public static final int GET_MESSAGE = 0;
	/**
	 * Impone ai metodi
	 * {@link tankBattle.utilities.Utility#logException(String)
	 * logException(String)},
	 * {@link tankBattle.utilities.Utility#logException(Throwable)
	 * logException(Throwable)},
	 * {@link tankBattle.utilities.Utility#logException(Object, String)
	 * logException(Object, String)},
	 * {@link tankBattle.utilities.Utility#logException(Object, Throwable)
	 * logException(Object, Throwable)}, una formattazione mediante la quale
	 * viene stampata la traccia dello stack al momento dell'eccezione e il
	 * messaggio di errore generato.
	 */
	public static final int PRINT_STACK_TRACE = 1;
	/** Lo stream di output. */
	public static final int OUTPUT_STREAM = 2;
	/** Lo stream di errore. */
	public static final int ERROR_STREAM = 3;
	/** Entrambi gli stream */
	public static final int BOTH_STREAM = 4;
	/** indica la formattazione attuale */
	private static int stateException = GET_MESSAGE;

	/**
	 * Effettua una stampa sull'output stream corrente.
	 * 
	 * @param message
	 *            la stringa da stampare.
	 */
	public static synchronized void log(String message) {
		line++;
		// acquisisce il timestamp
		String timestamp = new Date(System.currentTimeMillis()).toString();
		// costruisce la stringa
		String text = line + ") " + timestamp + ": " + message;
		// se l'output è stato redirezionato anche sulla console
		if (outRedirected && console != null) {
			// scrive sulla console
			console.printOut(text);
		}
		// altrimenti stampa solo sullo standard output
		System.out.println(text);
		System.out.println();
	}

	/**
	 * Effettua una stampa sull'output stream corrente.
	 * 
	 * @param obj
	 *            l'oggetto da cui verrà ricavato il messaggio.
	 */
	public static synchronized void log(Object obj) {
		line++;
		// acquisisce la stringa dell'oggetto
		String message = obj.toString();
		// acquisisce il timestamp
		String timestamp = new Date(System.currentTimeMillis()).toString();
		// costruisce la stringa
		String text = line + ") " + timestamp + ": " + message;
		// se l'output è stato redirezionato anche sulla console
		if (outRedirected && console != null) {
			// scrive sulla console
			console.printOut(text);
		}
		// altrimenti stampa solo sullo standard output
		System.out.println(text);
		System.out.println();
	}

	/**
	 * Effettua una stampa sull'output stream corrente, aggiungendo al messaggio
	 * la dicitura {@code "<obj> says: "}, dove {@code <obj>} è il nome della
	 * classe a cui appartiene l'oggetto che ha invocato questo metodo, passando
	 * come parametro il riferimento {@code this}. In questo modo è possibile
	 * non solo leggere il messaggio stampato, ma sapere anche chi lo ha
	 * stampato.
	 * 
	 * @param obj
	 *            l'oggetto che richiama questo metodo
	 * @param message
	 *            la stringa da stampare
	 */
	public static synchronized void log(Object obj, String message) {
		line++;
		// acquisisce il timestamp
		String timestamp = new Date(System.currentTimeMillis()).toString();
		// costruisce la stringa
		String text = line + ") " + timestamp + " - ";
		// se l'oggetto è un thread
		if (obj instanceof Runnable) {
			// acquisisce il nome del thread
			text += ((Thread) obj).getName() + " says: " + message;
		} else {
			// altrimenti acquisisce il nome canonico della classe
			String canonicalName = obj.getClass().getCanonicalName();
			String[] split = canonicalName.split("\\.");
			String name = split[split.length - 1];
			text += name + " says: " + message;
		}
		// se l'output è stato redirezionato anche sulla console
		if (outRedirected && console != null) {
			// scrive sulla console
			console.printOut(text);
		}
		// altrimenti stampa solo sullo standard output
		System.out.print(text);
		System.out.println();
	}

	/**
	 * /** Effettua una stampa sull'output stream corrente, aggiungendo al
	 * messaggio la dicitura {@code "<obj> says: "}, dove {@code <obj>} è il
	 * nome della classe a cui appartiene l'oggetto che ha invocato questo
	 * metodo, passando come parametro il riferimento {@code this}. In questo
	 * modo è possibile non solo leggere il messaggio stampato, ma sapere anche
	 * chi lo ha stampato.
	 * 
	 * @param obj1
	 *            l'oggetto che richiama questo metodo
	 * @param obj2
	 *            l'oggetto da cui verrà ricavato il messaggio.
	 */
	public static synchronized void log(Object obj1, Object obj2) {
		line++;
		String message = obj2.toString();
		String timestamp = new Date(System.currentTimeMillis()).toString();
		String text = line + ") " + timestamp + " - ";
		if (obj1 instanceof Thread) {
			text += ((Thread) obj1).getName() + " says: " + message;
		} else {
			String canonicalName = obj1.getClass().getCanonicalName();
			String[] split = canonicalName.split("\\.");
			String name = split[split.length - 1];
			text += name + " says: " + message;
		}
		if (outRedirected && console != null) {
			console.printOut(text);
		}
		System.out.println(text);
		System.out.println();
	}

	/**
	 * Effettua una stampa sull'error stream corrente.
	 * 
	 * @param message
	 *            la stringa da stampare
	 */
	public static synchronized void logException(String message) {
		line++;
		String timestamp = new Date(System.currentTimeMillis()).toString();
		String text = line + ") " + timestamp + ": " + message;
		if (outRedirected && console != null) {
			console.printErr(text);
		}
		System.err.println(text);
		System.err.println();
	}

	/**
	 * Effettua una stampa sull'error stream corrente, aggiungendo al messaggio
	 * la dicitura {@code "<obj> says: "}, dove {@code <obj>} è il nome della
	 * classe a cui appartiene l'oggetto che ha invocato questo metodo, passando
	 * come parametro il riferimento {@code this}. In questo modo è possibile
	 * non solo leggere il messaggio stampato, ma sapere anche chi lo ha
	 * stampato.
	 * 
	 * @param obj
	 *            l'oggetto che richiama questo metodo
	 * @param message
	 *            la stringa da stampare
	 */
	public static synchronized void logException(Object obj, String message) {
		line++;
		String timestamp = new Date(System.currentTimeMillis()).toString();
		String text = line + ") " + timestamp + " - ";
		if (obj instanceof Thread) {
			text += ((Thread) obj).getName() + " says: " + message;
		} else {
			String canonicalName = obj.getClass().getCanonicalName();
			String[] split = canonicalName.split("\\.");
			String name = split[split.length - 1];
			text += name + " says: " + message;
		}
		if (outRedirected && console != null) {
			console.printErr(text);
		}
		System.err.println(text);
		System.err.println();
	}

	/**
	 * Effettua una stampa sull'error stream corrente, accettando come parametro
	 * un oggetto {@link java.lang.Throwable} dal quale estrarrà il messaggio da
	 * stampare.
	 * 
	 * @param exception
	 *            l'oggetto {@link java.lang.Throwable} che è stato lanciato a
	 *            causa di un'eccezione.
	 */
	public static synchronized void logException(Throwable exception) {
		switch (stateException) {
		case GET_MESSAGE:
			logException(exception.getMessage());
			break;
		case PRINT_STACK_TRACE:
			logException("");
			exception.printStackTrace();
			stateException = GET_MESSAGE;
			break;
		}
	}

	/**
	 * Effettua una stampa sull'error stream corrente, accettando come parametro
	 * un oggetto {@link java.lang.Throwable} dal quale estrarrà il messaggio da
	 * stampare. Questo metodo aggiunge al messaggio la dicitura {@code
	 * "<obj> says: "}, dove {@code <obj>} è il nome della classe a cui
	 * appartiene l'oggetto che ha invocato questo metodo, passando come
	 * parametro il riferimento {@code this}. In questo modo è possibile non
	 * solo leggere il messaggio stampato, ma sapere anche chi lo ha stampato.
	 * 
	 * @param obj
	 *            l'oggetto che richiama questo metodo.
	 * @param exception
	 *            l'oggetto {@link java.lang.Throwable} che è stato lanciato a
	 *            causa di un'eccezione. Da questo oggetto sarà ricavata la
	 *            stringa da stampare.
	 */
	public static synchronized void logException(Object obj, Throwable exception) {
		switch (stateException) {
		case GET_MESSAGE:
			logException(obj, exception.getMessage());
			break;
		case PRINT_STACK_TRACE:
			logException(obj, "");
			exception.printStackTrace();
			stateException = GET_MESSAGE;
			break;
		}
	}

	/**
	 * Setta la formattazione di stampa. Grazie a questo metodo è possibile
	 * imporre a {@code log()} e {@code logException()} il modo in cui stampare
	 * rispettivamente: un messaggio di avviso, oppure un messaggio di errore.
	 * Se stamparli semplicemente mediante un messaggio, oppure aggiungendo al
	 * messaggio la traccia di esecuzione dello stack.
	 * 
	 * @param state
	 *            è il tipo di formattazione desiderata. I due valori possibili
	 *            sono: {@link tankBattle.utilities.Utility#PRINT_STACK_TRACE
	 *            Utility.PRINT_STACK_TRACE} e
	 *            {@link tankBattle.utilities.Utility#GET_MESSAGE
	 *            Utility.GET_MESSAGE}.
	 * @throws InvalidStateException
	 *             viene lanciato quando il valore passato al parametro {@code
	 *             state} è diverso da quelli sopraelencati.
	 */
	public static synchronized void setSateException(int state) {
		if (state != 0 && state != 1) {
			try {
				throw new InvalidStateException(
						"Invalid State. Only GET_MESSAGE or PRINT_STACK_TRACE values are allowed.");
			} catch (InvalidStateException e) {
				Utility.logException(new Utility(), e);
			}
		}
		stateException = state;
	}

	/**
	 * Restituisce il valore del tipo di formattazione di stampa corrente.
	 * 
	 * @return uno dei seguenti valori:
	 *         {@link tankBattle.utilities.Utility#PRINT_STACK_TRACE
	 *         Utility.PRINT_STACK_TRACE} e
	 *         {@link tankBattle.utilities.Utility#GET_MESSAGE
	 *         Utility.GET_MESSAGE}.
	 */
	public static synchronized int getStateException() {
		return stateException;
	}

	/**
	 * Apre la redirezione verso un file di log per uno o entrambi gli stream.
	 * 
	 * @param streamType
	 *            un intero che codifica quale stream debba essere
	 *            redirezionato. Questo parametro può assumere i seguenti
	 *            valori: <br />{@link tankBattle.utilities.Utility#OUTPUT_STREAM
	 *            Utility.OUTPUT_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#ERROR_STREAM
	 *            Utility.ERROR_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#BOTH_STREAM
	 *            Utility.BOTH_STREAM}
	 */
	public static synchronized void redirectToFile(int streamType) {
		switch (streamType) {
		case OUTPUT_STREAM:
			redirectOutputToFile();
			break;

		case ERROR_STREAM:
			redirectErrorToFile();
			break;

		case BOTH_STREAM:
			redirectOutputToFile();
			redirectErrorToFile();
			break;

		default:
			try {
				throw new InvalidStateException(
						"Invalid type. Only OUTPUT_STREAM, ERROR_STREAM or BOTH_STREAM values are allowed.");
			} catch (InvalidStateException e) {
				Utility.logException(new Utility(), e);
			}
		}
	}

	/**
	 * Chiude la redirezione su di un file per uno o entrambi gli stream.
	 * 
	 * @param streamType
	 *            un intero che codifica quale stream redirezionato debba essere
	 *            chiuso. Questo parametro può assumere i seguenti valori: <br />
	 *            {@link tankBattle.utilities.Utility#OUTPUT_STREAM
	 *            Utility.OUTPUT_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#ERROR_STREAM
	 *            Utility.ERROR_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#BOTH_STREAM
	 *            Utility.BOTH_STREAM}
	 */
	public static synchronized void closeRedirectionToFile(int streamType) {
		switch (streamType) {
		case OUTPUT_STREAM:
			outputStream.close();
			break;

		case ERROR_STREAM:
			errorStream.close();
			break;

		case BOTH_STREAM:
			outputStream.close();
			break;

		default:
			try {
				throw new InvalidStateException(
						"Invalid type. Only OUTPUT_STREAM, ERROR_STREAM or BOTH_STREAM values are allowed.");
			} catch (InvalidStateException e) {
				Utility.logException(new Utility(), e);
			}
		}
	}

	/**
	 * Apre la redirezione verso la {@code TextArea} per uno o entrambi gli
	 * stream.
	 * 
	 * @param streamType
	 *            un intero che codifica quale stream debba essere
	 *            redirezionato. Questo parametro può assumere i seguenti
	 *            valori: <br />{@link tankBattle.utilities.Utility#OUTPUT_STREAM
	 *            Utility.OUTPUT_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#ERROR_STREAM
	 *            Utility.ERROR_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#BOTH_STREAM
	 *            Utility.BOTH_STREAM}
	 */
	public static synchronized void rediretcAlsoToTextArea(int streamType) {
		switch (streamType) {
		case OUTPUT_STREAM:
			outRedirected = true;
			break;

		case ERROR_STREAM:
			errRedirected = true;
			break;

		case BOTH_STREAM:
			outRedirected = true;
			errRedirected = true;
			break;

		default:
			try {
				throw new InvalidStateException(
						"Invalid type. Only OUTPUT_STREAM, ERROR_STREAM or BOTH_STREAM values are allowed.");
			} catch (InvalidStateException e) {
				Utility.logException(new Utility(), e);
			}
		}
	}

	/**
	 * Chiude la redirezione verso la {@code TextArea} per uno o entrambi gli
	 * stream.
	 * 
	 * @param streamType
	 *            un intero che codifica quale stream redirezionato debba essere
	 *            chiuso. Questo parametro può assumere i seguenti valori: <br />
	 *            {@link tankBattle.utilities.Utility#OUTPUT_STREAM
	 *            Utility.OUTPUT_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#ERROR_STREAM
	 *            Utility.ERROR_STREAM} <br />
	 *            {@link tankBattle.utilities.Utility#BOTH_STREAM
	 *            Utility.BOTH_STREAM}
	 */
	public static synchronized void closeRedirectionToTextArea(int streamType) {
		switch (streamType) {
		case OUTPUT_STREAM:
			outRedirected = false;
			break;

		case ERROR_STREAM:
			errRedirected = false;
			break;

		case BOTH_STREAM:
			outRedirected = false;
			errRedirected = false;
			break;

		default:
			try {
				throw new InvalidStateException(
						"Invalid type. Only OUTPUT_STREAM, ERROR_STREAM or BOTH_STREAM values are allowed.");
			} catch (InvalidStateException e) {
				Utility.logException(new Utility(), e);
			}
		}
	}

	/**
	 * Restituisce un riferimento alla {@code TextArea} verso cui è stata fatta
	 * la redirezione.
	 * 
	 * @return un oggetto di tipo {@link javax.swing.JTextArea JTextArea}.
	 */
	public static synchronized Console getTextArea() {
		return console;
	}

	/**
	 * Redireziona lo "Standard Output" su un file di log.
	 */
	private static synchronized void redirectOutputToFile() {
		outputFile = new File("log/logOutput.txt");
		try {
			outputStream = new PrintStream(outputFile);
			outputFile.createNewFile();
		} catch (FileNotFoundException e) {
			logException(new Utility(), e.getMessage());
		} catch (IOException e) {
			logException(new Utility(), e.getMessage());
		}
		System.setOut(outputStream);
	}

	/**
	 * Redireziona lo "Standard Error" su un file di log.
	 */
	private static synchronized void redirectErrorToFile() {
		errorFile = new File("log/logError.txt");
		try {
			errorStream = new PrintStream(errorFile);
			errorFile.createNewFile();
		} catch (FileNotFoundException e) {
			logException(e.getMessage());
		} catch (IOException e) {
			logException(e.getMessage());
		}
		System.setErr(errorStream);
	}

	/**
	 * Effettua le operazioni di clean-up.
	 */
	@Override
	protected void finalize() throws Throwable {
		closeRedirectionToFile(BOTH_STREAM);
	}

}