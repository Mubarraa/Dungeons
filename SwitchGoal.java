package unsw.dungeon;

public class SwitchGoal implements Goal, Observer {

	private boolean done;
	private int no;
	private int initial;

	public SwitchGoal(int no) {
		this.done = false;
		this.no = no;
		this.initial = 0;
	}
	
	/**
	 * If number of switches triggered equals number of switches in game, update function 
	 * will change done to true
	 */
	@Override
	public void update(Observable obj) {
		
		initial ++;
		if(obj instanceof Switch && (initial == no)) {
			this.done = true;
		}
	}

	/**
	 * Returns true if isDone else false
	 */
	@Override
	public boolean isDone() {
		return done;
	}
	
	public String toString() {
		return "Switch Goal";
	}

}
