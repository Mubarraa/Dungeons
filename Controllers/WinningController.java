package unsw.dungeon;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WinningController {
	
    @FXML
    private Button button;
	private Stage primaryStage;
	
	WinningController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	/**
	 * 
	 * @param event For the winning event, a button will also be displayed on screen to return back to menu
	 * @throws IOException
	 */
	@FXML
	public void backToMenu(ActionEvent event) throws IOException {
		
		System.out.println("changing screens");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("menuView.fxml"));
	    MenuController menu = new MenuController(this.primaryStage);
        
	    loader.setController(menu);
        Parent root = loader.load();
        root.requestFocus();
        Scene scene = new Scene(root);
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
        System.out.println("switched");
		
	}
	
}
