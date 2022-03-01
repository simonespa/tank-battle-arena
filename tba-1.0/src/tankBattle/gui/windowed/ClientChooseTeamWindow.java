package tankBattle.gui.windowed;

import javax.swing.JFrame;

/**
 * La finestra di scelta del team da parte del client.
 * 
 * @author Simone Spaccarotella
 * 
 */
public class ClientChooseTeamWindow extends JFrame {

	private static ClientChooseTeamPanel panel;

	/**
	 * Crea una nuova finestra di scelta.
	 */
	private ClientChooseTeamWindow() {
		super("Choose the team");
		panel = new ClientChooseTeamPanel(this);
		setContentPane(panel);
		setSize(450, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	/**
	 * Mostra la finestra di scelta del team.
	 */
	public static void showWindow() {
		ClientChooseTeamWindow c = new ClientChooseTeamWindow();
	}

}
