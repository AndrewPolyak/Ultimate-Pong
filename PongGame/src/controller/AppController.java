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
 * @version TODO
 */
public class AppController implements Initializable {

	@FXML
    private Button PreGameMenuBtn; // TODO

    @FXML
    private AnchorPane appLaunchPane; // TODO

    @FXML
    private AnchorPane appLaunchView; // TODO

    @FXML
    private Button backToMenuBtn; // TODO

    @FXML
    private Button backToStartScreenBtn1; // TODO

    @FXML
    private Button backToStartScreenBtn2; // TODO

    @FXML
    private Circle ball; // TODO

    @FXML
    private AnchorPane creatAcctPane; // TODO

    @FXML
    private Button createAcctBtn; // TODO

    @FXML
    private Button createAcctMenuBtn; // TODO

    @FXML
    private Text createAcctValidationMsg; // TODO

    @FXML
    private AnchorPane createAcctView; // TODO

    @FXML
    private TextField existingPassword; // TODO

    @FXML
    private TextField existingUsername; // TODO

    @FXML
    private AnchorPane gameEndPane; // TODO

    @FXML
    private Text gameMsg; // TODO

    @FXML
    private Text gameResultMsg; // TODO

    @FXML
    private Button logInBtn; // TODO

    @FXML
    private Button logInMenuBtn; // TODO

    @FXML
    private AnchorPane logInPane; // TODO

    @FXML
    private Text logInValidationMsg; // TODO

    @FXML
    private AnchorPane logInView; // TODO

    @FXML
    private TextField newPassword; // TODO

    @FXML
    private TextField newUsername; // TODO

    @FXML
    private Text numWinsMsg; // TODO

    @FXML
    private Rectangle oppPaddle; // TODO

    @FXML
    private Text oppScore; // TODO

    @FXML
    private Button playAgainBtn; // TODO

    @FXML
    private Button playGameBtn; // TODO

    @FXML
    private Button plyForfeit; // TODO

    @FXML
    private Text plyName; // TODO

    @FXML
    private Rectangle plyPaddle; // TODO

    @FXML
    private Text plyScore; // TODO

    @FXML
    private AnchorPane pongGameView; // TODO

    @FXML
    private AnchorPane preGameView; // TODO

    @FXML
    private Text welcomeMsg; // TODO
    
    private StartScreenController startUpScreen; // TODO
    private LoginController loginScreen; // TODO
    private RegisterController registerScreen; // TODO
    private PreGameController preGameScreen; // TODO
    private PongGameController playPong; // TODO
    private PongMenuController navPong; // TODO
    
    private DataController data; // TODO
    private ArrayList<User> users; // TODO
    
    /*
     * TODO LIST
     * 
     * 1. COMPLETE DOCUMENTATION
     * 2. ADD LOGGER
     * 3. IMPROVE PRE-GAME UI (i.e., add game name at top for decoration, change "Menu" button to "Save & Quit"
     * 4. FIX PONG GAMEPLAY BOUNCING BUG
     * 5. FIND OUT HOW TO HIDE PASSWORD FIELD CHARACTERS
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
