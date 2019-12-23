package unsw.dungeon;

public class ExitGoal implements Goal, Observer {

	private boolean done;
	
	public ExitGoal() {
		this.done = false;
	}
	
	/*
	 * Returns done 
	 */
	@Override
	public boolean isDone() {
		return done;
	}

	/**
	 * sets done to true if update method is called
	 */
	@Override
	public void update(Observable obj) {
		if (obj instanceof Exit) {
			this.done = true; 
		}
	}

	/**
	 * 
	 * @param done Sets false or true depending on if exit goal is reached
	 */
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public String toString() {
		return "Exit Goal";
	}

}
