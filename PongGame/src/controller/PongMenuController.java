package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class PongMenuController {

	
	PongGameController game;
	
	AnchorPane pongGameView;
	AnchorPane preGameView;
	Button plyForfeitBtn;
	Button backToMenuBtn;
	Button playAgainBtn;
	PreGameController preGameScreen;
	
	
	private Runnable onPlayAgain;
	private Runnable onBackToMenu;
	
	
	public PongMenuController(AnchorPane pongGameView, AnchorPane preGameView, Button plyForfeitBtn, Button backToMenuBtn, Button playAgainBtn, PongGameController game, PreGameController preGameScreen) {
		this.pongGameView = pongGameView;
		this.preGameView = preGameView;
		this.plyForfeitBtn = plyForfeitBtn;
		this.backToMenuBtn = backToMenuBtn;
		this.playAgainBtn = playAgainBtn;
		this.game = game;
		this.preGameScreen = preGameScreen;
	}
	
	
	/**
	 * TODO
	 */
	public void getNavPongInput() {
		backToPreGameScreenBtnHandler();
		playAgainBtnHander();
	}

	
	/**
	 * TODO
	 */
	private void backToPreGameScreenBtnHandler() {
		plyForfeitBtn.setOnMouseClicked(e -> {
			game.stopGame(); // End the game
			
			preGameScreen.updateWinCount(); // FIXME the game only counts the win if the user exists the game following the win. Need to count it even if they choose to play again
			// FIXME losses sometimes seem to count for wins??
			
			
			pongGameView.setVisible(false);
			preGameView.setVisible(true); // Open pre-game menu
		});
		
		backToMenuBtn.setOnMouseClicked(e -> {
			if (onBackToMenu != null) {
				onBackToMenu.run();
			}
			
			preGameScreen.updateWinCount();
			
			pongGameView.setVisible(false);
			preGameView.setVisible(true); // Open pre-game menu
		});
	}
	
	
	/**
	 * TODO
	 */
	private void playAgainBtnHander() {
		playAgainBtn.setOnMouseClicked(e -> {
			if (onPlayAgain != null) {
				onPlayAgain.run();
			}
		});
	}
	
	
	/**
	 * TODO
	 * 
	 * @param onPlayAgain
	 */
	public void setOnPlayAgain(Runnable onPlayAgain) {
		this.onPlayAgain = onPlayAgain;
	}
	
	
	/**
	 * TODO
	 * 
	 * @param onBackToMenu
	 */
	public void setOnBackToMenu(Runnable onBackToMenu) {
		this.onBackToMenu = onBackToMenu;
	}

}
