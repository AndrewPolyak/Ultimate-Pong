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
    
    
    ArrayList<User> users = new ArrayList<>();
    User a = new User("a", "b", 0);
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	users.add(a); // temporary
    	
    	appLaunchView.setVisible(true);
    	logInView.setVisible(false);
    	createAcctView.setVisible(false);
    	preGameView.setVisible(false);
    	pongGameView.setVisible(false);
    	
    	startUpScreen = new StartScreenController(logInMenuBtn, createAcctMenuBtn, appLaunchView, logInView, createAcctView);
    	loginScreen = new LoginController(backToStartScreenBtn2, logInView, appLaunchView, preGameView, existingUsername, existingPassword, logInBtn, users, logInValidationMsg);
        registerScreen = new RegisterController(backToStartScreenBtn1, createAcctView, appLaunchView, preGameView, newUsername, newPassword, createAcctBtn, users, createAcctValidationMsg);
    	
    	startUpScreen.getStartScreenInput();
		
    	loginScreen.getLoginScreenInput();
		
    	registerScreen.getRegisterScreenInput();
    	users = registerScreen.getUsers();
    	
    	// FIXME this does not work
    	if (loginScreen.loggedIn()) {
        	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, loginScreen.getUsername(), loginScreen.getNumPongWins());
        } else if (registerScreen.loggedIn()) {
        	preGameScreen = new PreGameController(appLaunchView, preGameView, pongGameView, PreGameMenuBtn, playGameBtn, welcomeMsg, numWinsMsg, registerScreen.getUsername(), registerScreen.getNumPongWins());
        }
    	
    	preGameScreen.getPreGameScreenInput();
    	
    	// FIXME this does not work
    	if (preGameScreen.pongGameStarted()) {
    		runPong();
    	}
    }
    
    
    
    private void runPong() {
    	PongController play = new PongController(pongGameView, ball, plyPaddle, oppPaddle, plyScore, oppScore, gameEndPane, gameResultMsg);
    	play.startAnimation();
    }
    
}
