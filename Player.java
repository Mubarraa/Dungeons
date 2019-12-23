package unsw.dungeon;

import java.util.ArrayList;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private boolean isMoving;
    private boolean isPushingBoulder;
    private Collectables collected;
    private boolean isInvincible;
    private Status stat;
    private Boulder currentBoulder;
    private boolean isPortal;
    private int PotionNum;
    private Switch currentSwitch;
    
    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.currentSwitch = null;
        this.isPushingBoulder = false;
        this.collected = new Collectables();
        this.isInvincible = false;
        this.stat = Status.ALIVE;
        this.currentBoulder = null;
        this.isMoving = false;
        this.isPortal = false;
        this.PotionNum = 0;
    }

    /**
     * Player moves up and switch is untriggered if boulder is moved off 
     */
    public void moveUp() {
    	
        if (getY() > 0) {
            potionInvincibility();
        	boolean check = dungeon.collisionCheck(this, getX(), getY()-1);
            	
        	if (!check || isMoving) {
        		if (!isPortal) {
        			y().set(getY() - 1);
        		}
        		isPortal = false;
        	}
        	if (getCurrentSwitch() != null) {
	        	if (getCurrentSwitch().isTriggered() && (getCurrentSwitch().getX() == getX()) && (getCurrentSwitch().getY() == getY())) {
	        		getCurrentSwitch().getBoulder().moveUp(dungeon, this);
	        		switchTrigger();
	        	}
        	}
        }

        enemyMovement();

    }


	/**
     * Player moves down and switch is untriggered if boulder is moved off 
     */
    public void moveDown() {
    	
    	 if (getY() < dungeon.getHeight() - 1) {
             potionInvincibility();
    		boolean check = dungeon.collisionCheck(this, getX(), getY()+1); 
        	if (!check || isMoving) { 
        		if (!isPortal) {

        			y().set(getY() + 1);
        		}
        		isPortal = false;
        	}
        	if (getCurrentSwitch() != null) {
	        	if (getCurrentSwitch().isTriggered() && (getCurrentSwitch().getX() == getX()) && (getCurrentSwitch().getY() == getY())) {
	        		
	        		getCurrentSwitch().getBoulder().moveDown(dungeon, this);
	        		switchTrigger();
	        	}
        	}

        }

    	 enemyMovement();
    }

    /**
     * Player moves left and switch is untriggered if boulder is moved off 
     */
    public void moveLeft() {
    	
        if (getX() > 0) {
            potionInvincibility();
        	boolean check = dungeon.collisionCheck(this, getX() - 1, getY());
        	if (!check || isMoving) {
        		if (!isPortal) {
        			//System.out.println("moveLeft succeeded");
        			x().set(getX() - 1);
        		}
        		isPortal = false;
        	}
        	if (getCurrentSwitch() != null) {
	        	if (getCurrentSwitch().isTriggered() && (getCurrentSwitch().getX() == getX()) && (getCurrentSwitch().getY() == getY())) {

	        		
	        		getCurrentSwitch().getBoulder().moveLeft(dungeon, this);
	        		switchTrigger();
	        	}
        	}
        }
        enemyMovement();
    }


    /**
     * Player moves right and switch is untriggered if boulder is moved off 
     */
    public void moveRight() {
  	
        if (getX() < dungeon.getWidth() - 1) {
            potionInvincibility();
        	boolean check = dungeon.collisionCheck(this, getX() + 1, getY());

        	if (!check || isMoving) {
        		if (!isPortal) {
            		//System.out.println("moveRight succeeded");
        			x().set(getX() + 1);
        		}
        		isPortal = false;
        	}
        }
        enemyMovement();
        if (getCurrentSwitch() != null) {
        	if (getCurrentSwitch().isTriggered() && (getCurrentSwitch().getX() == getX()) && (getCurrentSwitch().getY() == getY())) {
        		
        		getCurrentSwitch().getBoulder().moveRight(dungeon, this);
        		switchTrigger();
        	}
    	} 
        //System.out.print("--------------------->Switch isnt triggerd");
    }
    
    /**
     * For player to have a limit of 10 steps for when invincible
     */
    public void potionInvincibility() {
    	if (isInvincible()) {
    		PotionNum++;
    		if (PotionNum == 10) {
    			setInvincible(false);
    			resetPotionNum();
    		}
    	}
    }
    
    /**
     * For enemy moving and running away
     */
    public void enemyMovement() {
    	if (!isInvincible()) {
    		dungeon.moveAllEnemies(dungeon.findEnemy());
    	} else {
    		dungeon.EnemyEscape(dungeon.findEnemy());
    	}
    }
    
    /**
     * Trigger is turned off when boulder is away from switch
     */
    public void switchTrigger() {
    	getCurrentSwitch().setTriggered(false);
    	getCurrentSwitch().setBoulder(null);
    	setCurrentSwitch(null);
    }
    
    public void resetPotionNum() {
    	this.PotionNum = 0;
    }
    
    /**
     * 
     * @returns true if key count is greater than 0
     */
    public boolean isHoldingKey() {
    	if (this.collected.getKeyCount() == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
 
	private void setCurrentSwitch(Switch _switch) {
		this.currentSwitch = _switch;
		
	}

	public Switch getCurrentSwitch() {
		return currentSwitch;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isInvincible() {
		return isInvincible;
	}

	public void setInvincible(boolean isInvincible) {
		this.isInvincible = isInvincible;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public Status getStat() {
		return stat;
	}

	public void setStat(Status stat) {
		this.stat = stat;
	}	
	
    public Collectables getCollected() {
		return collected;
	}

	public void setCollected(Collectables collected) {
		this.collected = collected;
	}

	public void setX(int x) {
    	this.x().set(x);
    }
    
    public void setY(int y) {
    	this.y().set(y);
    }
    
	public Boulder getCurrentBoulder() {
		return currentBoulder;
	}

	public void setCurrentBoulder(Boulder currentBoulder) {
		this.currentBoulder = currentBoulder;
	}
	
	public boolean isPushingBoulder() {
		return isPushingBoulder;
	}
	
	public void setPushingBoulder(boolean isPushingBoulder) {
		this.isPushingBoulder = isPushingBoulder;
	}

	@Override
	public void resolveCollision(CollisionHandler collided) {
		
		collided.resolveCollision(this);
	}
	
	/**
	 * Player is set to pushing boulder when it collides with a boulder
	 */
	@Override
	public void resolveCollision(Boulder boulder) {
		
		setPushingBoulder(true);
		isMoving = true;
		setCurrentBoulder(boulder);
		boulder.setGamePlayer(this);
	}
	
	/**
	 * When player collides with a switch and is holding boulder, switch is turned on
	 */
	@Override
	public void resolveCollision(Switch _switch) {
		// player is able to move through 
		isMoving = true;
		setCurrentSwitch(_switch);
		if (isPushingBoulder()) {
			_switch.setTriggered(true);
			currentBoulder.setOnSwitch(_switch);
		}
	}
	
	/**
	 * When player collides with a key, it can get picked up if keycount is 0
	 */
	@Override
	public void resolveCollision(Key key) {
		if (collected.getKeyCount() == 0) {
			key.setState(Status.INVISIBLE);
			this.collected.addKey(key);
		}
		this.isMoving = true;
	}
	
	/**
	 * Door will open if player is holding correct key with same id 
	 */
	@Override
	public void resolveCollision(Door door) {

		if (isHoldingKey() && door.checkid(dungeon, collected)) {
			collected.removeKey();
			isMoving = true;
			door.setState(Status.INVISIBLE); 
		} else {
			isMoving = false; 
		}
	}

	/**
	 * When player collides with an enemy
	 */
	@Override
	public void resolveCollision(Enemy enemy) {

			// enemy dies if player is invincible 
		if (this.isInvincible()) {
			enemyKilled(enemy);
			// enemy dies by being hit by boulder
		} else if (isPushingBoulder()) {
			enemyKilled(enemy);
			// enemy dies by sword hit	
		} else if (collected.getSwordCount() > 0 ) {
			collected.UseSword();
			enemyKilled(enemy);			
		}else  {
			stat = Status.DECEASED;
			setState(Status.INVISIBLE);
		}
		if (enemy.getStat().equals(Status.DECEASED)) {
			enemy.setReached(true);
			enemy.notifyObservers();
		}
		
	}

	/**
	 * When a player is killed it will be set to deceased and invisible 
	 * @param enemy
	 */
	public void enemyKilled(Enemy enemy) {
		enemy.setStat(Status.DECEASED);
		enemy.setState(Status.INVISIBLE);
		isMoving = true;
		collected.addKilled(enemy);
	}


	/**
	 * When player collides with a treasure
	 */
	@Override
	public void resolveCollision(Treasure treasure) {
		treasure.setState(Status.INVISIBLE);
		isMoving = true;
		collected.addTreasure(treasure);
		treasure.setReached(true);
		treasure.notifyObservers();
	}

	/**
	 * When player collides with potion
	 */
	@Override
	public void resolveCollision(Potion potion) {

		isMoving = true;
		isInvincible = true;
		potion.setState(Status.INVISIBLE);

	}

	/**
	 * When player collides with a portal
	 */
	@Override
	public void resolveCollision(Portal portal) {
		isPortal = true;
		isMoving = true;
		portal.ActivatePortal(this, dungeon.getEntities());
	}

	/**
	 * When player collides with a sword it will get picked up if sword count is 0
	 */
	@Override
	public void resolveCollision(Sword sword) {

		this.isMoving = true;
		if (collected.getSwordCount() == 0) {
			this.collected.addSword(sword);
			sword.setState(Status.INVISIBLE);
		}
	}

	/**
	 * When player collides with wall it will be blocked
	 */
	@Override
	public void resolveCollision(Wall wall) {
		this.isMoving = false ;
	}
	
	/**
	 * When player collides with exit 
	 */
	@Override
	public void resolveCollision(Exit exit) {
		isMoving = true;
		exit.setReached(true);
		exit.notifyObservers();
	}
	
	@Override 
	public String toString() {
		return "Player";
	}
}
