package controller;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;
import view.PongMenuMessages;

/**
 * The PreGameController class contains the logic for pre-game screen operations
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class PreGameController {

	private AnchorPane appLaunchView; // Represents the application launch screen
	private AnchorPane preGameView; // Represents the pre-game screen
	private AnchorPane pongGameView; // Represents the in-game screen
	private AnchorPane loginView; // Represents the login screen
	private AnchorPane createAcctView; // Represents the create account screen
	private Button menuBtn; // Represents the button to go back to the launch screen
	private Button startBtn; // Represents the button to start the game
	private Text welcomeMsg; // Represents the message to welcome the user
	private Text numWinsMsg; // Represents the message to display the user's win count
	private User user; // Represents the user's profile
	private DataController data; // Represents the DataController (to save data upon exit)
	private ArrayList<User> users; // Represents the users ArrayList (contains the users database)
	private PongMenuMessages message;
	
	private Runnable onGameStart; // TODO

	
	/**
	 * The PreGameController constructor initiates all pane objects for the pre-game page, 
	 * as well as instances of ArrayList of User, and DataController to manage saving of said ArrayList
	 * 
	 * @param appLaunchView
	 * @param preGameView
	 * @param pongGameView
	 * @param loginView
	 * @param createAcctView
	 * @param menuBtn
	 * @param startBtn
	 * @param welcomeMsg
	 * @param numWinsMsg
	 * @param user
	 * @param data
	 * @param users
	 */
	public PreGameController(
			AnchorPane appLaunchView, AnchorPane preGameView, AnchorPane pongGameView, AnchorPane loginView, 
			AnchorPane createAcctView, Button menuBtn, Button startBtn, Text welcomeMsg, Text numWinsMsg, 
			User user, DataController data, ArrayList<User> users) {
		this.appLaunchView = appLaunchView;
		this.preGameView = preGameView;
		this.pongGameView = pongGameView;
		this.loginView = loginView;
		this.createAcctView = createAcctView;
		this.menuBtn = menuBtn;
		this.startBtn = startBtn;
		this.welcomeMsg = welcomeMsg;
		this.numWinsMsg = numWinsMsg;
		this.user = user;
		this.data = data;
		this.users = users;
	}
	
	
	/**
	 * The getLoginScreenInput method contains the handling methods for the back button and login button
	 */
	public void getPreGameScreenInput() {
		setMessages(); // Welcome the user back and display their win count
		backToStartScreenBtnHandler(); // Back button handler
		startPongGameBtnHandler(); // Start game handler
	}
	
	
	/**
	 * The setMessages method sets a user-specific welcome back message and win count message
	 */
	private void setMessages() {
		welcomeMsg.setText(message.welcomeMsg(user.getUsername().toUpperCase()));
		numWinsMsg.setText(message.winCountMsg(user.getNumPongWins() + ""));
	}
	
	
	/**
	 * The backToStartscreenBtnHandler method alters AnchorPane visibility so as to return the user to the app's launch screen
	 */
	private void backToStartScreenBtnHandler() {
		menuBtn.setOnMouseClicked(e -> {
			data.saveUser(user, users); // Save the user's updated data into the database upon pre-game screen exit
			
			preGameView.setVisible(false);
			loginView.setVisible(false);
			createAcctView.setVisible(false);
			appLaunchView.setVisible(true); // Open launcher screen
		});
	}
	
	
	/**
	 * The startPongGameBtnHandler method listens for the "Start Game" button press <br>
	 * Once clicked, the pong game screen is opened and the game logic "container" runs
	 */
	private void startPongGameBtnHandler() {
		startBtn.setOnMouseClicked(e -> {
			loginView.setVisible(false);
			createAcctView.setVisible(false);
			preGameView.setVisible(false);
			pongGameView.setVisible(true); // Open Pong game
			
			// Trigger game logic "container"
			if (onGameStart != null) {
				onGameStart.run();
			}
		});
	}
	
	
	/**
	 * The setOnGameStart method instantiates onGameStart
	 * 
	 * @param onGameStart
	 */
	public void setOnGameStart(Runnable onGameStart) {
		this.onGameStart = onGameStart;
	}
	
	
	/**
	 * The updateWinCount method increments the user's win count by 1
	 */
	public void updateWinCount() {
		user.setNumPongWins(user.getNumPongWins() + 1); // Add one win to the user's profile
		setMessages(); // Adjust the UI to reflect the updated win count
	}
	
}
