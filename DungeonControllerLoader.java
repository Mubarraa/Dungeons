package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    private List<ImageView> toRemove;
    private List<ImageView> p;
    //Images
    private Image playerImage;
    private Image wallImage;
    private Image keyImage;
    private Image treasureImage;
    private Image swordImage;
    private Image doorImage;
    private Image switchImage;
    private Image exitImage;
    private Image potionImage;
    private Image enemyImage;
    private Image portalImage;
    private Image boulderImage;

    public DungeonControllerLoader(String filename, Stage primaryStage)
            throws FileNotFoundException {
        super(filename, primaryStage);
        entities = new ArrayList<>();
        toRemove = new ArrayList<>();
        p = new ArrayList<>();
        
        playerImage = new Image("/human_new.png");
        wallImage = new Image("/brick_brown_0.png");
        keyImage = new Image("/key.png");
        treasureImage = new Image("/gold_pile.png");
        swordImage = new Image("/greatsword_1_new.png");
        doorImage = new Image("/closed_door.png");
        switchImage = new Image("/pressure_plate.png");
        exitImage = new Image("/exit.png");
        potionImage = new Image("/bubbly.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        portalImage = new Image("/portal.png");
        boulderImage = new Image("boulder.png");
    }

    @Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        System.out.print("-----------------" + view.toString()+ " ------------------");
        addEntity(player, view);
        p.add(view);
    }
    
    @Override 
    public void onLoad(Portal portal) {
    	ImageView view = new ImageView(portalImage);
    	addEntity(portal, view);
    	
    }

    @Override 
    public void onLoad(Enemy enemy) {
        ImageView view = new ImageView(enemyImage);
        System.out.print("Enemy -------------------- " + view.toString() + " ------------------- ");
        addEntity(enemy, view);
        toRemove.add(view);
    }
    
    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }
    
    @Override
    public void onLoad(Key key) {
        ImageView view = new ImageView(keyImage);
        addEntity(key, view);
        toRemove.add(view);
    }
    
    @Override
    public void onLoad(Treasure treasure) {
        ImageView view = new ImageView(treasureImage);
        addEntity(treasure, view);
        toRemove.add(view);
    }
    
    @Override
    public void onLoad(Sword sword) {
        ImageView view = new ImageView(swordImage);
        addEntity(sword, view);
        toRemove.add(view);
    }
    
    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(doorImage);
        addEntity(door, view);
        toRemove.add(view);
    }
    
    @Override
    public void onLoad(Switch floorSwitch) {
        ImageView view = new ImageView(switchImage);
        addEntity(floorSwitch, view);
    }
    
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }
    
    @Override
    public void onLoad(Potion potion) {
        ImageView view = new ImageView(potionImage);
        addEntity(potion, view);
        toRemove.add(view);
    }
    
    @Override 
    public void onLoad(Boulder boulder) {
    	ImageView view = new ImageView(boulderImage);
    	addEntity(boulder, view);
    }
    
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }
    

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
    	Stage s = this.getPrimaryStage();
        return new DungeonController(load(), entities, toRemove, p, s);
    }


}
