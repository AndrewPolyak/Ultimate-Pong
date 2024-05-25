module PongGame {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
    
    // Open the controller package to javafx.fxml
    opens controller to javafx.fxml;

    // Export the main application package
    exports application;
}
