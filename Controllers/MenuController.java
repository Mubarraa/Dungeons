package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;


public class MenuController {
	
    
	private String title;	
	private Stage primaryStage;
	private Scene scene;
	private Button button1;
	
	
	public MenuController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
		
	}	

	/**
	 * 
	 * @param event if Instructions button is clicked, it will show instructions page 
	 * @throws IOException
	 */
	@FXML
    public void viewInstructions(ActionEvent event) throws IOException {
		System.out.println("Instructions");
		
		InstructionsController controller = new InstructionsController(primaryStage);
		
		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructionsView.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        scene = new Scene(root);
		//System.out.print("This is being called");
		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		//primaryStage.initOwner(button1.getScene().getWindow());
		primaryStage.show();
	}
	
	/**
	 * 
	 * @param event if Map is selected, options for several maps will be displayed
	 * @throws IOException
	 */
	@FXML
	public void chooseMap(MouseEvent event) throws IOException{

		System.out.println("Maps");	
		
		MapSelectorController controller = new MapSelectorController(primaryStage);

		
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mapSelectView.fxml"));

        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        scene = new Scene(root);
		primaryStage.setTitle(title);
	    primaryStage.setScene(scene);
	    primaryStage.show();
		
		
	}
	
}
