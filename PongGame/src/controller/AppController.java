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
    private AnchorPane creatAcctView1;

    @FXML
    private Button createAcctBtn;

    @FXML
    private Button createAcctBtn1;

    @FXML
    private Button createAcctMenuBtn;

    @FXML
    private AnchorPane createAcctView;

    @FXML
    private Button easyModeBtn;

    @FXML
    private TextField existingPassword;

    @FXML
    private TextField existingUsername;

    @FXML
    private AnchorPane gameConfigPane;

    @FXML
    private AnchorPane gameEndPane;

    @FXML
    private Text gameMsg;

    @FXML
    private Text gameResultMsg;

    @FXML
    private Button hardModeBtn;

    @FXML
    private Button logInBtn;

    @FXML
    private Button logInMenuBtn;

    @FXML
    private AnchorPane logInPane;

    @FXML
    private Text logInValidationMsg;

    @FXML
    private Text createAcctValidationMsg;

    @FXML
    private Text logInValidationMsg11;

    @FXML
    private AnchorPane logInView;

    @FXML
    private Button midModeBtn;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField newPassword1;

    @FXML
    private TextField newUsername;

    @FXML
    private TextField newUsername1;

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
    
    StartScreenController startUpScreen;
    
    LoginController loginScreen;
    RegisterController registerScreen;
    
    
    ArrayList<User> users = new ArrayList<>();
    User a = new User("a", "b", 0);
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	users.add(a); // temporary
    	
    	
    	startUpScreen = new StartScreenController(logInMenuBtn, createAcctMenuBtn, appLaunchView, logInView, createAcctView);
    	loginScreen = new LoginController(backToStartScreenBtn2, logInView, appLaunchView, preGameView, existingUsername, existingPassword, logInBtn, users, logInValidationMsg);
        registerScreen = new RegisterController(backToStartScreenBtn1, createAcctView, appLaunchView, preGameView, newUsername, newPassword, createAcctBtn, users, createAcctValidationMsg);
    	
    	appLaunchView.setVisible(true);
    	logInView.setVisible(false);
    	createAcctView.setVisible(false);
    	preGameView.setVisible(false);
    	pongGameView.setVisible(false);
    	
    	startUpScreen.getStartScreenInput();
		
    	loginScreen.getLoginScreenInput();
		
    	registerScreen.getRegisterScreenInput();
    	users = registerScreen.getUsers();
    	
    	
    	//if (playPong) {
    	//	pongGameView.setVisible(true);
    	//	runPong();
    	//}
    		
    }
    
    
    private void runPong() {
    	PongController play = new PongController(pongGameView, ball, plyPaddle, oppPaddle, plyScore, oppScore, gameEndPane, gameResultMsg);
    	play.startAnimation();
    }
    
}
