package unsw.dungeon;

public class Wall extends Entity {

	private int x;
	private int y;
	
    public Wall(int x, int y) {
        super(x, y);
        this.x = x;
        this.y = y;
    }
    

	/**
	 * always returns true for a wall 
	 */
    @Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return true;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "wall";
	}

    
    
}
