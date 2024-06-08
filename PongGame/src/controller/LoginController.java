package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * LoginController handles user interactions during the login process, including user authentication and account creation.
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class LoginController {

	
	private Button backToStartScreenBtn;
	private AnchorPane loginView;
	private AnchorPane appLaunchView;
	
	public LoginController(Button backToStartScreenBtn, AnchorPane loginView, AnchorPane appLaunchView) {
		
		
	}
	
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			loginView.setVisible(false);
			appLaunchView.setVisible(true);
		});
	}
	
}
