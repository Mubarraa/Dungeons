package unsw.dungeon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sun.naming.internal.ResourceManager;
//import com.sun.tools.doclint.Entity;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;
    
	@FXML
	private AnchorPane dungeonPane;
	
	@FXML
	private ListView<String> inventoryItems;
	
	private ObservableList<String> inventoryList;
	
	@FXML
    private StackPane pausePane;

    private List<ImageView> initialEntities;

    private Player player;

    private Dungeon dungeon;
        
    private List<ImageView> toRemove;
    
    private List<ImageView> p;
    
    private boolean alive;
    
	private Stage primaryStage;
	
    
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, List<ImageView> toRemove, List<ImageView> player, Stage s) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.toRemove = new ArrayList<>(toRemove);
        this.alive = true;
        this.p = new ArrayList<>(player);
        this.primaryStage = s;
    }

    /**
     * Initialises grid pane with images of dirt
     * Adds entities to gridpane 
     */
    @FXML
    public GridPane initialize() {
        Image ground = new Image("/dirt_0_new.png");
        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities) {
        	squares.getChildren().add(entity);
        }

        return squares;
        
    }
    
    /**
     * Removes entities from list if they become invisible
     */
    public void remove () {
    	List<Entity> removeEntities = new ArrayList<>();
    	
    	for (Entity e : dungeon.getEntities()) {
    		if (e.getState() == Status.INVISIBLE) {
    			if (e instanceof Player) { alive = false; }
    			RemoveFromScreen(e.getX(), e.getY());
    			if (e instanceof Door) {ReplaceDoor(e.getX(), e.getY());}
    			removeEntities.add(e);
    		} 
    	}
    	dungeon.getEntities().removeAll(removeEntities);
    }
    
    /**
     * 
     * @param row remove entities from screen 
     * @param col
     */
    public void RemoveFromScreen(int row, int col) {
    	for (Node node : squares.getChildren()) {
        	for (ImageView remove : toRemove) {
        		if (alive && node != null && remove.equals(node) && GridPane.getRowIndex(node) == col && GridPane.getColumnIndex(node) == row) {
        			remove.setImage(null);
        		} else if (!alive) {
        			for (ImageView pa : p) { pa.setImage(null); }
        		}
        	}
        }
    }
    
    /**
     * Replace closed door with an open door
     * @param row
     * @param col
     */
    public void ReplaceDoor(int row, int col) {
    	Image image = new Image("/open_door.png");
    	squares.add(new ImageView(image), row, col);
    }
    

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {

        if (player.getStat() == Status.ALIVE) {
        	
            switch (event.getCode()) {
            case UP:
                player.moveUp();
                if (player.isPushingBoulder() && player.isMoving()) {
                	System.out.print("Player is pushing boulder here");
                	player.getCurrentBoulder().moveUp(dungeon, player);
                	player.setPushingBoulder(false);
                	player.setCurrentBoulder(null);
                }
                //finished = dungeon.finalGoalCheck();
                //dungeon.moveAllEnemies(dungeon.findEnemy());
                break;
            case DOWN:
                player.moveDown();
                if (player.isPushingBoulder() && player.isMoving()) {
                	player.getCurrentBoulder().moveDown(dungeon, player);
                	player.setPushingBoulder(false);
                	player.setCurrentBoulder(null);
                }   
                //finished = dungeon.finalGoalCheck();
                //dungeon.moveAllEnemies(dungeon.findEnemy());
                break;
            case LEFT:
                player.moveLeft();
                if (player.isPushingBoulder() && player.isMoving()) {
                	player.getCurrentBoulder().moveLeft(dungeon, player);
                	player.setPushingBoulder(false);
                	player.setCurrentBoulder(null);
                }
                //finished = dungeon.finalGoalCheck();
                //dungeon.moveAllEnemies(dungeon.findEnemy());
                break;
            case RIGHT:
                player.moveRight();
                if (player.isPushingBoulder() && player.isMoving()) {
                	player.getCurrentBoulder().moveRight(dungeon, player);
                	player.setPushingBoulder(false);
                	player.setCurrentBoulder(null);
                }
                //finished = dungeon.finalGoalCheck();
                //dungeon.moveAllEnemies(dungeon.findEnemy());
                break;
            case ENTER:
            	// goals and inventory pop up
            	statusView (event);
            default:
                break;
            }
            boolean finished = false;
            finished = dungeon.finalGoalCheck();
            if (finished) {            	
            	winningView(event);
            } 
            else if (player.getStat().equals(Status.DECEASED)) {
            	losingView(event);
            }
            
            remove();
        }
        
                
    }
    
    public void winningView(KeyEvent event) throws IOException {
    	
    	System.out.print("YOU WON");
    	WinningController controller = new WinningController(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("winningView.fxml"));
    	
        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        Scene scene = new Scene(root);
		//System.out.print("This is being called");
		primaryStage.setTitle("Game");
		primaryStage.setScene(scene);
		//primaryStage.initOwner(button1.getScene().getWindow());
		primaryStage.show();
    }
    
    public void losingView(KeyEvent event) throws IOException {
    	
    	WinningController controller = new WinningController(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("losingView.fxml"));
    	
        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        Scene scene = new Scene(root);
		//System.out.print("This is being called");
		primaryStage.setTitle("Game");
		primaryStage.setScene(scene);
		//primaryStage.initOwner(button1.getScene().getWindow());
		primaryStage.show();
    }
    
    
    public void statusView (KeyEvent event) throws IOException {
    	
    	Stage popUpStage = new Stage();
    	PopUpController controller = new PopUpController(primaryStage, dungeon.getGoalList(), player.getCollected());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("popUpView.fxml"));
    	
        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        Scene scene = new Scene(root);
        //controller.setStage(popUpStage);
        popUpStage.setScene(scene);
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(squares.getScene().getWindow());
        popUpStage.showAndWait();
    	
    }

}

