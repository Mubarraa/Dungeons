package unsw.dungeon.test;

import unsw.dungeon.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class PlayerTest {

	
	
	@Test
	void testMovement() {
		// Setup
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 2, 2); 
		d.setPlayer(p);
		p.moveRight();

		// moves from (2, 2) to (3, 2)
		assertEquals(p.getX(), 3);
		assertEquals(p.getY(), 2);
	
		
	}
		 
	
	
	@Test 
	void testSword() {
		Dungeon d = new Dungeon(10, 10);
		Player p = new Player(d, 2, 2); 
		Sword sword = new Sword(2, 3);
		Enemy enemy = new Enemy(d, 2, 4);
		Sword sword2 = new Sword(3, 4);
		Enemy enemy2 = new Enemy(d, 3, 5);
		Enemy enemy3 = new Enemy(d, 4, 5);
		Enemy enemy4 = new Enemy(d, 5, 5);
		Enemy enemy5 = new Enemy(d, 6, 5);
		Enemy enemy6 = new Enemy(d, 6, 4);
	
		d.setPlayer(p);
		d.addEntity(sword);
		d.addEntity(enemy);
		d.addEntity(sword2);
		d.addEntity(enemy2);
		d.addEntity(enemy3);
		d.addEntity(enemy4);
		d.addEntity(enemy5);
		d.addEntity(enemy6);
		
		assertEquals(p.isPushingBoulder(), false);
		// Player moves down to (2, 3) from (2, 2) to pick up a sword
		p.moveDown();
		assertEquals(p.isPushingBoulder(), false);
		
		int r = p.getCollected().getSwordCount();
		assertEquals(r, 5);
		assertEquals(p.isPushingBoulder(), false);
		// Player moves down to (2, 4) from (2, 3) to kill an enemy
		p.moveDown();
		assertEquals(p.getCollected().getSwordCount(), 4);
		
		assertEquals(p.isPushingBoulder(), false);
		assertEquals(p.getCollected().getEnemyCount(), 1);
		assertEquals(p.isPushingBoulder(), false);
		// Player moves right to (3, 4) from (2, 4) but 
		// cannot pick up another sword as it already has a sword
		p.moveRight();
		assertEquals(p.getCollected().getSwordCount(), 4);

		// manually testing if a sword can be used up to 5 times
		// kills a enemy at (3, 5) by moving down from (3, 4)
		p.moveDown();
		assertEquals(p.getCollected().getSwordCount(), 3);
		
		p.moveRight();
		assertEquals(p.getCollected().getSwordCount(), 2);

		p.moveRight();
		assertEquals(p.getCollected().getSwordCount(), 1);
		
		p.moveRight();
		assertEquals(p.getCollected().getSwordCount(), 0);

		assertEquals(p.getCollected().getEnemyCount(), 5);
		// no Sword Left so player will die when collides with enemy
		p.moveUp();
		assertEquals(enemy6.getStat(), Status.ALIVE);
		assertEquals(p.getStat(), Status.DECEASED);
	}
	
	
	
	@Test
	void testKeyDoorSimple() {
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 2, 2); 
		Key key1 = new Key(2, 3, 3);
		//Key key2 = new Key(2, 4, 2);
		Door door1 = new Door (3, 4, 3);
		d.setPlayer(p); 
		d.addEntity(key1);
		//d.addEntity(key2);
		d.addEntity(door1);
		assertEquals(p.isHoldingKey(), false);
		
		// player will move to (2,3) from (2, 2) to take key1 
		p.moveDown();
		assertEquals(p.getX(), 2);
		assertEquals(p.getY(), 3);
		assertEquals(key1.getId(), p.getCollected().getKeyID());
		assertEquals(p.isHoldingKey(), true);

		// player will move to (2,4) from (2, 3)
		p.moveDown();
		assertEquals(p.isHoldingKey(), true);
		
		// player will move to (3,4) to open door 
		p.moveRight();

		assertEquals(p.getCollected().getKeyCount(), 0);
		assertEquals(door1.getId(), key1.getId());
		
		assertEquals(p.isHoldingKey(), false);
		assertEquals(p.getCollected().getKeyCount(), 0);

		assertEquals(p.getX(), 3);
		assertEquals(p.getY(), 4);

		assertEquals(door1.getX(), 3);
		assertEquals(door1.getY(), 4);	 
	}
	
	@Test
	void testMultipleKeyDoor() {
		/*
		 * |p |  |
		 * |k1|d2|
		 * |d1|k2|
		 */
		Dungeon d = new Dungeon(2, 3);
		Player p = new Player(d, 0, 0); 
		Key key1 = new Key(0, 1, 1);
		Key key2 = new Key(1, 2, 2);
		Door door1 = new Door(0, 2, 1);
		Door door2 = new Door(1, 1, 2);
		d.setPlayer(p); 
		d.addEntity(key1);
		d.addEntity(key2);
		d.addEntity(door1);
		d.addEntity(door2);
		assertEquals(p.isHoldingKey(), false);
		
		// Player move down onto key 1
		p.moveDown();
		assertEquals(p.isHoldingKey(), true);
		assertEquals(p.getCollected().getKeyID(), key1.getId());
		
		// Player move into door 2
		p.moveRight();
		assertEquals(true, p.isHoldingKey());
		//System.out.println("X: " + p.getX() + "\tY: " + p.getY());
		assertEquals(p.getX(), 0);
		assertEquals(p.getY(), 1);
		
	}
		 
	 
	 
	
	@Test
	void testPortal() {
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 2, 2); 
		Portal p1 = new Portal(3, 2, 1);
		Portal _p1 = new Portal(1, 2, 1);
		
		d.setPlayer(p);
		
		d.addEntity(p1);
		d.addEntity(_p1);
		assertEquals(p1.getId(), _p1.getId());
	    
		p.moveRight();
		assertEquals(p.getX(), 1);
		assertEquals(p.getY(), 2);
	}
	
	
	@Test
	void testTreasure() {
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 1, 3); 
		Treasure t1 = new Treasure(2, 3);
		Treasure t2 = new Treasure(3, 3);
		Treasure t3 = new Treasure(3, 4);
		
		d.setPlayer(p);
		d.addEntity(t1);
		d.addEntity(t2);
		d.addEntity(t3);
		// player will move to (2,3)
		p.moveRight();
		assertEquals(p.getCollected().getTreasureCount(), 1);
		p.moveRight();
		assertEquals(p.getCollected().getTreasureCount(), 2);
		p.moveDown();
		assertEquals(p.getCollected().getTreasureCount(), 3);
	}
	
	
	@Test
	void testEnemyMovement() {
		
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 1, 3); 
		d.setPlayer(p);
		
		Enemy enemy = new Enemy(d, 3, 4);
		d.addEntity(enemy);
		d.addEntity(p);
		// enemy will move from (3, 4) to (1, 3) where 
		// player is as there are no obstructions
		d.moveAllEnemies(d.findEnemy());
		assertEquals(enemy.getX(), 1);
		assertEquals(enemy.getY(), 3);
		
		// player will die 
		assertEquals(p.getStat(), Status.DECEASED);
		
		Player p2 = new Player(d, 4, 3);
		d.setPlayer(p2);
		d.addEntity(p2);
		Wall wall = new Wall(3, 3);
		d.addEntity(wall);
		d.moveAllEnemies(d.findEnemy());
		
		// enemy moves right until it gets blocked by a wall
		assertEquals(enemy.getX(), 2);
		assertEquals(enemy.getY(), 3);
	
		
	}
	
	@Test
	void EnemyEscaping() {
		Dungeon d = new Dungeon(10, 10);
		Player p = new Player(d, 2, 8); 
		d.setPlayer(p);
		
		Enemy enemy = new Enemy(d, 3, 4);
		d.addEntity(enemy);
		d.addEntity(p);
		
		d.EnemyEscape(d.findEnemy());
		
		assertEquals(enemy.getX(), 10);
		assertEquals(enemy.getY(), 0);
	}
}


