package controller;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;

/**
 * LoginController handles user interactions during the account creation process
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class RegisterController {

	private Button backToStartScreenBtn; // Button to return to the start menu
	private AnchorPane registerView; // View of the registration page
	private AnchorPane appLaunchView; // View of the app's launch page (page before login page)
	private AnchorPane preGameView; // View of the app's pre-game page (page after login page)
	private TextField usernameField; // Text field containing the username input
	private TextField passwordField; // Text field containing the password input // TODO find out how to hide characters
	private Button createAcctBtn; 
	private Text validationMsg; // A message that appears if the user credentials don't match any in the records
	
	private String username = "";
	private String password = "";
	
	private ArrayList<User> users; // Represents the database of user credentials
	
	private User newUser;
	
	private int NEW_USER_NUM_WINS = 0; // The user will start with 0 wins
	
	private int USERNAME_MIN_CHARS = 3; // The minimum character count of the username is 3
	private int USERNAME_MAX_CHARS = 25; // The maximum character count of the username is 25
	
	private Runnable onRegisterSuccess; // TODO
	
	
	/**
	 * The RegisterController constructor initiates all pane objects for the createAcctView page
	 * 
	 * @param backToStartScreenBtn
	 * @param registerView
	 * @param appLaunchView
	 * @param preGameView
	 * @param usernameField
	 * @param passwordField
	 * @param createAcctBtn
	 * @param users
	 * @param validationMsg
	 */
	public RegisterController(Button backToStartScreenBtn, AnchorPane registerView, AnchorPane appLaunchView, AnchorPane preGameView, TextField usernameField, TextField passwordField, Button createAcctBtn, ArrayList<User> users, Text validationMsg) {
		this.backToStartScreenBtn = backToStartScreenBtn;
		this.registerView = registerView;
		this.appLaunchView = appLaunchView;
		this.preGameView = preGameView;
		this.usernameField = usernameField;
		this.passwordField = passwordField;
		this.createAcctBtn = createAcctBtn;
		this.users = users;
		this.validationMsg = validationMsg;
	}
	
	
	/**
	 * The getRegisterScreenInput method contains the handling methods for the back button and create account button
	 */
	public void getRegisterScreenInput() {
		backToStartScreenBtnHandler(); // Back button handler
		collectNewCredentials(); // Account create & login attempt handler
	}
	
	
	/**
	 * The backToStartscreenBtnHandler method alters AnchorPane visibility so as to return the user to the app's launch screen
	 */
	private void backToStartScreenBtnHandler() {
		backToStartScreenBtn.setOnMouseClicked(e -> {
			validationMsg.setVisible(false);
			registerView.setVisible(false);
			appLaunchView.setVisible(true); // Open launcher screen
		});
	}
	
	
	/**
	 * The collectNewCredentials method collects the user-entered username and password and calls the validUsername method <br>
	 * If the new credentials are valid, then AnchorPane visibility is altered so as to take the user to the preGameView screen, otherwise an error message is displayed
	 */
	private void collectNewCredentials() {
		createAcctBtn.setOnMouseClicked(e -> {
			username = usernameField.getText();
			password = passwordField.getText();
			
			if (validUsername(username) && password.length() > 0) { // If both username and password are valid
				
				// Create new User object with the user's details and add it to the users ArrayList
				newUser = new User(username, password, NEW_USER_NUM_WINS);
				users.add(newUser);
				
				if (onRegisterSuccess != null) {
					onRegisterSuccess.run();
				}
				
				validationMsg.setVisible(false);
				appLaunchView.setVisible(false);
				preGameView.setVisible(true); // Open pre-game screen
			} else {
				validationMsg.setVisible(true); // Inform user that the entered username is invalid
			}
		});
	}
	
	
	/**
	 * The validUsername method checks whether the provided username's length is between 3 and 25 characters (the valid length range)
	 * 
	 * @param username
	 * @return true if the username is of a valid length, false otherwise
	 */
	private boolean validUsername(String username) {
		if (username.length() >= USERNAME_MIN_CHARS && username.length() <= USERNAME_MAX_CHARS) { // If username is valid length
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * @return users
	 */
	public ArrayList<User> getUsers() {
		return users;
	}
	
	
	/**
	 * @return 
	 */
	public User getNewUser() {
		return newUser;
	}
	
	
	
	/**
	 * TODO
	 * 
	 * @param onRegisterSuccess
	 */
	public void setOnRegisterSuccess(Runnable onRegisterSuccess) {
		this.onRegisterSuccess = onRegisterSuccess;
	}
	
}
