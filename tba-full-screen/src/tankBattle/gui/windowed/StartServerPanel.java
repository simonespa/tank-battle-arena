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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tankBattle.gui.windowed.controller.GUIWindowedController;
import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.ImageLoader;

/**
 * Crea il pannello su cui verrano disegnati tutti i componenti che gestiscono e
 * fanno partire il server.
 * 
 * @author Simone Spaccarotella
 */
public final class StartServerPanel extends JPanel {

	/* sono i componenti che verranno aggiunti sul pannello */
	private JTextField tankName;
	private JTextField tankColor;
	private JComboBox selectGameType;
	private JComboBox selectGameMode;
	private JComboBox selectGameMap;
	private DefaultComboBoxModel gameTypeModel;
	private DefaultComboBoxModel gameModeModel;
	private DefaultComboBoxModel gameMapModel;
	private JLabel tankNameLabel;
	private JLabel colorLabel;
	private JLabel gameTypeLabel;
	private JLabel gameModeLabel;
	private JLabel gameMapLabel;
	private JPanel colorPanel;
	private JButton colorButton;
	private JButton startButton;
	private JButton backButton;

	private Image[] tank;
	private GUIWindowedController controller;

	private int width = 100;
	private int height = 20;
	private int horizontalGap = 4;
	private int verticalGap = 4;

	private int xCombo = 60;
	private int yCombo = 80;

	private int xTextField = 80;
	private int yTextField = 25;

	private int xButton = xCombo + width + horizontalGap;
	private int yButton = 150;

	/**
	 * Crea il pannello con tutti i parametri di default.
	 * 
	 * @param controller
	 *            il controller di gioco
	 */
	public StartServerPanel(GUIWindowedController controller) {
		super(null);
		this.controller = controller;
		tank = new Image[2];
		tank[0] = ImageLoader.getInstance().getGUIFullScreenImage("tank1");
		tank[1] = ImageLoader.getInstance().getGUIFullScreenImage("tank2");
		setBackground(Color.BLACK);
		initComponent();
		addComponent();
	}

	/**
	 * Inizializza i componenti.
	 */
	private void initComponent() {
		setFirstLineComponent();
		setSecondLineComponent();
		setSecondLineLabel();
		fillModel();
		setStartButton();
		setBackButton();
	}

	/**
	 * Aggiunge i componenti al pannello.
	 */
	private void addComponent() {
		add(colorButton);
		add(tankName);
		add(tankColor);
		add(selectGameType);
		add(selectGameMode);
		add(selectGameMap);
		add(tankNameLabel);
		add(colorLabel);
		add(gameTypeLabel);
		add(gameModeLabel);
		add(gameMapLabel);
		add(startButton);
		add(backButton);
	}

	/**
	 * Setta la prima fila di componenti sul pannello
	 */
	private void setFirstLineComponent() {
		// la label del campo "name"
		int x = xTextField - 30;
		int y = yTextField + 4;
		tankNameLabel = new JLabel("Name");
		tankNameLabel.setForeground(Color.WHITE);
		tankNameLabel.setBounds(x, y, width / 2, height / 2);

		// il campo "name" in cui inserire il nome del tank
		x = xTextField + width + (horizontalGap * 2) + 30;
		y = yTextField;
		tankName = new JTextField(TankBattleGame.getTankName());
		tankName.addFocusListener(controller);
		tankName.setBounds(xTextField, yTextField, width, height);

		// la label del campo "color"
		x = xTextField + width + (horizontalGap * 2);
		y = yTextField + 4;
		colorLabel = new JLabel("Color");
		colorLabel.setForeground(Color.WHITE);
		colorLabel.setBounds(x, y, width / 2, height / 2);

		// il campo che visualizza il colore scelto
		x = x + 30;
		y = yTextField;
		tankColor = new JTextField();
		tankColor.setEditable(false);
		tankColor.setBounds(x, y, width / 2, height);

		// il bottone che apre la finestra di dialogo per scegliere il colore
		x = x + width / 2 + horizontalGap;
		colorButton = new JButton("Choose");
		colorButton.addActionListener(controller);
		colorButton.setBounds(x, y, 80, height);

		// inizializza il colore del carro armato in base al file di
		// configurazione
		setColorChoosed(TankBattleGame.getTankColor());
	}

