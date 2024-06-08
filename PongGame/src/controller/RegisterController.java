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
	private AnchorPane registerView;
	private AnchorPane appLaunchView;
	
	public RegisterController(Button backToStartScreenBtn, AnchorPane registerView, AnchorPane appLaunchView) {
		this.backToStartScreenBtn = backToStartScreenBtn;
		this.registerView = registerView;
		this.appLaunchView = appLaunchView;
		
	}
	
	
	public void getRegisterScreenInput() {
		backToStartScreenBtnHandler();
	}
	
	
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			registerView.setVisible(false);
			appLaunchView.setVisible(true);
		});
	}
	
}
