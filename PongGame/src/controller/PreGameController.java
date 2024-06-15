package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class PreGameController {

	private AnchorPane appLaunchView;
	private AnchorPane preGameView;
	private AnchorPane pongGameView;
	private AnchorPane loginView;
	private AnchorPane createAcctView;
	private Button menuBtn;
	private Button startBtn;
	private Text welcomeMsg;
	private Text numWinsMsg;
	private String username;
	private String numWins;
	
	private boolean pongGameStarted;
	private Runnable onGameStart;


	public PreGameController(AnchorPane appLaunchView, AnchorPane preGameView, AnchorPane pongGameView, AnchorPane loginView, AnchorPane createAcctView, Button menuBtn, Button startBtn, Text welcomeMsg, Text numWinsMsg, String username, String numWins) {
		this.appLaunchView = appLaunchView;
		this.preGameView = preGameView;
		this.pongGameView = pongGameView;
		this.loginView = loginView;
		this.createAcctView = createAcctView;
		this.menuBtn = menuBtn;
		this.startBtn = startBtn;
		this.welcomeMsg = welcomeMsg;
		this.numWinsMsg = numWinsMsg;
		this.username = username;
		this.numWins = numWins;
	}
	
	
	/**
	 * The getLoginScreenInput method contains the handling methods for the back button and login button
	 */
	public void getPreGameScreenInput() {
		setMessages();
		backToStartScreenBtnHandler(); // Back button handler
		startPongGameBtnHandler();
	}
	
	
	private void setMessages() {
		welcomeMsg.setText("WELCOME BACK " + username.toUpperCase()); // TODO MVC
		numWinsMsg.setText(numWins + " WINS"); // TODO MVC
	}
	
	
	/**
	 * The backToStartscreenBtnHandler method alters AnchorPane visibility so as to return the user to the app's launch screen
	 */
	private void backToStartScreenBtnHandler() {
		menuBtn.setOnMouseClicked(e -> {
			preGameView.setVisible(false);
			loginView.setVisible(false);
			createAcctView.setVisible(false);
			appLaunchView.setVisible(true); // Open launcher screen
		});
	}
	
	
	/**
	 * TODO
	 */
	private void startPongGameBtnHandler() {
		startBtn.setOnMouseClicked(e -> {
			pongGameStarted = true;
			
			if (onGameStart != null) {
				onGameStart.run();
			}
			
			loginView.setVisible(false);
			createAcctView.setVisible(false);
			preGameView.setVisible(false);
			pongGameView.setVisible(true); // Open Pong game
		});
	}
	
	
	/**
	 * @return TODO
	 */
	public boolean pongGameStarted() {
		return pongGameStarted;
	}
	
	
	/**
	 * TODO
	 * 
	 * @param onGameStart
	 */
	public void setOnGameStart(Runnable onGameStart) {
		this.onGameStart = onGameStart;
	}
	
	
	public void updateWinCount() { // TODO update users ArrayList with the updated win count
		numWins = Integer.parseInt(numWins) + 1 + "";
		setMessages();
	}
	
}
