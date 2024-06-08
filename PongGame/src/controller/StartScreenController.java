package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * The StartScreenController class contains the logic for navigating the start screen of the application
 * 
 * @author Andrew Polyak
 * @version June 8, 2024
 */
public class StartScreenController {

	private Button login;
	private Button createAccount;
	private AnchorPane appLaunchView;
	private AnchorPane loginView;
	private AnchorPane createAcctView;
	
	
	/**
	 * The StartScreenController constructor initializes all relevant JavaFX objects on the start screen + loginView & createAcctView
	 * 
	 * @param login
	 * @param createAccount
	 * @param appLaunchView
	 * @param loginView
	 * @param createAcctView
	 */
	public StartScreenController(Button login, Button createAccount, AnchorPane appLaunchView, AnchorPane loginView, AnchorPane createAcctView) {
		this.login = login;
		this.createAccount = createAccount;
		this.appLaunchView = appLaunchView;
		this.loginView = loginView;
		this.createAcctView = createAcctView;
	}
	
	
	/**
	 * The getStartScreenInput method contains the action handler methods for the "login" and "create account" buttons
	 */
	public void getStartScreenInput() {
		loginBtnHandler();
		createAcctBtnHandler();
	}
	
	
	/**
	 * The loginBtnHandler method opens loginView once the login button is clicked
	 */
	private void loginBtnHandler() {
		login.setOnMouseClicked(e -> {
			appLaunchView.setVisible(false);
			loginView.setVisible(true);
		});
	}
	
	
	/**
	 * The createAcctBtnHandler method opens createAcctView once the create account button is clicked
	 */
	private void createAcctBtnHandler() {
		createAccount.setOnMouseClicked(e -> {
			appLaunchView.setVisible(false);
			createAcctView.setVisible(true);
		});
	}
}
