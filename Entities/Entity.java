package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class Entity implements CollisionHandler{

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
	private Status state ;
    
    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.state = Status.VISIBLE;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    
    public void setX(int x) {
    	this.x().set(x);
    }
    
    public void setY(int y) {
    	this.y().set(y);
    }
    
    
	public boolean isBlocking() {
    	return false;
    }
	
	@Override
	public String toString() {
		return "Entity [entityId= ]";
	}
		
	
    public Status getState() {
		return state;
	}

	public void setState(Status state) {
		this.state = state;
	}

	public void resolveCollision(Player player) { }
	
	public void resolveCollision(Enemy enemy) { }

	public void resolveCollision(Boulder boulder) { }

	public void resolveCollision(Switch _switch) { }

	public void resolveCollision(Treasure treasure) { }

	public void resolveCollision(Potion potion) { }

	public void resolveCollision(Portal portal) { }

	public void resolveCollision(Key key) { }

	public void resolveCollision(Door door) { }

	public void resolveCollision(Sword sword) { }

	public void resolveCollision(Wall wall) { }
	
	public void resolveCollision(Exit exit) { }

	@Override
	public void resolveCollision(CollisionHandler collided) {
		// TODO Auto-generated method stub
		
	}
}
