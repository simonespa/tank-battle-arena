package test;

import java.awt.Color;

import tankBattle.model.game.TankBattleGame;
import tankBattle.utilities.GUIProperties;
import tankBattle.utilities.GameProperties;
import tankBattle.utilities.SoundManager;

public class SaveGuiProperties {

	public static void main(String[] args) {
		GUIProperties gui = new GUIProperties();
		GameProperties game = new GameProperties();
		gui.initLookAndFeelMap();
		gui.gui = GUIProperties.WINDOWED;
		gui.music = SoundManager.OFF;
		gui.soundEffect = SoundManager.OFF;
		gui.setLookAndFeel("Windows");
		game.tankName = "Marco";
		game.tankColor = Color.RED;
		game.debugMode = false;
		TankBattleGame.setGUIProperties(gui);
		TankBattleGame.setGameProperties(game);
		TankBattleGame.saveProperties();
	}
	
}
