package application;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Sensor;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Déclarartion liste des capteurs
	        int nbSensors = 3;
	        ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	        
	        // Initialisation capteurs
	        for(int i=0; i<nbSensors; i++) {
	        	Sensor sensor = new Sensor(i+1, 180, 1, "Une donnée définie par l'utilisateur");
	        	sensors.add(sensor);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
