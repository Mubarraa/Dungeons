/**
 *
 */
package unsw.dungeon;


import java.util.ArrayList;
import java.util.List;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon{

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<Goal> goalList;
    private GoalType goal;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
        this.goalList = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addEntity(Entity entity) {
    	//System.out.println(entity.toString());
        entities.add(entity);
    }
    
    public List<Entity> getEntities() {
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public int getEntityLength() {
		return entities.size();
	}
	public GoalType getGoal() {
		return goal;
	}

	public void setGoal(GoalType goal) {
		this.goal = goal;
	}

	public List<Goal> getGoalList() {
		return goalList;
	}

	public void addGoalList(Goal goal) {
		goalList.add(goal);
	}

	/**
	 * 
	 * @param entityName
	 * @return Returns total of the entity that is passed in. 
	 * This will be called when entities are loaded
	 */
	public int getTotal (String entityName) {   	
    	
    	int i = 0;
    	
    	for (Entity collectable : entities) {
    		
    		if (collectable.toString().equals(entityName)) {
    			
    			i++;
    		}
    	}
    	return i;
    }
	
	/**
	 * Goals being printed for testing
	 */
	public void printGoals() {
		for (Goal g: goalList) {
			if (g instanceof ComplexGoal) {
				List<Goal> G = ((ComplexGoal) g).getGoals();
				for (Goal i : G) {
					System.out.print(i);
				}
			}
			System.out.print(g.toString());
		}
	}
	
	/**
	 * 
	 * @return Checking to see if the goals are being completed
	 * Will be checked with every movement of player
	 */
	public boolean finalGoalCheck() {
	
		Boolean check = true;
		
		for (Goal g: goalList) {
		
			if (!g.isDone()) {
				if (g instanceof ComplexGoal) {
					List<Goal> G = ((ComplexGoal) g).getGoals();
					for (Goal i : G) {
						if (i instanceof ComplexGoal) {
							List<Goal> Complexagain = ((ComplexGoal) i).getGoals();
							System.out.print(Complexagain.toString() + "---------->Goal is not done\n");
						}
						
					}
				} else {
					System.out.print(g.toString() + "Goal is not done\n");
				}
				check = false;
				System.out.print(g.toString() + "Goal is not done\n");
			} else {
				if (g instanceof ComplexGoal) {
					List<Goal> G = ((ComplexGoal) g).getGoals();
					for (Goal i : G) {
						System.out.print(i.toString() + "Goal is done\n");
					}
				}
			}
			
		}
		
		if (check == false) {
			
			for (Goal g : goalList) {
				if (g instanceof ComplexGoal) {
					List<Goal> G = ((ComplexGoal) g).getGoals();
					for (Goal i : G) {
						if (i instanceof ExitGoal) {
							
							((ExitGoal) i).setDone(false);
						}
					}
				}
			}
		}
		
		return check;
		
	}
	
    /**
     * 
     * @param entity which collides with an object
     * @param x coordinate of object
     * @param y coordinate of object
     * @returns true if there is a collision/ true if false
     */
    public boolean collisionCheck(Entity entity, int x, int y) {
    	
    	for (Entity obj : entities ) {
    		if ((((obj.getX() == x) && (obj.getY() == y)) && obj.getState() != Status.INVISIBLE)) {
    			System.out.println(entity.getClass() + " has collided with " + obj.getClass());
    			determineType(entity, obj);
    			return true; 
    		} 

    	}
    	return false; 
    }
    
    /**
     * 
     * @param entity that collides with an object
     * @param obj that is collided by an object
     */
	public void determineType(Entity entity, Entity obj) {
		
		if (obj instanceof Wall) {
			entity.resolveCollision((Wall) obj);
		} else if (obj instanceof Key) {
			entity.resolveCollision((Key) obj);
		} else if (obj instanceof Potion) {
			entity.resolveCollision((Potion) obj);
		} else if (obj instanceof Enemy) {
			entity.resolveCollision((Enemy) obj);
		} else if (obj instanceof Treasure) {
			entity.resolveCollision((Treasure) obj);
		} else if (obj instanceof Sword) {
			entity.resolveCollision((Sword) obj);
		} else if (obj instanceof Door) {
			entity.resolveCollision((Door) obj);
		} else if (obj instanceof Exit) {
			entity.resolveCollision((Exit) obj);
		} else if (obj instanceof Switch) {
			entity.resolveCollision((Switch) obj);
		} else if (obj instanceof Portal) {
			entity.resolveCollision((Portal) obj);
		} else if (obj instanceof Boulder) {
			entity.resolveCollision((Boulder) obj);
		} else if (obj instanceof Player) {
			entity.resolveCollision((Player) obj);
		}
	}

	/**
	 * 
	 * @returns a list of all the enemies 
	 */
    public List<Enemy> findEnemy() {
    	
    	List<Enemy> allEnemies = new ArrayList<Enemy>();
    	
    	for (Entity e : entities) {
    		
    		if (e instanceof Enemy) {
    			allEnemies.add((Enemy) e);
    		}
    	}
    	
    	return allEnemies;
    }
    
    /**
     * 
     * @param enemies runs away when a player is invincible by collecting a potion
     */
    public void EnemyEscape(List<Enemy> enemies) {
    	
    	int px = player.getX();
    	int py = player.getY();
    		
    	for (Enemy e: enemies) {
    		boolean moveFound = false;
    		int x = e.getX();
    		int y = e.getY();
    		int dx = px - x;
    		int dy = py - y;
    		
    		if (dx <= 0) {
    			if (!collisionCheck(e, x+1, y)) {
    				e.setX(x+1);
    				moveFound = true; 
    			}
    		} 
    		
    		if (dx > 0 && !moveFound) {
    			if (!collisionCheck(e, x-1, y)) {
    				e.setX(x-1);
    				moveFound = true;
    			}
    		}
    		
    		if (dy <= 0 && !moveFound) {
    			if (!collisionCheck(e, x, y+1)) {
    				e.setY(y+1);
    				moveFound = true; 
    			}
    		}
    		
    		if (dy > 0 && !moveFound) {
    			if (!collisionCheck(e, x, y-1)) {
    				e.setY(y-1);
    				moveFound = true;
    			}
    		}
    		
    		if (dx == 0 && !moveFound) {
    			if (dy > 0) {
    				if (!collisionCheck(e, x, y-1)) {
        				e.setY(y-1);
        				moveFound = true;
        			} 
    			} else { 
    				if (!collisionCheck(e, x, y+1)) {
        				e.setY(y+1);
        				moveFound = true;
        			} 
    			}
	    		if (!collisionCheck(e, x-1, y)) {
					e.setX(x-1);
					moveFound = true;
				}
				if (!collisionCheck(e, x+1, y)) {
					e.setX(x+1);
					moveFound = true;
				}
    				
    		} else if (dy == 0 && !moveFound) {
    			if (dx > 0) {
    				if (!collisionCheck(e, x-1, y)) {
        				e.setX(x-1);
        				moveFound = true;
        			}
    			} else {
    				if (!collisionCheck(e, x+1, y)) {
        				e.setX(x+1);
        				moveFound = true;
        			}
    			}
    			if (!collisionCheck(e, x, y-1)) {
    				e.setY(y-1);
    				moveFound = true;
    			}
				if (!collisionCheck(e, x, y+1)) {
    				e.setY(y+1);
    				moveFound = true;
    			}
			}
    		
    	}
    	return;

    }
    
    /**
     * 
     * @param obs observer being added into the observer list for goal checking
     * @param obj 
     */
    public void addObs(Observer obs, String obj) {
    	//System.out.print("obs toString " + obs.toString() + "obs.getClass.toString " + obs.getClass().toString() + "obj " + obj);
    	for (Entity ent : entities  ) {
    		if (ent.toString().equals(obj)) {
    			
    			if (ent instanceof Enemy) {
    				((Enemy) ent).addObserver(obs);
    			}else if (ent instanceof Treasure) {
    				((Treasure) ent).addObserver(obs);
    			} else if (ent instanceof Switch) {
    				((Switch) ent).addObserver(obs);
    				System.out.print("Added Switch");
    			} else if (ent instanceof Exit) {
    				((Exit) ent).addObserver(obs);
    				System.out.print("added observer");
    			}
    			
    		}
    	}
    }
    
    /**
     * 
     * @param Pass in all enemies in the game
     * enemies move according to players movement 
     */
    public void moveAllEnemies (List<Enemy> enemies) {
    	
    	int px = player.getX();
    	int py = player.getY();
    		
    	for (Enemy e: enemies) {
    		boolean moveFound = false;
    		int x = e.getX();
    		int y = e.getY();
    		int dx = px - x;
    		int dy = py - y;
    		
    		if (dx < 0) {
    			if (!collisionCheck(e, x-1, y)) {
    				e.setX(x-1);
    				moveFound = true; 
    			}
    		} 
    		
    		if (dx > 0 && !moveFound) {
    			if (!collisionCheck(e, x+1, y)) {
    				e.setX(x+1);
    				moveFound = true;
    			}
    		}
    		
    		if (dy < 0 && !moveFound) {
    			if (!collisionCheck(e, x, y-1)) {
    				e.setY(y-1);
    				moveFound = true; 
    			}
    		}
    		
    		if (dy > 0 && !moveFound) {
    			if (!collisionCheck(e, x, y+1)) {
    				e.setY(y+1);
    				moveFound = true;
    			}
    		}
    		

    		if (dx == 0 && !moveFound) {
    			if (dy > 0) {
    				if (!collisionCheck(e, x, y+1)) {
        				e.setY(y+1);
        				moveFound = true;
        			}
    			} else {
    				if (!collisionCheck(e, x, y-1)) {
        				e.setY(y-1);
        				moveFound = true;
        			}
    			}
    		} else if (dy == 0 && !moveFound) {
    			if (dx > 0) {
    				if (!collisionCheck(e, x+1, y)) {
        				e.setX(x+1);
        				moveFound = true;
        			}
    			} else {
    				if (!collisionCheck(e, x-1, y)) {
        				e.setX(x-1);
        				moveFound = true;
        			}
    			}
    		}
    	}
    	return;
    	
    }

}
