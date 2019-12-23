package unsw.dungeon.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import unsw.dungeon.*;

class DefensiveTest {

	@Test
	void BoulderTest() {
		// Setup
		Dungeon d = new Dungeon(6, 6);
		Player p = new Player(d, 2, 2);
		Boulder b = new Boulder(2, 1);
		Enemy enemy1 = new Enemy(d, 2, 0);
		Boulder bb = new Boulder (1, 0);
		Switch s = new Switch(4, 3);
		Boulder b2 = new Boulder(5,3);
		
		d.addEntity(p);
		d.addEntity(b);
		d.addEntity(enemy1);
		d.addEntity(bb);
		d.addEntity(s);
		d.setPlayer(p);
		
		
		// boulder test
		assertEquals(p.getCurrentBoulder(), null);
		assertEquals(p.isPushingBoulder(), false);
		p.moveUp();
		
		assertEquals(p.getCurrentBoulder(), b);
		assertEquals(p.isPushingBoulder(), true);
		p.moveUp();
		
		assertEquals(enemy1.getStat(), Status.DECEASED);
		
		p.moveLeft();
		// player pushes only one boulder at a time
		assertEquals(p.getCurrentBoulder(), b);
		
		assertEquals(p.getCurrentBoulder().getOnSwitch() , null);
		p.moveDown();
		p.moveDown();
		
		assertEquals(p.isPushingBoulder(), true);
		
		
		// trigger switch
		p.setX(5);
		p.setY(3);
		p.setCurrentBoulder(b2);
		p.setPushingBoulder(true);
		p.moveLeft();
		assertEquals(p.isPushingBoulder(), true);
		assertEquals(p.getCurrentBoulder().getOnSwitch() , s);
			
	}
	
	@Test
	void PotionTest() {
		
		Dungeon d = new Dungeon(5, 5);
		Player p = new Player(d, 2, 2); 
		d.setPlayer(p);
		p.moveRight();
		
		// ENEMY ENTITY
		Potion potion = new Potion(3, 3);
		d.addEntity(potion);

		// player moves from (3,2) to (3,3)  
		p.moveDown();
		assertEquals(potion.getState(), Status.INVISIBLE);
		assertEquals(p.isInvincible(), true);
	
		Enemy enemy2 = new Enemy(d, 2, 3);
		d.addEntity(enemy2);
		//moves from (3, 3) to (2, 3) to kill enemy
		// move left to enemy while invincible 
		p.moveLeft();
		assertEquals(p.getCollected().getEnemyCount(), 1);
		assertEquals(enemy2.getStat(), Status.DECEASED);
		assertEquals(p.getStat(), Status.ALIVE);
		
		// potion invincibility will be turned off
		p.setInvincible(false);
		assertEquals(p.isInvincible(), false);
		
		// moves from (2, 3) to (2, 2)
		Enemy enemy1 = new Enemy(d, 2, 2);
		d.addEntity(enemy1);
		p.moveUp();
		
		assertEquals(p.getStat(), Status.DECEASED);
		assertEquals(enemy1.getStat(), Status.ALIVE);
	}

}