package unsw.dungeon;

import java.util.List;

public class Portal extends Entity{
	
	int id;
	public Portal (int x, int y, int id) {
		super(x, y);
		this.id = id;
	}
	
	/**
	 * if id with same id as current portal is activated, 
	 * @param player 
	 * @param entities list of all the entities and checks if portal is instanceof switch
	 */
	public void ActivatePortal (Player player, List<Entity> entities) {
		
		for (Entity e : entities) {
			if (e instanceof Portal) {
				if (((Portal) e).getId() == id && (((Portal) e).getX() != getX() 
						|| ((Portal) e).getY() != getY())) {
					int portalX = ((Portal) e).getX();
							// make player move to that location 
							player.setX(portalX);
							player.setY(((Portal) e).getY());
				}
			}
		}
		
	}

	@Override
	public String toString() {
		return "Portal";
	}

	public int getId() {
		return id;
	}
	
}
