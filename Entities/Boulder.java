package unsw.dungeon;
/*
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
*/
public class Boulder extends Entity {

    private boolean isMoving;
    private Player gamePlayer;
    private Switch onSwitch;
    
	public Boulder(int x, int y) {
		super(x, y);
		this.isMoving = false;
		this.gamePlayer = null;
		this.onSwitch = null;
	}

	/**
	 * 
	 * @param dungeon
	 * @param player boulder moves up depending on players position,
	 * collision and switch is untriggered if it can move off switch
	 */
    public void moveUp(Dungeon dungeon, Player player) {
        if (getY() > 0) {
        	
        	boolean checkT = false;
        	boolean moved = false;
        	
        	if (onSwitch != null && onSwitch.triggered) {
        		checkT = true;
        	}       	
        	if (!dungeon.collisionCheck(this, getX(), getY()-1)|| isMoving) {
        		y().set(player.getY() - 1);  
        		//System.out.println("boulder is coming in here");
        		moved = true;
        	}
        	
        	if (checkT && moved) {
        		onSwitch.setTriggered(false);
        		gamePlayer.getCollected().removeSwitch(onSwitch);
        		onSwitch.setBoulder(null);
        	}
        }
    }
	
	/**
	 * 
	 * @param dungeon
	 * @param player boulder moves down depending on players position,
	 * collision and switch is untriggered if it can move off switch
	 */
    public void moveDown(Dungeon dungeon, Player player) {
        if (getY() < dungeon.getHeight() - 1) {
        	
        	boolean checkT = false;
        	boolean moved = false;
        	
        	if (onSwitch != null && onSwitch.triggered) {
        		checkT = true;
        	} 
        	
        	if (!dungeon.collisionCheck(this, getX(), getY()+1) || isMoving) {
        		y().set(player.getY() + 1);
        		moved = true;
        	}
        	
        	if (checkT && moved) {
        		onSwitch.setTriggered(false);
        		gamePlayer.getCollected().removeSwitch(onSwitch);
        		onSwitch.setBoulder(null);
        	}
        }
    }
	
	/**
	 * 
	 * @param dungeon
	 * @param player boulder moves left depending on players position,
	 * collision and switch is untriggered if it can move off switch
	 */
    public void moveLeft(Dungeon dungeon, Player player) {
        if (getY() > 0) {
        	boolean checkT = false;
        	boolean moved = false;
        	
        	if (onSwitch != null && onSwitch.triggered) {
        		checkT = true;
        	} 
        	if (!dungeon.collisionCheck(this, getX() - 1, getY()) || isMoving) {
        		x().set(player.getX() - 1);
        		moved = true;
        	}
        	if (checkT && moved) {
        		onSwitch.setTriggered(false);
        		gamePlayer.getCollected().removeSwitch(onSwitch);
        		onSwitch.setBoulder(null);
        	}
        }
    }
	
	/**
	 * 
	 * @param dungeon
	 * @param player boulder moves right depending on players position,
	 * collision and switch is untriggered if it can move off switch
	 */
    public void moveRight(Dungeon dungeon, Player player) {
        if (getX() < dungeon.getWidth() - 1) {
        	
        	boolean checkT = false;
        	boolean moved = false;
        	
        	if (onSwitch != null && onSwitch.triggered) {
        		checkT = true;
        	} 
        	if (!dungeon.collisionCheck(this, getX() + 1, getY()) || isMoving) {
        		x().set(player.getX() + 1);
        		moved = true;
        	}
        	
        	if (checkT && moved) {
        		onSwitch.setTriggered(false);
        		gamePlayer.getCollected().removeSwitch(onSwitch);
        		onSwitch.setBoulder(null);
        	}
        	
        }
    }        

    /**
     * 
     * @return returns true if isMoving false if not 
     */
	public boolean isMoving() {
		return isMoving;
	}


	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}


	public Player getGamePlayer() {
		return gamePlayer;
	}


	public void setGamePlayer(Player gamePlayer) {
		this.gamePlayer = gamePlayer;
	}
	

	public Switch getOnSwitch() {
		return onSwitch;
	}


	public void setOnSwitch(Switch onSwitch) {
		this.onSwitch = onSwitch;
	}


	@Override
	public void resolveCollision(CollisionHandler collided) {
		collided.resolveCollision(this);
	}

	/**
	 * Boulder collision with Enemy
	 */
	@Override
	public void resolveCollision(Enemy enemy) {
		if (gamePlayer.isPushingBoulder()) {
			isMoving = true;
			enemy.setStat(Status.DECEASED);
			gamePlayer.getCollected().addKilled(enemy);
			enemy.setState(Status.INVISIBLE);
		}
		if (enemy.getStat().equals(Status.DECEASED)) {
			enemy.setReached(true);
			enemy.notifyObservers();
		}
	}
	/**
	 * Collision is boulder 
	 */
	public void resolveCollision(Boulder boulder) {
		setMoving(false);
	}
	
	/**
	 * Collision with switch which will make boulder move to switch if 
	 * switch is not triggered else not
	 */
	@Override
	public void resolveCollision(Switch _switch) {
		if (_switch.getBoulder() == null || !_switch.isTriggered() || getOnSwitch() == null) {
			setMoving(true);
			_switch.setTriggered(true);
			onSwitch = _switch;
			gamePlayer.getCollected().addSwitch(_switch);
			_switch.setBoulder(this);
		// if switch already has a boulder or switch is triggered 
		// boulder will not move
		} else {
			setMoving(false);
		}
		_switch.setReached(true);
		_switch.notifyObservers();
			
	}

	/**
	 * Boulder can move on treasure 
	 */
	@Override
	public void resolveCollision(Treasure treasure) {
		isMoving = true;
	}

	/**
	 * Boulder can move on potion
	 */
	@Override
	public void resolveCollision(Potion potion) {
		isMoving = true;
		
	}

	/**
	 * Boulder can move on portal
	 */
	@Override
	public void resolveCollision(Portal portal) {
		isMoving = true;
	}

	/**
	 * Boulder can move on key 
	 */
	@Override
	public void resolveCollision(Key key) {
		isMoving = true;
	}

	/**
	 * Boulder can move on door 
	 */
	@Override
	public void resolveCollision(Door door) {
		isMoving = false;
	}

	/**
	 * Boulder can move on sword 
	 */
	@Override
	public void resolveCollision(Sword sword) {
		isMoving = true;
	}

	/**
	 * Boulder can't move if collides with wall 
	 */
	@Override
	public void resolveCollision(Wall wall) {
		isMoving = false;
	}

	/**
	 * Can't collide with exit 
	 */
	@Override
	public void resolveCollision(Exit exit) {
		isMoving = false;
	}
    
    
}
