package unsw.dungeon;

//import java.util.ArrayList;
import java.util.List;

public interface GameEntity {

    
    public boolean collisionHandler (int x, int y, Player player, List<Entity> entities );
}
