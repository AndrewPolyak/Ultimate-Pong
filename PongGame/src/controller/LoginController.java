package controller;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;
import view.LoginMenuMessages;

/**
 * LoginController handles user interactions during the login process
 * 
 * @author Andrew Polyak
 * @version June 18, 2024
 */
public class LoginController {

	private Button backToStartScreenBtn; // Button to return to the start menu
	private AnchorPane loginView; // View of the login page
	private AnchorPane appLaunchView; // View of the app's launch page (page before login page)
	private AnchorPane preGameView; // View of the app's pre-game page (page after login page)
	private TextField usernameField; // Text field containing the username input
	private TextField passwordField; // Text field containing the password input
	private Button loginBtn; // Button to submit user credentials to go to pre-game page
	private Text validationMsg; // A message that appears if the user credentials don't match any in the records
	
	private LoginMenuMessages message; // Contains dynamic menu validation messages
	
	private String username = "";
	private String password = "";
	
	private ArrayList<User> users; // Represents the database of user credentials
	private User user;
	
	private Runnable onLoginSuccess; // Represents a Runnable instance which runs a new pre-game menu
	
	
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
		this.message = new LoginMenuMessages();
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
			
			// Clear the input fields
			usernameField.clear();
			passwordField.clear();
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
			if (credentialsExistInDb(user.getUsername(), user.getPassword())) { // If the credentials match a database item, log them in
				// Clear the input fields
				usernameField.clear();
				passwordField.clear();
				
				this.user = user;
				
				validationMsg.setVisible(false);
				appLaunchView.setVisible(false);
				preGameView.setVisible(true); // Open pre-game screen

				// If setOnLoginSuccess has been called, run onLoginSuccess
				if (onLoginSuccess != null) {
					onLoginSuccess.run();
				}
				
			} else if (username.length() == 0 && password.length() == 0) { // If both fields are empty
				validationMsg.setText(message.fieldsBlankMsg()); // Inform user that they need to enter values into the fields
				validationMsg.setVisible(true);
				
			} else if (username.length() == 0) { // If the username field is blank
				validationMsg.setText(message.usernameBlankMsg()); // Inform user that the username field must be filled in
				validationMsg.setVisible(true);
				
			} else if (password.length() == 0) { // If the password field is blank
				validationMsg.setText(message.passwordBlankMsg()); // Inform user to fill in the password field
				validationMsg.setVisible(true);
				
			} else {
				validationMsg.setText(message.AccountDoesNotExistMsg()); // Inform user that the entered credentials don't match an existing account
				validationMsg.setVisible(true); // Inform user that the entered credentials don't match an account
			}
		}
	}
	
	
	/**
	 * The credentialsExistInDb method returns true if the user-entered credentials match a database item, false otherwise
	 * 
	 * @param enteredUsername
	 * @param enteredPassword
	 * @return
	 */
	private boolean credentialsExistInDb(String enteredUsername, String enteredPassword) {
		return enteredUsername.toLowerCase().trim().equals(username.toLowerCase().trim()) && 
				enteredPassword.trim().equals(password.trim());
	}
	
	
	/**
	 * @return 
	 */
	public User getUser() {
		return user;
	}
	
	
	/**
	 * The setOnLoginSuccess method instantiates onLoginSuccess
	 * 
	 * @param onLoginSuccess
	 */
	public void setOnLoginSuccess(Runnable onLoginSuccess) {
		this.onLoginSuccess = onLoginSuccess;
	}
	
}
