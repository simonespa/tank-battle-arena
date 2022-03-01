package com.simone.console;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;

import com.simone.console.state.GUIState;
import com.simone.console.state.Interpreter;

/**
 * &Egrave; il controller dell'interfaccia grafica. Gestisce gli eventi generati
 * dai men&ugrave; e dalla console.
 * 
 * @author Simone Spaccarotella
 * 
 */
final class Controller extends ControllerAdapter {

	private ConsoleWindow window;
	private JTabbedPane tabbedPane;
	private CommandLine commandLine;
	private ConsoleMenu menu;
	private JTextArea area;

	/**
	 * Crea un controller, passandogli il riferimento della finestra principale.
	 * 
	 * @param window
	 *            il riferimento alla finestra principale.
	 * @see {@link com.simone.console.ConsoleWindow}
	 */
	public Controller(ConsoleWindow window) {
		this.window = window;
		commandLine = window.getCommandLine();
		commandLine.addKeyListener(this);
		tabbedPane = window.getTabbedPane();
		menu = (ConsoleMenu) window.getJMenuBar();
		menu.registerController(this);
	}

	/**
	 * Setta il riferimento della text area nella quale viene mostrato il
	 * risultato della modifica del font.
	 * 
	 * @param area
	 *            la text area nella quale avvengono i cambiamenti.
	 */
	public void registerNewTextArea(JTextArea area) {
		this.area = area;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("start")) {
			startMenu();
		} else if (command.equals("stop")) {
			stopMenu();
		} else if (command.equals("close")) {
			closeMenu();
		} else if (command.equals("background")) {
			backgroundMenu();
		} else if (command.equals("foreground")) {
			foregroundMenu();
		} else if (command.equals("font")) {
			fontMenu();
		} else if (command.equals("changeFamily")) {
			changeFamily(e);
		} else if (command.equals("changeSize")) {
			changeSize(e);
		} else if (command.equals("boldCheckBox")) {
			setBold(e);
		} else if (command.equals("italicCheckBox")) {
			setItalic(e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			CommandLine commandLine = (CommandLine) e.getSource();
			String command = commandLine.getText();
			commandLine.setText("");
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		closeMenu();
	}

	/**
	 * Gestisce l'evento generato dal menù "Start menù".
	 */
	private void startMenu() {
		
		menu.getMenu(0).getItem(0).setEnabled(false);
		menu.getMenu(0).getItem(1).setEnabled(true);
	}

	/**
	 * Gestisce l'evento generato dal menù "Stop menu".
	 */
	private void stopMenu() {
		Interpreter.stopServer();
		menu.getMenu(0).getItem(0).setEnabled(true);
		menu.getMenu(0).getItem(1).setEnabled(false);
	}

	/**
	 * Chiude la console.
	 */
	private void closeMenu() {
		GUIState.setWindowDimension(window.getWidth(), window.getHeight());
		GUIState.save();
		stopMenu();
		System.exit(0);
	}

	/**
	 * Gestisce l'evento generato dal menù "Background".
	 */
	private void backgroundMenu() {
		Color color = JColorChooser.showDialog(window, "Background color",
				GUIState.getBackgroundColor());
		if (color != null) {
			for( int i = 0; i < tabbedPane.getTabCount(); i++) {
				
			}
			commandLine.setBackground(color);
			GUIState.setBackgroundColor(color);
		}
	}

	/**
	 * Gestisce l'evento generato dal menù "Foreground".
	 */
	private void foregroundMenu() {
		Color color = JColorChooser.showDialog(window, "Background color",
				GUIState.getForegroundColor());
		if (color != null) {
			for( int i = 0; i < tabbedPane.getTabCount(); i++) {
				tabbedPane.getTabComponentAt(i).setForeground(color);
				((JTextArea) tabbedPane.getTabComponentAt(i)).setCaretColor(color);
			}
			commandLine.setForeground(color);
			commandLine.setCaretColor(color);
			GUIState.setForegroundColor(color);
		}
	}

	/**
	 * Gestisce l'evento generato dal menù "Font"
	 */
	private void fontMenu() {
		new FontWindow(window, "Font");
	}

	/**
	 * Cambia il font del testo.
	 * 
	 * @param e
	 *            l'evento generato.
	 */
	private void changeFamily(ActionEvent e) {
		JComboBox b = (JComboBox) e.getSource();
		String family = (String) b.getSelectedItem();
		Font oldFont = GUIState.getFont();
		GUIState
				.setFont(new Font(family, oldFont.getStyle(), oldFont.getSize()));
		area.setFont(GUIState.getFont());
	}

	/**
	 * Cambia la dmensione del testo.
	 * 
	 * @param e
	 *            l'evento generato
	 */
	private void changeSize(ActionEvent e) {
		JComboBox b = (JComboBox) e.getSource();
		int size = (Integer) b.getSelectedItem();
		Font oldFont = GUIState.getFont();
		GUIState
				.setFont(new Font(oldFont.getFamily(), oldFont.getStyle(), size));
		area.setFont(GUIState.getFont());
	}

	/**
	 * Setta lo stile "grassetto".
	 * 
	 * @param e
	 *            l'evento generato.
	 */
	private void setBold(ActionEvent e) {
		JCheckBox bold = (JCheckBox) e.getSource();
		Font oldFont = GUIState.getFont();
		if (bold.isSelected()) {
			if (oldFont.isItalic()) {
				GUIState.setFont(new Font(oldFont.getFamily(),
						(Font.BOLD | Font.ITALIC), oldFont.getSize()));
			} else {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.BOLD,
						oldFont.getSize()));
			}
		} else {
			if (oldFont.isItalic()) {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.ITALIC,
						oldFont.getSize()));
			} else {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.PLAIN,
						oldFont.getSize()));
			}
		}
		area.setFont(GUIState.getFont());
	}

	/**
	 * Setta lo stile "corsivo".
	 * 
	 * @param e
	 *            l'evento generato.
	 */
	private void setItalic(ActionEvent e) {
		JCheckBox italic = (JCheckBox) e.getSource();
		Font oldFont = GUIState.getFont();
		if (italic.isSelected()) {
			if (oldFont.isBold()) {
				GUIState.setFont(new Font(oldFont.getFamily(),
						(Font.BOLD | Font.ITALIC), oldFont.getSize()));
			} else {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.ITALIC,
						oldFont.getSize()));
			}
		} else {
			if (oldFont.isBold()) {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.BOLD,
						oldFont.getSize()));
			} else {
				GUIState.setFont(new Font(oldFont.getFamily(), Font.PLAIN,
						oldFont.getSize()));
			}
		}
		area.setFont(GUIState.getFont());
	}

	/**
	 * Gestisce l'evento scaturito dalla linea di comando.
	 */
	private void commandLine(KeyEvent e) {
		CommandLine commandLine = (CommandLine) e.getSource();
		String command = commandLine.getText();
		commandLine.setText("");
		System.out.println(command);
	}

}