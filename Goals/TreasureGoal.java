package unsw.dungeon;

public class TreasureGoal implements Goal, Observer {

	private boolean done;
	private int no;
	private int initial;
	
	public TreasureGoal(int no) {
		this.done = false;
		this.no = no;
		this.initial = 0;
		
	}
	
	/**
	 * If number of collected treasures equals available treasures, update will return true 
	 */
	@Override
	public void update(Observable obj) {
		initial ++;
		if(obj instanceof Treasure && (initial == no)) {
			this.done = true;
		}
	}

	@Override
	public boolean isDone() {
		return done;
	}
	
	public String toString() {
		return "Treasure Goal";
	}
	

}
