package unsw.dungeon;

public class EnemyGoal implements Goal, Observer {

	private boolean done;
	private int no;
	private int initial;
	
	public EnemyGoal(int no) {
		this.done = false;
		this.no = no;
		this.initial = 0;
	}
	
	/**
	 * Updates will be made when number of times update function is called 
	 * equals number of enemies are initially in game 
	 */
	@Override
	public void update(Observable obj) {
		
		initial ++;
		if(obj instanceof Enemy && (initial == no)) {
			this.done = true;
		}
	}

	/**
	 * Returns true when update is completed
	 */
	@Override
	public boolean isDone() {
		return done;
	}
	
	public String toString() {
		return "Enemy Goal";
	}

}
