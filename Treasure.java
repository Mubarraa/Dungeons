package unsw.dungeon;

import java.util.ArrayList;

public class Treasure extends Entity implements Observable {

	private ArrayList<Observer> observers;
	private boolean reached;
	// use location from super class 
	public Treasure (int x, int y) {
		super (x,y);
		this.observers = new ArrayList<Observer>();
		reached = false;
	}

	public void setReached(boolean reached) {
		this.reached = reached;
	}
	
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Notify observers if a treasure is collected
	 */
	@Override
	public void notifyObservers() {
		if (reached == true) {
			for (Observer o : observers) {
				o.update(this);
			}
		}
	}

	@Override
	public String toString() {
		return "Treasure";
	}
}
