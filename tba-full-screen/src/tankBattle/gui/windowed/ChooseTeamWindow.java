package tankBattle.gui.windowed;

import javax.swing.JFrame;

import tankBattle.gui.windowed.controller.GUIWindowedController;

public class ChooseTeamWindow extends JFrame {

	private static ChooseTeamPanel panel;
	
	private ChooseTeamWindow(GUIWindowedController controller) {
		super("Choose the team");
		panel = new ChooseTeamPanel(this, controller);
		setContentPane(panel);
		setSize(450, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public static void showWindow(GUIWindowedController controller) {
		ChooseTeamWindow c = new ChooseTeamWindow(controller);
	}
	
}
