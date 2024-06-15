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

public class AppController implements Initializable {

	@FXML
    private Button PreGameMenuBtn;

    @FXML
    private AnchorPane appLaunchPane;

    @FXML
    private AnchorPane appLaunchView;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Button backToStartScreenBtn1;

    @FXML
    private Button backToStartScreenBtn2;

    @FXML
    private Circle ball;

    @FXML
    private AnchorPane creatAcctPane;

    @FXML
    private Button createAcctBtn;

    @FXML
    private Button createAcctMenuBtn;

    @FXML
    private Text createAcctValidationMsg;

    @FXML
    private AnchorPane createAcctView;

    @FXML
    private TextField existingPassword;

    @FXML
    private TextField existingUsername;

    @FXML
    private AnchorPane gameEndPane;

    @FXML
    private Text gameMsg;

    @FXML
    private Text gameResultMsg;

    @FXML
    private Button logInBtn;

    @FXML
    private Button logInMenuBtn;

    @FXML
    private AnchorPane logInPane;

    @FXML
    private Text logInValidationMsg;

    @FXML
    private AnchorPane logInView;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField newUsername;

    @FXML
    private Text numWinsMsg;

    @FXML
    private Rectangle oppPaddle;

    @FXML
    private Text oppScore;

    @FXML
    private Button playAgainBtn;

    @FXML
    private Button playGameBtn;

    @FXML
    private Button plyForfeit;

    @FXML
    private Text plyName;

    @FXML
    private Rectangle plyPaddle;

    @FXML
    private Text plyScore;

    @FXML
    private AnchorPane pongGameView;

    @FXML
    private AnchorPane preGameView;

    @FXML
    private Text welcomeMsg;
    
    private StartScreenController startUpScreen; 
    private LoginController loginScreen;
    private RegisterController registerScreen;
    private PreGameController preGameScreen;
    private PongGameController playPong;
    private PongMenuController navPong;
    
    private DataController data;
    ArrayList<User> users;
    
    public AppController() {
    	data = new DataController();
    	users = new ArrayList<>();
    }
    
    
    
    
    
    // TODO implement logger
    
    /**
     * TODO
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	users = data.loadUsers();
    	
    	setAppLaunchView();
    	
    	setMainMenuControllers();
    	
    	getMainMenuInput();
    	
    	getLoginSuccess();
    }

    
    /**
     * TODO
     */
    private void setAppLaunchView() {
    	appLaunchView.setVisible(true);
    	logInView.setVisible(false);
    	createAcctView.setVisible(false);
    	preGameView.setVisible(false);
    	pongGameView.setVisible(false);
    }
    
    
    /**
     * TODO
     */
    private void setMainMenuControllers() {
    	startUpScreen = new StartScreenController(logInMenuBtn, createAcctMenuBtn, appLaunchView, logInView, createAcctView);
    	loginScreen = new LoginController(backToStartScreenBtn2, logInView, appLaunchView, preGameView, existingUsername, existingPassword, logInBtn, users, logInValidationMsg);
        registerScreen = new RegisterController(backToStartScreenBtn1, createAcctView, appLaunchView, preGameView, newUsername, newPassword, createAcctBtn, users, createAcctValidationMsg);
    }
    
    
    /**
     * TODO
     */
    private void getMainMenuInput() {
    	startUpScreen.getStartScreenInput();
		
    	loginScreen.getLoginScreenInput();
		
    	registerScreen.getRegisterScreenInput();
    	users = registerScreen.getUsers();
    }
    
    
    /**
     * TODO
     */
    private void getLoginSuccess() {
    	// Set listeners for login and registration success
        loginScreen.setOnLoginSuccess(() -> onUserLoggedIn(loginScreen.getUser()));
        registerScreen.setOnRegisterSuccess(() -> onUserRegistered(registerScreen.getNewUser()));
    }
    
    
    /**
     * TODO
     * 
     * @param username
     * @param numPongWins
     */
    private void onUserLoggedIn(User user) {
    	users = data.loadUsers();
    	
    	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, logInView, createAcctView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, user, data, users);
    	preGameScreen.getPreGameScreenInput();
    	
    	preGameScreen.setOnGameStart(() -> runPong(loginScreen.getUser().getUsername()));
    }
    
    
    /**
     * TODO
     * 
     * @param username
     * @param numPongWins
     */
    private void onUserRegistered(User user) {
    	users = data.loadUsers();
    	
    	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, logInView, createAcctView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, user, data, users);
    	preGameScreen.getPreGameScreenInput();
    	
    	preGameScreen.setOnGameStart(() -> runPong(user.getUsername()));
    }
    
    
    /**
     * TODO
     * 
     * @param username
     */
    private void runPong(String username) {
    	playPong = new PongGameController(pongGameView, ball, plyPaddle, oppPaddle, plyScore, oppScore, gameEndPane, gameResultMsg, plyName, username, plyForfeit);
    	navPong = new PongMenuController(pongGameView, preGameView, plyForfeit, backToMenuBtn, playAgainBtn, playPong, preGameScreen);
    	
    	navPong.getNavPongInput();
    	playPong.startGame();
    	
    	navPong.setOnPlayAgain(() -> runPong(username));
    }
    
}
