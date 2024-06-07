package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class AppController implements Initializable {

    @FXML
    private AnchorPane appLaunchPane;

    @FXML
    private AnchorPane appLaunchView;

    @FXML
    private Button backToMenuBtn;

    @FXML
    private Circle ball;

    @FXML
    private AnchorPane creatAcctPane;

    @FXML
    private Button createAcctBtn;

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
    private AnchorPane pongGameView;

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
    private Text logInValidationMsg1;

    @FXML
    private AnchorPane logInView;

    @FXML
    private Button midModeBtn;

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
    private AnchorPane preGameView;

    @FXML
    private Text welcomeMsg;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	boolean playPong = true; // placeholder
    	
    	if (playPong) {
    		pongGameView.setVisible(true);
    		runPong();
    	}

    }
    
    
    private void runPong() {
    	PongController play = new PongController(pongGameView, ball, plyPaddle, oppPaddle, plyScore, oppScore, gameEndPane, gameResultMsg);
    	play.startAnimation();
    }
    
    
    
    
    @FXML
    void backToMenuBtnHandler(ActionEvent event) {

    }

    @FXML
    void easyModeBtnHandler(ActionEvent event) {

    }

    @FXML
    void hardModeBtnHandler(ActionEvent event) {

    }

    @FXML
    void midModeBtnHandler(ActionEvent event) {

    }

    @FXML
    void playAgainButtonHandler(ActionEvent event) {

    }

    @FXML
    void playGameBtnHandler(ActionEvent event) {

    }

    @FXML
    void plyForfeitHandler(ActionEvent event) {

    }

}
