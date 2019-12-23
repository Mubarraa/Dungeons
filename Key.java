package unsw.dungeon;


public class Key extends Entity {

	int id;
	// using constructor same as Player 
	public Key (int x, int y, int id) {
		super(x,y);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Key";
	}
	// in door class can have it so that if key id == door id, key will open door
	// a key will have a location in the maze within the constructor
	// player can only have one key at a time 
	// need to implement a count limit thing
	// a key has an id 
	// if player collects another key, key will be swapped 
	// and original key will replace new key on tile

}

