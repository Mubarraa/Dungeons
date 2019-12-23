package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javafx.stage.Stage;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public abstract class DungeonLoader {

    private JSONObject json;
    private Stage primaryStage;

    public DungeonLoader(String filename, Stage primaryStage) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
        this.primaryStage = primaryStage;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Dungeon dungeon = new Dungeon(width, height);

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i));
            
        }


        System.out.print("Anything here");
        determineGoals(dungeon);
        dungeon.printGoals();
        
        return dungeon;


    }
    
    public void determineGoals(Dungeon dungeon) {
    	JSONObject goal_condition = json.getJSONObject("goal-condition");
    	
    	//String subGoals = json.getString("subgoals");

    	String jsonGoals = goal_condition.getString("goal");
    	
    	
    	if (jsonGoals.equals("OR")) {
    		JSONArray subgoals = goal_condition.getJSONArray("subgoals");
    		
    		ComplexGoal or = new ComplexGoal(false);
    		
    		for (int i = 0; i < subgoals.length(); i++) {
    			JSONObject subGoal = subgoals.getJSONObject(i);
    			String goal = subGoal.getString("goal");
    			
    			random(dungeon, or, subGoal, goal);
    		}
    		System.out.print("This best not come in here OR\n");
    		dungeon.addGoalList(or);
    		//dungeon.setGoal(GoalType.OR);   
    		
    	}else if (jsonGoals.equals("AND")) {
    		JSONArray subgoals = goal_condition.getJSONArray("subgoals");
    		
    		ComplexGoal and = new ComplexGoal(true);
    		
    		for (int i = 0; i < subgoals.length(); i++) {
    			JSONObject subGoal = subgoals.getJSONObject(i);
    			String goal = subGoal.getString("goal");
    			
    			random(dungeon, and, subGoal, goal);
    		}
    		dungeon.addGoalList(and);
    		//dungeon.setGoal(GoalType.AND);
    		System.out.print("This best not come in here AND \n");
    	}else {
    		//dungeon.setGoal(GoalType.SIMPLE);
    		//String goal = jsonGoals.getString("goal");
    		Goal new_goal = loadGoals(dungeon, jsonGoals);
    		dungeon.addGoalList(new_goal);
    		System.out.print("I SEE YOU\n");
    	}    	    	
    	
    }

	private void random(Dungeon dungeon, ComplexGoal or, JSONObject subGoal, String goal) {
		if (goal.equals("OR")) {
			JSONArray subGoals2 = subGoal.getJSONArray("subgoals");
			ComplexGoal or2 = new ComplexGoal(false);
			
			addingComplexGoal(dungeon, subGoals2, or2);
			or.addgoal(or2);
			
		} else if (goal.equals("AND")) {
			JSONArray subGoals2 = subGoal.getJSONArray("subgoals");
			ComplexGoal or2 = new ComplexGoal(true);
			
			addingComplexGoal(dungeon, subGoals2, or2);
			or.addgoal(or2);    				
			
		} else {
			Goal or_goal = loadGoals(dungeon, goal);
			or.addgoal(or_goal);
		}
	}

	private void addingComplexGoal(Dungeon dungeon, JSONArray subGoals2, ComplexGoal or2) {
		for (int j = 0; j < subGoals2.length(); j++) {
			JSONObject subGoal2 = subGoals2.getJSONObject(j);
			String goal2 = subGoal2.getString("goal");
			Goal or_goal2 = loadGoals(dungeon, goal2);    					
			or2.addgoal(or_goal2);
		}
	}

    
    
    private Goal loadGoals(Dungeon dungeon, String condition ) {
    	//String goal = condition.getString("goal");
    	
    	switch (condition) {
        case "enemies":
        	int i = dungeon.getTotal("Enemy");        
    		EnemyGoal _enemyGoal = new EnemyGoal(i);
    		
    		String e = "Enemy";
    		
    		dungeon.addObs(_enemyGoal, e);
    				
    		return _enemyGoal;
    		//break;
    	case "treasure":
    		int j = dungeon.getTotal("Treasure");
    		String t = "Treasure";
    		
    		TreasureGoal _treasureGoal = new TreasureGoal(j);
    		dungeon.addObs(_treasureGoal,t);
//    		break;
    		return _treasureGoal;
    	case "boulders":
    		
    		int s = dungeon.getTotal("Switch");
    		System.out.print("Total ----> " + s);
    		String s2 = "Switch";
    		SwitchGoal _switchGoal = new SwitchGoal(s);
    		dungeon.addObs(_switchGoal,s2);
//    		break;
    		return _switchGoal;
    	case "exit":
    		String ex = "Exit";
    		ExitGoal _exitGoal = new ExitGoal();                
    		dungeon.addObs(_exitGoal,ex);
    		return _exitGoal;
    		
    	case "AND" :
    		ComplexGoal and = new ComplexGoal(true);
    		return and;
//    		determineGoals(dungeon);
    	case "OR" :
    		System.out.print("-------->SUBGOAL OR");
    		ComplexGoal or = new ComplexGoal(false);
    		return or;
//    		determineGoals(dungeon);
		}
    	return null;
    }


    // need to add a get Id from json line 
    // which will be in the key, door and portal cases (which we have to make) 
    private void loadEntity(Dungeon dungeon, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int id;

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "enemy":
        	Enemy enemy = new Enemy(dungeon, x, y);
        	onLoad(enemy);
        	entity = enemy;
        	break;
        case "wall":
            Wall wall = new Wall(x, y);
            onLoad(wall);
            entity = wall;
            break;
        // TODO Handle other possible entities
        case "key":
        	id = json.getInt("id");
        	Key key = new Key(x, y, id);
        	onLoad(key);
        	entity = key;
        	break;
        case "sword":
        	Sword sword = new Sword(x, y);
        	onLoad(sword);
        	entity = sword;
        	break;
        case "treasure":
        	Treasure treasure = new Treasure(x, y);
        	onLoad(treasure);
        	entity = treasure;
        	break;
        case "door":
        	id = json.getInt("id");
        	Door door = new Door(x, y, id);
        	// would this need to change bc of when the door opens? or make another function for that
        	onLoad(door);
        	entity = door;
        	break;	
        case "switch":
        	Switch floorSwitch = new Switch(x, y);
        	onLoad(floorSwitch);
        	entity = floorSwitch;
        	break;
        case "exit":
        	Exit exit = new Exit(x, y);
        	onLoad(exit);
        	entity = exit;
        	break;
        case "invincibility":
        	Potion potion = new Potion(x, y);
        	onLoad(potion);
        	entity = potion;
        	break;
        case "portal":
        	id = json.getInt("id");
        	Portal portal = new Portal(x, y, id);
        	onLoad(portal);
        	entity = portal;
        	break;
        case "boulder":
        	Boulder boulder = new Boulder(x, y);
        	onLoad(boulder);
        	entity = boulder;
        	break;
        // need to do a portal one but unsure about where the image should be shown welp 
        }
        dungeon.addEntity(entity);
    }

        
    public Stage getPrimaryStage() {
		return primaryStage;
	}

	public abstract void onLoad(Entity player);

    public abstract void onLoad(Enemy enemy);
    
    public abstract void onLoad(Wall wall);
    
    public abstract void onLoad(Key key);
    
    public abstract void onLoad(Treasure treasure);
    
    public abstract void onLoad(Sword sword);
    
    public abstract void onLoad(Door door);
    
    public abstract void onLoad(Switch floorSwitch);
    
    public abstract void onLoad(Exit exit);
    
    public abstract void onLoad(Potion potion);
    
    public abstract void onLoad(Portal portal);

    public abstract void onLoad(Boulder boulder);
    // NEEED TO DO THIS 

}
