package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import tankBattle.utilities.TankEngineProperties;
import tankBattle.utilities.TankProperties;

public class SaveTankProperties {

	public static void main(String[] args) {
		TankProperties properties = new TankProperties();

		TankEngineProperties t = new TankEngineProperties();
		t.maxSpeed = 8f;
		t.acceleration = 0.8f;
		t.deceleration = 0.5f;
		t.engineRevolution = 50;
		properties.putNewConfiguration("Desert", t);

		t = new TankEngineProperties();
		t.maxSpeed = 12f;
		t.acceleration = 1.0f;
		t.deceleration = 0.2f;
		t.engineRevolution = 50;
		properties.putNewConfiguration("Glacier", t);

		t = new TankEngineProperties();
		t.maxSpeed = 15f;
		t.acceleration = 1.3f;
		t.deceleration = 0.8f;
		t.engineRevolution = 50;
		properties.putNewConfiguration("Grassland", t);

		t = new TankEngineProperties();
		t.maxSpeed = 10f;
		t.acceleration = 1.2f;
		t.deceleration = 0.8f;
		t.engineRevolution = 50;
		properties.putNewConfiguration("Metal Arena", t);
		
		saveConfiguration(properties);
	}

	public static void saveConfiguration(TankProperties tankProperties) {
		File file = new File("config/tank.init");
		ObjectOutputStream outputStream = null;
		try {
			file.createNewFile();
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(tankProperties);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
		}
	}
	
}
