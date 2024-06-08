package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class RegisterController {
	// username should be max 25 characters
	
	
	private Button backToStartScreenBtn;
	private AnchorPane loginView;
	private AnchorPane appLaunchView;
	
	public RegisterController(Button backToStartScreenBtn, AnchorPane loginView, AnchorPane appLaunchView) {
		
		
	}
	
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			loginView.setVisible(false);
			appLaunchView.setVisible(true);
		});
	}
	
}
