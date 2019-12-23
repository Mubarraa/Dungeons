package unsw.dungeon;

public interface CollisionHandler {

	void resolveCollision(CollisionHandler collided);
	
	void resolveCollision(Player player);
	void resolveCollision(Enemy enemy);
	void resolveCollision(Boulder boulder);	
	void resolveCollision(Switch _switch);	
	void resolveCollision(Treasure treasure);
	void resolveCollision(Potion potion);	
	void resolveCollision(Portal portal);
	void resolveCollision(Key key);
	void resolveCollision(Door door);
	void resolveCollision(Sword sword);
	void resolveCollision(Wall wall);	
	void resolveCollision(Exit exit);
	
}
