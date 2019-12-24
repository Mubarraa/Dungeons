package unsw.dungeon;

import java.util.ArrayList;


public class Exit extends Entity implements Observable {

	private ArrayList<Observer> observers;
	private boolean reached;
	
	public Exit (int x, int y) {
		super(x, y);
		observers = new ArrayList<Observer>();
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
	 * Notify when a goal is reached
	 */
	@Override
	public void notifyObservers() {
		if (reached) {
			for (Observer o : observers) {
				o.update(this);
			}
		}
	}
	
	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return reached;
	}
	
	@Override 
	public String toString() {
		return "Exit";
	}
	
}