	/**
	 * Setta il colore del campo che permette di scegliere il colore
	 * 
	 * @param color
	 *            il colore da settare
	 */
	public void setColorChoosed(Color color) {
		tankColor.setBackground(color);
	}

	/**
	 * Setta la seconda linea di componenti, composta dalle combo box di scelta
	 * e dalle relative label.
	 */
	private void setSecondLineComponent() {
		// la combo box per il tipo di gioco
		gameTypeModel = new DefaultComboBoxModel();
		selectGameType = new JComboBox(gameTypeModel);
		selectGameType.setName("JComboBox.Type");
		selectGameType.addFocusListener(controller);
		int x = xCombo;
		int y = yCombo;
		selectGameType.setBounds(x, y, width, height);

		// la combo box per la modalità di gioco
		gameModeModel = new DefaultComboBoxModel();
		selectGameMode = new JComboBox(gameModeModel);
		selectGameMode.setName("JComboBox.Mode");
		selectGameMode.addFocusListener(controller);
		x = xCombo + width + horizontalGap;
		y = yCombo;
		selectGameMode.setBounds(x, y, width, height);

		// la combo box per la scelta della mappa
		gameMapModel = new DefaultComboBoxModel();
		selectGameMap = new JComboBox(gameMapModel);
		selectGameMap.setName("JComboBox.Map");
		selectGameMap.addFocusListener(controller);
		x = xCombo + (width * 2) + (horizontalGap * 2);
		y = yCombo;
		selectGameMap.setBounds(x, y, width, height);
	}

	/**
	 * Setta le etichette dei componenti.
	 */
	private void setSecondLineLabel() {
		int x = xCombo + 2;
		int y = yCombo - height;
		gameTypeLabel = new JLabel("Type");
		gameTypeLabel.setForeground(Color.WHITE);
		gameTypeLabel.setBounds(x, y, width, height);

		x = xCombo + width + horizontalGap + 2;
		gameModeLabel = new JLabel("Mode");
		gameModeLabel.setForeground(Color.WHITE);
		gameModeLabel.setBounds(x, y, width, height);

		x = xCombo + (width * 2) + (horizontalGap * 2) + 2;
		gameMapLabel = new JLabel("Map");
		gameMapLabel.setForeground(Color.WHITE);
		gameMapLabel.setBounds(x, y, width, height);
	}

	/**
	 * Setta il contenuto dei componenti.
	 */
	private void fillModel() {
		gameTypeModel.addElement("- select type -");
		gameTypeModel.addElement("Team");
		gameTypeModel.addElement("Free for all");

		gameModeModel.addElement("- select mode -");
		gameModeModel.addElement("death match");
		gameModeModel.addElement("score match");

		gameMapModel.addElement("- select map -");
		gameMapModel.addElement("Desert");
		gameMapModel.addElement("Glacier");
		gameMapModel.addElement("Grassland");
		gameMapModel.addElement("Metal Arena");
	}

	/**
	 * Setta il bottone che farà partire il server.
	 */
	private void setStartButton() {
		startButton = new JButton("Start Server");
		startButton.setActionCommand("start server");
		startButton.addActionListener(controller);
		int x = xButton;
		int y = yButton;
		startButton.setBounds(x, y, width, height * 2);
	}

	/**
	 * Setta il bottone che chiude la finestra che fa partire il server e passa
	 * il controllo alla finestra dei menù
	 */
	private void setBackButton() {
		backButton = new JButton("Back");
		backButton.setActionCommand("back server");
		int x = xButton;
		int y = yButton + (height * 2) + verticalGap;
		backButton.setBounds(x, y, width, height * 2);
		backButton.addActionListener(controller);
	}

	@Override
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Graphics2D g = (Graphics2D) graph;
		g.drawImage(tank[0], 18, 95, null);
		g.drawImage(tank[1], 262, 95, null);
	}

}