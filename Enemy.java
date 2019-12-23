package unsw.dungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class Enemy extends Entity implements Observable{

	private Dungeon dungeon;
	private Status stat;
    private boolean isMoving;    
    
    private ArrayList<Observer> observers;
	private boolean reached;
	
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		this.stat = Status.ALIVE;
		this.isMoving = true;
		this.observers = new ArrayList<Observer>();
		reached = false;
	}	

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public Status getStat() {
		return stat;
	}

	@Override
	public boolean isBlocking() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setStat(Status stat) {
		this.stat = stat;
	}

	@Override
	public void resolveCollision(CollisionHandler collided) {
		collided.resolveCollision(this);
	}

	/**
	 * When enemy collides with player either enemy dies or player gets killed
	 */
	@Override
	public void resolveCollision(Player player) {
		// TODO Auto-generated method stub
		if (player.isInvincible()) {
			stat = Status.DECEASED;
			setState(Status.INVISIBLE);
			player.getCollected().addKilled(this);
		}
		isMoving = true;
	}

	/**
	 * Enemy can't move if collides with enemy
	 */
	@Override
	public void resolveCollision(Boulder boulder) {
		isMoving = false;
	}

	/**
	 * Enemy can move on switch
	 */
	@Override
	public void resolveCollision(Switch _switch) {
		isMoving = true;
	}

	/**
	 * Enemy can move on treasure 
	 */
	@Override
	public void resolveCollision(Treasure treasure) {
		isMoving = true;
	}

	/**
	 * Enemy can move on potion
	 */
	@Override
	public void resolveCollision(Potion potion) {
		isMoving = true;
	}

	/**
	 * Enemy can move on portal but won't get transported to other portal
	 */
	@Override
	public void resolveCollision(Portal portal) {
		isMoving = true;
	}

	/**
	 * Enemy can move on key 
	 */
	@Override
	public void resolveCollision(Key key) {
		isMoving = true;
	}

	/**
	 * Enemy can move on door 
	 */
	@Override
	public void resolveCollision(Door door) {
		isMoving = false;
	}

	/**
	 * Enemy can move on player 
	 */
	@Override
	public void resolveCollision(Sword sword) {
		isMoving = true;
	}

	/**
	 * Enemy can't move on wall
	 */
	@Override
	public void resolveCollision(Wall wall) {
		isMoving = false;
	}

	/**
	 * Enemy can't move on Exit 
	 */
	@Override
	public void resolveCollision(Exit exit) {
		isMoving = false;
	}
	
	public void setReached(boolean reached) {
		this.reached = reached; 
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	/**
	 * Gets called if an enemy dies 
	 */
	@Override
	public void notifyObservers() {
		if (reached == true) {
			for (Observer o : observers) {
				o.update(this);
			}
		}
	}
	
	public String toString() {
		return "Enemy";
	}
	

	
}
