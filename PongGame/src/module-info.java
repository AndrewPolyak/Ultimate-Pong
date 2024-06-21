module PongGame {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.logging;
    
    // Open the controller package to javafx.fxml
    opens controller to javafx.fxml;

    // Export the main application package
    exports application;
}
