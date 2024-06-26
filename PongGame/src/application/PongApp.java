package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * The PongApp class launches the application
 * 
 * @author Andrew Polyak
 * @version June 21, 2024
 */
public class PongApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/AppView.fxml")); // Define the root as the AppView file
			Scene scene = new Scene(root,1000,650); // Set the scene for the root, displaying it at 1000x650
			primaryStage.setScene(scene);
			primaryStage.show(); // Display the GUI
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args); // Run application
	}
}
