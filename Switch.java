package unsw.dungeon;

import java.util.ArrayList;

import com.sun.tools.javac.util.List;

public class Switch extends Entity implements Observable {
	
	private ArrayList<Observer> observers;
	private boolean reached;
	private Boulder boulder;
	boolean triggered;

	public Switch (int x, int y) {
		super(x, y);
		this.reached = false;
		this.triggered = false;
		this.reached = false;
		this.boulder = null;
		this.observers = new ArrayList<Observer>();
	}
		
	/**
	 * @return true if triggered else false
	 */
	public boolean isTriggered() {
		return triggered;
	}
	
	public void setTriggered(boolean triggered) {
		this.triggered = triggered;
	}

	public void setBoulder(Boulder boulder) {
		this.boulder = boulder; 
	}
	
	public Boulder getBoulder() {
		return boulder;
	}
	public void setReached(boolean reached) {
		this.reached = reached; 
	}
	
	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Notifies observers if switch is triggered
	 */
	@Override
	public void notifyObservers() {
		if (reached) { 
			for (Observer o : observers) {
				o.update(this);
			}
		}	
	}
	
	public String toString() {
		return "Switch";
	}
	
}
