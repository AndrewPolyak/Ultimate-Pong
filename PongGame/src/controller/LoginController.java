package controller;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;

/**
 * LoginController handles user interactions during the login process
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class LoginController {

	private Button backToStartScreenBtn; // Button to return to the start menu
	private AnchorPane loginView; // View of the login page
	private AnchorPane appLaunchView; // View of the app's launch page (page before login page)
	private AnchorPane preGameView; // View of the app's pre-game page (page after login page)
	private TextField usernameField; // Text field containing the username input
	private TextField passwordField; // Text field containing the password input // TODO find out how to hide characters
	private Button loginBtn; // Button to submit user credentials to go to pre-game page
	private Text validationMsg; // A message that appears if the user credentials don't match any in the records
	
	private String username = "";
	private String password = "";
	private String numPongWins;
	
	private ArrayList<User> users; // Represents the database of user credentials
	
	private Runnable onLoginSuccess;
	
	
	/**
	 * The LoginController constructor initiates all pane objects for the loginView page
	 * 
	 * @param backToStartScreenBtn
	 * @param loginView
	 * @param appLaunchView
	 * @param preGameView
	 * @param usernameField
	 * @param passwordField
	 * @param loginBtn
	 * @param users
	 * @param validationMsg
	 */
	public LoginController(Button backToStartScreenBtn, AnchorPane loginView, AnchorPane appLaunchView, AnchorPane preGameView, TextField usernameField, TextField passwordField, Button loginBtn, ArrayList<User> users, Text validationMsg) {
		this.backToStartScreenBtn = backToStartScreenBtn;
		this.loginView = loginView;
		this.appLaunchView = appLaunchView;
		this.preGameView = preGameView;
		this.usernameField = usernameField;
		this.passwordField = passwordField;
		this.loginBtn = loginBtn;
		this.users = users;
		this.validationMsg = validationMsg;
	}
	
	
	/**
	 * The getLoginScreenInput method contains the handling methods for the back button and login button
	 */
	public void getLoginScreenInput() {
		backToStartScreenBtnHandler(); // Back button handler
		collectCredentials(); // Login attempt handler
	}
	

	/**
	 * The backToStartscreenBtnHandler method alters AnchorPane visibility so as to return the user to the app's launch screen
	 */
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			validationMsg.setVisible(false);
			loginView.setVisible(false);
			appLaunchView.setVisible(true); // Open launcher screen
		});
	}
	
	
	/**
	 * The collectCredentials method collects the user-entered username and password and calls the validateCredentials method
	 */
	private void collectCredentials() {
		loginBtn.setOnMouseClicked(e -> {
			username = usernameField.getText();
			password = passwordField.getText();
			validateCredentials();
		});
	}
	
	
	/**
	 * The validateCredentials method sorts through the users ArrayList to determine whether the user-entered credentials exist <br>
	 * If the credentials match, then AnchorPane visibility is altered so as to take the user to the preGameView screen, otherwise an error message is displayed
	 */
	private void validateCredentials() {
		for (User user : users) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password)) { // If the credentials match a database item
				numPongWins = user.getNumPongWins() + ""; // Retrieve the number of pong wins from the user
				
				validationMsg.setVisible(false);
				appLaunchView.setVisible(false);
				preGameView.setVisible(true); // Open pre-game screen
				
				if (onLoginSuccess != null) {
					onLoginSuccess.run();
				}
				
				return;
			} else {
				validationMsg.setVisible(true); // Inform user that the entered credentials don't match an account
			}
		}
	}
	
	
	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return TODO
	 */
	public String getNumPongWins() {
		return numPongWins;
	}
	
	
	/**
	 * TODO
	 * 
	 * @param onLoginSuccess
	 */
	public void setOnLoginSuccess(Runnable onLoginSuccess) {
		this.onLoginSuccess = onLoginSuccess;
	}
	
}
