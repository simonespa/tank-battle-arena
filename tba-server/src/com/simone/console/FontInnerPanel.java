package com.simone.console;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import com.simone.console.state.GUIState;

final class FontInnerPanel extends JPanel {

	/* i pannelli che andranno nei rispettivi quadranti */
	private JPanel theFirst;
	private JPanel theSecond;
	private JPanel theThird;
	private JPanel theFourth;
	/* i bordi dei due riquadri superiori */
	private Border outerBorder;
	private Border fontBorder;
	private Border styleBorder;
	/* i componenti del primo riquadro */
	private JComboBox familyComboBox;
	private DefaultComboBoxModel familyModel;
	private JComboBox sizeComboBox;
	private DefaultComboBoxModel sizeModel;
	/* i componenti del secondo riquadro */
	private JCheckBox boldCheckBox;
	private JLabel boldLabel;
	private JCheckBox italicCheckBox;
	private JLabel italicLabel;
	/* l'unico componente del terzo riquadro */
	private JTextArea area;
	/* i componenti del quarto riquadro */
	private JButton ok;
	private JButton cancel;
	/* il controller dell'interfaccia grafica */
	private FontWindow owner;
	private Controller controller;

	/**
	 * Crea un pannello con all'interno tutti i componenti necessari alla
	 * gestione dei font.
	 * 
	 * @param controller
	 *            il controller dell'interfaccia grafica.
	 */
	public FontInnerPanel(FontWindow window) {
		super(new GridLayout(2, 2));
		owner = window;
		controller = window.getController();
		initBorder();
		initFirstGroup();
		initSecondGroup();
		initThirdGroup();
		initFourthGroup();
	}

	/**
	 * Inizializza i bordi che verranno impostati all'interfaccia grafica.
	 */
	private void initBorder() {
		outerBorder = BorderFactory.createCompoundBorder();
		fontBorder = BorderFactory.createTitledBorder("Family and Size");
		styleBorder = BorderFactory.createTitledBorder("Style");
		// setta il bordo esterno
		setBorder(outerBorder);
	}

	/**
	 * Inizializza i componenti che stanno nel quadrante in alto a sinistra.
	 */
	private void initFirstGroup() {
		// istanzia il pannello del primo quadrante
		theFirst = new JPanel();

		// acquisisce l'ambiente grafico locale
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		// acquisisce tutti i nomi di font supportati dal sistema
		String[] familyNames = env.getAvailableFontFamilyNames();
		// istanzia ed inizializza il model
		familyModel = new DefaultComboBoxModel();
		for (String familyName : familyNames) {
			familyModel.addElement(familyName);
		}
		// istanzia la combo box settandone i parametri
		familyComboBox = new JComboBox(familyModel);
		familyComboBox.setSelectedItem(GUIState.getFont().getFamily());
		familyComboBox.setActionCommand("changeFamily");
		familyComboBox.addActionListener(controller);

		// istanzia ed inizializza il model
		sizeModel = new DefaultComboBoxModel();
		sizeModel.addElement(8); // la dimensione del font
		sizeModel.addElement(9);
		sizeModel.addElement(10);
		sizeModel.addElement(11);
		sizeModel.addElement(12);
		sizeModel.addElement(14);
		sizeModel.addElement(16);
		sizeModel.addElement(18);
		sizeModel.addElement(20);
		// istanzia la combo box settandone i parametri
		sizeComboBox = new JComboBox(sizeModel);
		sizeComboBox.setSelectedItem(GUIState.getFont().getSize());
		sizeComboBox.setActionCommand("changeSize");
		sizeComboBox.addActionListener(controller);
		// aggiunge i componenti al pannello
		theFirst.setBorder(fontBorder);
		theFirst.add(familyComboBox);
		theFirst.add(sizeComboBox);

		// istanzia il pannello al quadrante
		add(theFirst);
	}

	/**
	 * Inizializza i componenti che stanno nel quadrante in alto a destra.
	 */
	private void initSecondGroup() {
		// istanzia il pannello del secondo quadrante
		theSecond = new JPanel();
		// inizializza il controllo per "bold"
		boldLabel = new JLabel("Bold");
		boldCheckBox = new JCheckBox();
		boldCheckBox.setActionCommand("boldCheckBox");
		boldCheckBox.addActionListener(controller);
		if (GUIState.getFont().isBold()) {
			boldCheckBox.setSelected(true);
		}
		// inizializza il controllo per "italic"
		italicLabel = new JLabel("Italic");
		italicCheckBox = new JCheckBox();
		italicCheckBox.setActionCommand("italicCheckBox");
		italicCheckBox.addActionListener(controller);
		if (GUIState.getFont().isItalic()) {
			italicCheckBox.setSelected(true);
		}
		// aggiunge i componenti al pannello
		theSecond.setBorder(styleBorder);
		theSecond.add(boldLabel);
		theSecond.add(boldCheckBox);
		theSecond.add(italicLabel);
		theSecond.add(italicCheckBox);

		// aggiunge il pannello al quadrante
		add(theSecond);
	}

	/**
	 * Inizializza i componenti che stanno nel quadrante in basso a sinistra.
	 */
	private void initThirdGroup() {
		// istanzia il pannello
		theThird = new JPanel();

		// istanzia il componente
		area = new JTextArea(GUIState.getFontText());
		area.setBackground(GUIState.getBackgroundColor());
		area.setForeground(GUIState.getForegroundColor());
		area.setFont(GUIState.getFont());
		area.setEditable(false);
		area.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		JScrollPane scrollPane = new JScrollPane(area);
		controller.registerNewTextArea(area);

		// infine aggiunge il pannello al quadrante
		add(scrollPane);
	}

	/**
	 * Inizializza i componenti che stanno nel quadrante in basso a destra.
	 */
	private void initFourthGroup() {
		// istanzia il pannello
		theFourth = new JPanel(new GridLayout(2, 1));

		// istanzia i componenti
		ok = new JButton("Ok");
		// aggiunge un listener anonimo
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConsoleWindow consoleWindow = (ConsoleWindow) owner.getOwner();
				consoleWindow.getConsole().setFont(GUIState.getFont());
				owner.dispose();
			}

		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				owner.dispose();
			}

		});
		// aggiunge i componenti al pannello
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(ok, BorderLayout.CENTER);
		buttonPanel.add(cancel, BorderLayout.CENTER);
		centerPanel.add(buttonPanel);
		theFourth.add(centerPanel);

		// aggiunge il pannello al quadrante
		add(theFourth);
	}

}