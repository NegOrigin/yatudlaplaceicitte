package modele;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class Sensor {

	private int id;
	private int range;
	private int rate;
	private String opt;
	
	private int state = -1;
	private int oldState;
	
	public Sensor(int id, int range, int rate, String opt) {
		setId(id);
		setRange(range);
		setRate(rate);
		setOpt(opt);
		signalLoop();
	}
	
	public void signalLoop() {
		// Après rate secondes de pause, le capteur émet un signal
		Task<Void> sleeper = new Task<Void>() {
            protected Void call() throws Exception {
                try {
                    Thread.sleep(rate*1000);
                } catch (InterruptedException e) {}
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            public void handle(WorkerStateEvent event) {
            	signalLoop();
            	oldState = state;
            	state = testState();
            	sendRawSignal(state);
            	sendFilter1Signal(state);
            	sendFilter2Signal(oldState, state);
            }
        });
        new Thread(sleeper).start();
	}
	
	// Retourne l'état de la place de parking détecté par le capteur
	public int testState() {
		return (int)(Math.random()*2);
	}
	
	// Retourne le signal brut
	public String rawSignal(int state) {
		return id+":R"+range+"_P"+state+":"+opt;
	}
	
	// Transmet le signal brut
	public void sendRawSignal(int state) {
		System.out.println("Capteur "+id+" | Signal brut : "+rawSignal(state));
	}
	
	// Retourne le signal traité par le filtre 1
	public String filter1Signal(int state) {
		if(Integer.parseInt(rawSignal(state).split(":")[1].split("_P")[1]) == 0)
			return "LIBRE";
		return "OCCUPEE";
	}
	
	// Transmet le signal traité par le filtre 1
	public void sendFilter1Signal(int state) {
		System.out.println("Capteur "+id+" | Signal filtre 1 : "+filter1Signal(state));
	}
	
	// Transmet le signal traité par le filtre 2
	public void sendFilter2Signal(int oldState, int state) {
		if(oldState != state)
			System.out.println("Capteur "+id+" | Signal filtre 2 : "+filter1Signal(state));
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
}
