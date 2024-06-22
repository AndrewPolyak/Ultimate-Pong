package controller;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;
import view.RegisterMenuMessages;

/**
 * LoginController handles user interactions during the account creation process
 * 
 * @author Andrew Polyak
 * @version June 18, 2024
 */
public class RegisterController {

	private Button backToStartScreenBtn; // Button to return to the start menu
	private AnchorPane registerView; // View of the registration page
	private AnchorPane appLaunchView; // View of the app's launch page (page before login page)
	private AnchorPane preGameView; // View of the app's pre-game page (page after login page)
	private TextField usernameField; // Text field containing the username input
	private TextField passwordField; // Text field containing the password input
	private Button createAcctBtn; 
	private Text validationMsg; // A message that appears if the user credentials don't match any in the records
	
	private String username = "";
	private String password = "";
	
	private ArrayList<User> users; // Represents the database of user credentials
	
	private User newUser;
	
	private RegisterMenuMessages message; // Contains dynamic menu validation messages
	
	private int NEW_USER_NUM_WINS = 0; // The user will start with 0 wins
	
	private int USERNAME_MIN_CHARS = 3; // The minimum character count of the username is 3
	private int USERNAME_MAX_CHARS = 25; // The maximum character count of the username is 25
	
	private Runnable onRegisterSuccess; // Represents a Runnable instance which runs a new pre-game menu
	
	
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
		this.message = new RegisterMenuMessages();
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
			
			// Clear the input fields
			usernameField.clear();
			passwordField.clear();
		});
	}
	
	
	/**
	 * The collectNewCredentials method collects the user-entered username and password and calls the validUsername method <br>
	 * If the new credentials are valid, then AnchorPane visibility is altered so as to take the user to the preGameView screen, otherwise an error message is displayed
	 */
	private void collectNewCredentials() {
		createAcctBtn.setOnMouseClicked(e -> {
			boolean userIdExists = false;
			
			username = usernameField.getText();
			password = passwordField.getText();
			
			// Check if the user-entered username matches an existing username
			for (User user : users) {
				if (usernameExistInDb(user.getUsername())) {
					userIdExists = true;
				}
			}
			
			if (validUsername(username) && password.length() > 0 && !userIdExists) { // If both username and password are valid, create the account
				// Clear the input fields
				usernameField.clear();
				passwordField.clear();
				
				// Create new User object with the user's details and add it to the users ArrayList
				newUser = new User(username, password, NEW_USER_NUM_WINS);
				users.add(newUser);
				
				// If setOnRegisterSuccess has been called, run onRegisterSuccess
				if (onRegisterSuccess != null) {
					onRegisterSuccess.run();
				}
				
				validationMsg.setVisible(false);
				appLaunchView.setVisible(false);
				preGameView.setVisible(true); // Open pre-game screen
				
			} else if (username.length() == 0 && password.length() == 0) { // If both fields are empty
				validationMsg.setText(message.fieldsBlankMsg()); // Inform user that they need to enter values into the fields
				validationMsg.setVisible(true);
				
			} else if (username.length() == 0) { // If the username field is blank
				validationMsg.setText(message.usernameBlankMsg()); // Inform user that the username field must be filled in
				validationMsg.setVisible(true);
				
			} else if (password.length() == 0) { // If the password field is blank
				validationMsg.setText(message.passwordBlankMsg()); // Inform user that the password field must be filled in
				validationMsg.setVisible(true);
				
			} else if (userIdExists) { // If the user has entered a username that already exists
				validationMsg.setText(message.nameAlreadyExistsMsg()); // Inform user that the entered username is invalid due to it already existing
				validationMsg.setVisible(true);
				
			} else {
				validationMsg.setText(message.nameLengthMsg()); // Inform user that the entered username is invalid due to length
				validationMsg.setVisible(true);
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
	 * The usernameExistInDb method returns true if the user-entered username matches a database item, false otherwise
	 * 
	 * @param enteredUsername
	 * @return
	 */
	private boolean usernameExistInDb(String enteredUsername) {
		return enteredUsername.toLowerCase().trim().equals(username.toLowerCase().trim());
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
	 * The setOnRegisterSuccess method instantiates onRegisterSuccess
	 * 
	 * @param onRegisterSuccess
	 */
	public void setOnRegisterSuccess(Runnable onRegisterSuccess) {
		this.onRegisterSuccess = onRegisterSuccess;
	}
	
}
