package com.simone.console;

import javax.swing.JTextField;

import com.simone.console.state.GUIState;

public class CommandLine extends JTextField {

	public CommandLine() {
		super();
		setBackground(GUIState.getBackgroundColor());
		setForeground(GUIState.getForegroundColor());
		setFont(GUIState.getFont());
	}
	
}
