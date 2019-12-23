package unsw.dungeon;

/**
 * 
 * @author z5127033
 * state is true if the door is OPEN, false if the door is CLOSED
 */
public class Door extends Entity {

	private int id;
	private boolean state;
	public Door (int x, int y, int id) {
		super(x, y);
		this.id = id;
		this.state = false;
	}

	/**
	 * 
	 * @param dungeon
	 * @param collectables
	 * @returns true if key and door id are the same 
	 */
	public boolean checkid(Dungeon dungeon, Collectables collectables) {
		if (collectables.getKeyID() == this.id) {
			System.out.println("Key and door ID match!");
			this.state = true;
			return true;

		}
		return false;
	}
	
	@Override
	public boolean isBlocking() {
		return this.state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public String toString() {
		return "Door";
	}
}

