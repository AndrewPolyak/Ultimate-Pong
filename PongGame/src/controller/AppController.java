package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.User;

/**
 * The AppController class manages the entire application's execution flow
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class AppController implements Initializable {

	@FXML
    private Button PreGameMenuBtn; // Represents a button in the pre-game screen which allows the user to return to the start screen

    @FXML
    private AnchorPane appLaunchPane; // Represents the sub-view of the start screen

    @FXML
    private AnchorPane appLaunchView; // Represents the view of the start screen

    @FXML
    private Button backToMenuBtn; // Represents a button in the pong post-game window which allows the user to return to the pre-game screen

    @FXML
    private Button backToStartScreenBtn1; // Represents a button in the create account screen which allows the user to return to the start screen

    @FXML
    private Button backToStartScreenBtn2; // Represents a button in the login screen which allows the user to return to the start screen

    @FXML
    private Circle ball; // Represents the ball in the pong game

    @FXML
    private AnchorPane createAcctPane; // Represents the sub-view of the create account screen

    @FXML
    private Button createAcctBtn; // Represents a button in the create account screen which allows the user to create a new account

    @FXML
    private Button createAcctMenuBtn; // Represents a button on the start screen which allows the user to go to the create account screen

    @FXML
    private Text createAcctValidationMsg; // Represents a message displayed to the user in the create account screen if the username format is invalid

    @FXML
    private AnchorPane createAcctView; // Represents the view of the create account screen

    @FXML
    private TextField existingPassword; // Represents the password field within the login screen

    @FXML
    private TextField existingUsername; // Represents the username field within the login screen

    @FXML
    private AnchorPane gameEndPane; // Represents the pop-up window displayed after a pong game

    @FXML
    private Text gameResultMsg; // Represents the text informing the player if they won or lost within the pong post-game window

    @FXML
    private Button logInBtn; // Represents a button in the login screen which allows the user to submit their credentials to login

    @FXML
    private Button logInMenuBtn; // Represents a button on the start screen which allows the user to go to the login screen

    @FXML
    private AnchorPane logInPane; // Represents the sub-view of the login screen

    @FXML
    private Text logInValidationMsg; // Represents a message displayed to the user in the login screen if the credentials don't match an account

    @FXML
    private AnchorPane logInView; // Represents the view of the login screen

    @FXML
    private TextField newPassword; // Represents the password field within the create account screen

    @FXML
    private TextField newUsername; // Represents the username field within the create account screen

    @FXML
    private Text numWinsMsg; // Represents the display of the user's win count within the pre-game screen

    @FXML
    private Rectangle oppPaddle; // Represents the opponent's paddle in the pong game

    @FXML
    private Text oppScore; // Represents the opponent's score in the pong game

    @FXML
    private Button playAgainBtn; // Represents a button in the pong post-game window that allows the user to play again

    @FXML
    private Button playGameBtn; // Represents a button in the pre-game screen which allows the user to play pong

    @FXML
    private Button plyForfeit; // Represents a button in the pong game view which allows the user to forfeit the game and return to the pre-game view

    @FXML
    private Text plyName; // Represents the player's name within the pong game

    @FXML
    private Rectangle plyPaddle; // Represents the player's paddle in the pong game

    @FXML
    private Text plyScore; // Represents the player's score in the pong game

    @FXML
    private AnchorPane pongGameView; // Represents the pong game screen

    @FXML
    private AnchorPane preGameView; // Represents the pre-game screen

    @FXML
    private Text welcomeMsg; // Represents the welcome message in the pre-game screen
    
    private StartScreenController startUpScreen; // Represents an instance of StartScreenController
    private LoginController loginScreen; // Represents an instance of LoginController
    private RegisterController registerScreen; // Represents an instance of RegisterController
    private PreGameController preGameScreen; // Represents an instance of PreGameController
    private PongGameController playPong; // Represents an instance of PongGameController
    private PongMenuController navPong; // Represents an instance of PongMenuController
    
    private DataController data; // Represents an instance of DataController (to manage loading data)
    private ArrayList<User> users; // Represents the ArrayList of User objects (all users in the database)
    
    /*
     * TODO LIST
     * 
     * DONE - 1. COMPLETE DOCUMENTATION
     * 2. ADD LOGGER
     * 3. IMPROVE PRE-GAME UI (i.e., add game name at top for decoration, change "Menu" button to "Save & Quit"
     * 4. FIX PONG GAMEPLAY BOUNCING BUG
     * 5. FIND OUT HOW TO HIDE PASSWORD FIELD CHARACTERS
     * 6. CLEAR REGISTER / LOGIN FIELDS ONCE INFO VALIDATED
     * 7. GENERATE JAVADOCS
     */
    
    
    /**
     * The AppController constructor instantiates the DataController instance to manage data loading within AppController, as well as
     * an ArrayList of Users which will contain the elements loaded by the DataController
     */
    public AppController() {
    	data = new DataController();
    	users = new ArrayList<>();
    }
    
    
    /**
     * The initialize method loads the users database and manages the logic for the front end of the application (i.e., everything before the pre-game screen)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	users = data.loadUsers(); // Load the users database
    	
    	setAppLaunchView(); // Open the launch screen
    	getMainMenuLogic(); // Instantiate the logic for controlling and navigating the main menu
    	getLoginSuccess(); // Listen for a successful login, which will then lead to the pre-game and gameplay logic
    }

    
    /**
     * The setAppLaunchView method makes the launch screen visible, but all other screens invisible
     */
    private void setAppLaunchView() {
    	appLaunchView.setVisible(true);
    	logInView.setVisible(false);
    	createAcctView.setVisible(false);
    	preGameView.setVisible(false);
    	pongGameView.setVisible(false);
    }
    
    
    /**
     * The getMainMenuLogic method instantiates instances of StartScreenController, LoginController, and RegisterController, 
     * then calls methods which listen for inputs on each of these screens
     */
    private void getMainMenuLogic() {
    	startUpScreen = new StartScreenController(logInMenuBtn, createAcctMenuBtn, appLaunchView, logInView, createAcctView);
    	loginScreen = new LoginController(backToStartScreenBtn2, logInView, appLaunchView, preGameView, existingUsername, existingPassword, logInBtn, users, logInValidationMsg);
        registerScreen = new RegisterController(backToStartScreenBtn1, createAcctView, appLaunchView, preGameView, newUsername, newPassword, createAcctBtn, users, createAcctValidationMsg);
        
        // Listen for inputs, button presses, or other interactions on any of these screens
        startUpScreen.getStartScreenInput();
    	loginScreen.getLoginScreenInput();
    	registerScreen.getRegisterScreenInput();
    	users = registerScreen.getUsers(); // Get an updated users ArrayList with the new user from the registration
    }
    
    
    /**
     * The getLoginSuccess method listens for a successful login, then calls the onUserLoggedIn method
     */
    private void getLoginSuccess() {
        loginScreen.setOnLoginSuccess(() -> onUserLoggedIn(loginScreen.getUser())); // User successfully logged in
        registerScreen.setOnRegisterSuccess(() -> onUserRegistered(registerScreen.getNewUser())); // User account successfully created; logged in
    }
    
    
   /**
    * The onUserLoggedIn method creates a new PreGameController instance, and takes in a User instance brought over from the login attempt <br>
    * Pre-game screen inputs and interactions are then listened for <br>
    * If the user chooses to play pong, then the runPong method is called, passing in the user's username
    * 
    * @param user
    */
    private void onUserLoggedIn(User user) {
    	users = data.loadUsers(); // Every time the user logs in, load the database to check it
    	
    	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, logInView, createAcctView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, user, data, users);
    	preGameScreen.getPreGameScreenInput();
    	
    	preGameScreen.setOnGameStart(() -> runPong(loginScreen.getUser().getUsername())); // User selects "Play Pong"
    }
    
    
    /**
     * The onUserRegistered method creates a new PreGameController instance, and takes in a User instance brought over from the register attempt <br>
     * Pre-game screen inputs and interactions are then listened for <br>
     * If the user chooses to play pong, then the runPong method is called, passing in the user's username
     * 
     * @param user
     */
    private void onUserRegistered(User user) {
    	users = data.loadUsers(); // Every time the user registers a new account, load the latest database contents
    	
    	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, logInView, createAcctView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, user, data, users);
    	preGameScreen.getPreGameScreenInput();
    	
    	preGameScreen.setOnGameStart(() -> runPong(user.getUsername()));
    }
    
    
    /**
     * The runPong method creates a new PongGameController instance and PongMenuController instance, taking in the user's username to display in-game <br>
     * In-game menu inputs are listened for (i.e., forfeit, play again, back to menu) <br>
     * The pong game itself begins <br>
     * If the user chooses to play pong again, then the runPong method is internally called, passing in the user's username
     * 
     * @param username
     */
    private void runPong(String username) {
    	playPong = new PongGameController(pongGameView, ball, plyPaddle, oppPaddle, plyScore, oppScore, gameEndPane, gameResultMsg, plyName, username, plyForfeit);
    	navPong = new PongMenuController(pongGameView, preGameView, plyForfeit, backToMenuBtn, playAgainBtn, playPong, preGameScreen);
    	
    	navPong.getNavPongInput();
    	playPong.startGame(); // Start the pong game
    	
    	navPong.setOnPlayAgain(() -> runPong(username)); // Call runPong again if the user wants to play again upon game over
    }
    
}
