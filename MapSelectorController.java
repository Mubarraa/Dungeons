package unsw.dungeon;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class MapSelectorController {

	
    @FXML
    private ListView<File> mapListView;
	private String title;	
	private Stage primaryStage;
	private Scene scene;
	private Button button1;
	
	
	public MapSelectorController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;		
	}
	
	/**
	 * Displays list of maps 
	 */
    public void initialize() {
		
        mapListView.setOnMouseClicked(event -> {
            // load map if double clicked
            if(event.getClickCount() == 2){
                try {
					onPlayButtonClicked();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        File dir = new File("dungeons");
        File[] files = dir.listFiles();
        
        for (File file: files) {
            mapListView.getItems().add(file);
        }
    
    }
     
    /**
     * 
     * @throws IOException Displays the actual game 
     */
    private void onPlayButtonClicked() throws IOException {
		// TODO Auto-generated method stub
		
		File file = mapListView.getSelectionModel().getSelectedItem();
		
		String file_name = file.getName();
		
		DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(file_name, this.primaryStage);
		
		DungeonController controller = dungeonLoader.loadController();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
		    loader.setController(controller);
		    Parent root = loader.load();
		    Scene scene = new Scene(root);
		    root.requestFocus();
		    primaryStage.setScene(scene);
		    primaryStage.show();
			
	}
	
    
}
