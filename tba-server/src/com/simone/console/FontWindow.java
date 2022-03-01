package com.simone.console;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;

final class FontWindow extends JDialog {

	private JPanel contentPanel;
	private FontInnerPanel centerPanel;
	private JPanel northernPanel;
	private JPanel southernPanel;
	private JPanel westernPanel;
	private JPanel easternPanel;
	private Controller controller;

	public FontWindow(ConsoleWindow window, String title) {
		super(window, title, true);
		controller = window.getController();
		initField();
		setSize(600, 300);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Restituisce il controller.
	 * 
	 * @return un oggetto di tipo {@link com.simone.console.Controller}
	 */
	public Controller getController() {
		return controller;
	}

	private void initField() {
		contentPanel = new JPanel(new BorderLayout());

		centerPanel = new FontInnerPanel(this);
		northernPanel = new JPanel();
		southernPanel = new JPanel();
		westernPanel = new JPanel();
		easternPanel = new JPanel();

		contentPanel.add(centerPanel, BorderLayout.CENTER);
		contentPanel.add(northernPanel, BorderLayout.NORTH);
		contentPanel.add(southernPanel, BorderLayout.SOUTH);
		contentPanel.add(westernPanel, BorderLayout.WEST);
		contentPanel.add(easternPanel, BorderLayout.EAST);

		setContentPane(contentPanel);
	}

}
