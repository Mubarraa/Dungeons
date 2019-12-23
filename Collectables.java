package unsw.dungeon;

import java.util.ArrayList;


public class Collectables  {			
	
	private ArrayList<Key> keys;
	private ArrayList<Sword> swords;
	private ArrayList<Treasure> treasures;
	private ArrayList<Enemy> killed;
	private ArrayList<Switch> triggered;
	private ArrayList<Entity> all;
	
	public Collectables () {
		this.keys = new ArrayList<Key>();
		this.swords = new ArrayList<Sword>();
		this.treasures = new ArrayList<Treasure>();
		this.killed = new ArrayList<Enemy>();
		this.triggered = new ArrayList<Switch>();
		this.all = new ArrayList<Entity>();
	
	}
	
	/**
	 * 
	 * @returns Key Count 
	 */
	public int getKeyCount() {
		return keys.size();
	}
	
	/**
	 * 
	 * @returns Sword Count
	 */
	public int getSwordCount() {
		return swords.size();
	}
	
	/**
	 * 
	 * @returns Treasure Count
	 */
	public int getTreasureCount() {
		return treasures.size();
	}
	
	/**
	 * 
	 * @returns Enemy Count
	 */
	public int getEnemyCount() {
		return killed.size();
	}
	
	/**
	 * 
	 * @returns Switch Count
	 */
	public int getSwitchCount() {
		return triggered.size();
	}
	
	public ArrayList<Entity> getallEnt(){
		all.removeAll(all);
		all.addAll(keys);
		all.addAll(killed);
		all.addAll(swords);
		all.addAll(treasures);
		all.addAll(triggered);
		return all;
	}
	
	/**
	 * 
	 * @param _switch Adds switch to triggered list 
	 */
	public void addSwitch(Switch _switch) {
		triggered.add((Switch)_switch);
	}
	
	/**
	 * 
	 * @param treasure adds treasure to treasure list 
	 */
	public void addTreasure(Treasure treasure) {
		treasures.add((Treasure)treasure);
	}
	
	/**
	 * 
	 * @param key adds key to key list only if there's no key in list 
	 */
	public void addKey(Key key) {
		if (keys.size() == 0) {
			keys.add(key);
		} else {
			return;
		}
	}

	/**
	 * 
	 * @return get key id for key 0
	 */
	public int getKeyID() {
		return keys.get(0).getId();
	}

	/**
	 * Remove key which will be called if door and key id are the same
	 * when collides
	 */
	public void removeKey() {
		if (keys.size() > 0) {
		 	keys.remove(0);
		}
	}

	/**
	 * 
	 * @param adds 5 swords into sword list only if theres no swords in list
	 */
	public void addSword(Sword sword) {
		if (swords.size() == 0) {
			for (int i = 0; i < 5; i++) {
				swords.add(sword);
			}
		}  else {
			return;
		}
	}
	
	/**
	 * 
	 * @param e adds enemies into list 
	 */
	public void addKilled (Enemy e) {
		killed.add(e);		
	}
	
	public void removeSwitch(Switch _switch) {
		triggered.remove(_switch);
	}
	
	/**
	 * Removes sword from list when it hits an enemy
	 */
	public void UseSword() {
		int size = swords.size() - 1;
		// if all swords have been used
		if (size < 0 ) {
			return;
		}
		swords.remove(size);
	}

}
