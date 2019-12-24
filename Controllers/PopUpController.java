package unsw.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PopUpController {
	
    @FXML
    private ListView<String> goalList;
    private ListView<String> collectedList;
    List<Goal> goals;
    Collectables collected;
	private String title;	
	private Stage primaryStage;
	private Scene scene;
	private Stage stage;

	public PopUpController(Stage primaryStage, List<Goal> goals, Collectables collected) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		this.goals = goals;
		this.collected = collected;
	}
	
    public void initialize() {
	
        goalList.getItems().add("GOALS");
        for (Goal goal: goals) {
        	if (goal instanceof ComplexGoal) {
        		List<Goal> G = ((ComplexGoal) goal).getGoals();
        		String n = String.valueOf(((ComplexGoal) goal).getType());
				checkingComplexType(n);
        		for (Goal i : G) {
        			if (i instanceof ComplexGoal) {
        				List<Goal> j = ((ComplexGoal) i).getGoals();
        				n = String.valueOf(((ComplexGoal) i).getType());
        				if (n.equals("false")) {
        					goalList.getItems().add("And one of these goals below must be completed");
        				} else if (n.equals("true")) {
        					goalList.getItems().add("And all these goals below must be completed");
        				}
                		String complete = "";
        				for (Goal p : j) {
        					complete = complete + p + " ";
                		}
        				goalList.getItems().add(complete);
        			} else {
        				goalList.getItems().add(i.toString());
        			}
        		}
        	} else {
        		goalList.getItems().add(goal.toString());
        	}
        }
        
        goalList.getItems().add("COLLECTED");
        if (collected.getallEnt().size() == 0) {
        	goalList.getItems().add("No items collected");
        } else {
        	String same = "";
	        for (Entity e: collected.getallEnt()) {
	        	if (same.equals(e.toString())) continue;
	        	if (e.toString().equals("Key")) {
	        		String complete = e.toString() + ": " + String.valueOf(collected.getKeyCount());
		        	goalList.getItems().addAll(complete);
	        	} else if (e.toString().equals("Sword")) {
	        		String complete = e.toString() + ": " + String.valueOf(collected.getSwordCount() +"/5");
	        		goalList.getItems().addAll(complete);
	        	} else if (e.toString().equals("Enemy")) {
	        		String complete = e.toString() + ": " + String.valueOf(collected.getEnemyCount());
	        		goalList.getItems().addAll(complete);
	        	} else if (e.toString().equals("Treasure")) {
	        		String complete = e.toString() + ": " + String.valueOf(collected.getTreasureCount());
	        		goalList.getItems().addAll(complete);
	        	}
	        	same = e.toString();
	        }
        }
       
        
    }

	public void checkingComplexType(String n) {
		if (n.equals("false")) {
			goalList.getItems().add("OR: One of these goals below must be completed");
		} else if (n.equals("true")) {
			goalList.getItems().add("AND: All these goals below must be completed");
		}
	}
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

	
}
