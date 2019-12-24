package unsw.dungeon;

import java.util.List;

public interface Observable {
	public void addObserver(Observer o);
	public void notifyObservers();
}
