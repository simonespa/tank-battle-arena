package com.simone.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Una classe adapter astratta per ricevere gli eventi: da tastiera, dalla
 * chiusura di una generica finestra e da un componente che genera un evento
 * "Ation".
 * 
 * I metodi in questa classe sono vuoti. Questa classe esiste per convenienza,
 * infatti grazie ad essa e possibile istanziare oggetti ascoltatori degli
 * eventi che interessano.
 * 
 * @author Simone Spaccarotella
 * 
 */
abstract class ControllerAdapter implements ActionListener, KeyListener,
		WindowListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

}