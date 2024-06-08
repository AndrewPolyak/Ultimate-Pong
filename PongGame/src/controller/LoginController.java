package controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
	private TextField username;
	private TextField password;
	
	
	/**
	 * TODO
	 * 
	 * @param backToStartScreenBtn
	 * @param loginView
	 * @param appLaunchView
	 * @param username
	 * @param password
	 */
	public LoginController(Button backToStartScreenBtn, AnchorPane loginView, AnchorPane appLaunchView, TextField username, TextField password) {
		this.backToStartScreenBtn = backToStartScreenBtn;
		this.loginView = loginView;
		this.appLaunchView = appLaunchView;
		this.username = username;
		this.password = password;
	}
	
	
	/**
	 * 
	 */
	public void getLoginScreenInput() {
		backToStartScreenBtnHandler();
		
	}
	

	/**
	 * 
	 */
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			loginView.setVisible(false);
			appLaunchView.setVisible(true);
		});
	}
	
}
