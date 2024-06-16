package controller;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * The PongMenuController class contains the logic for handling the non-gameplay-related elements of the pong game, 
 * including the forfeit button, back to menu button, and play again button
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class PongMenuController {

	AnchorPane pongGameView; // Represents the pong game screen
	AnchorPane preGameView; // Represents the pre-game screen
	Button plyForfeitBtn; // Represents the forfeit button during gameplay
	Button backToMenuBtn; // Represents the back to menu button post-gameplay
	Button playAgainBtn; // Represents the play again button post-gameplay
	PongGameController game; // Represents the logical instance of the pong game
	PreGameController preGameScreen; // Represents the logical instance of the pre-game menu
	
	
	private Runnable onPlayAgain; // TODO
	private Runnable onBackToMenu; // TODO
	
	
	/**
	 * The PongMenuController constructor takes in the interactable UI elements from the pong game and instantiates those elements
	 * 
	 * @param pongGameView
	 * @param preGameView
	 * @param plyForfeitBtn
	 * @param backToMenuBtn
	 * @param playAgainBtn
	 * @param game
	 * @param preGameScreen
	 */
	public PongMenuController(
			AnchorPane pongGameView, AnchorPane preGameView, Button plyForfeitBtn, Button backToMenuBtn, 
			Button playAgainBtn, PongGameController game, PreGameController preGameScreen) {
		this.pongGameView = pongGameView;
		this.preGameView = preGameView;
		this.plyForfeitBtn = plyForfeitBtn;
		this.backToMenuBtn = backToMenuBtn;
		this.playAgainBtn = playAgainBtn;
		this.game = game;
		this.preGameScreen = preGameScreen;
	}
	
	
	/**
	 * The getNavPongInput method contains the handling methods for the back to menu button and play again button
	 */
	public void getNavPongInput() {
		backToPreGameScreenBtnHandler();
		playAgainBtnHandler();
	}

	
	/**
	 * The backToPreGameScreenBtnHandler method handles instances where the user clicks the forfeit button or 
	 * back to menu button from within the pong game <br><br>
	 * 
	 * - If the forfeit button is clicked, then the user is simply returned to the pre-game menu <br>
	 * - If the back to menu button is clicked, the user is returned to the pre-game menu with an updated win count if they won the game
	 * 
	 */
	private void backToPreGameScreenBtnHandler() {
		// Handle the forfeit button (during gameplay)
		plyForfeitBtn.setOnMouseClicked(e -> {
			game.stopGame(); // End the game
			
			pongGameView.setVisible(false); // Close game
			preGameView.setVisible(true); // Open pre-game menu
		});
		
		// Handle the back to menu button (post-gameplay)
		backToMenuBtn.setOnMouseClicked(e -> {
			game.stopGame(); // End the game
			
			pongGameView.setVisible(false); // Close the game
			preGameView.setVisible(true); // Open pre-game menu
			
			// If the player won, update their win count
			if (game.getPlyWon()) {
				preGameScreen.updateWinCount();
			}
			
			// If setOnBackToMenu has been called, run onBackToMenu
			if (onBackToMenu != null) {
				onBackToMenu.run();
			}
		});
	}
	
	
	/**
	 * The playAgainBtnHandler method handles the logic for updating the user's win count if they won the previous game and 
	 * also returns them into the game for another round
	 */
	private void playAgainBtnHandler() {
		playAgainBtn.setOnMouseClicked(e -> {
			// If the player won the previous game, update their win count
			if (game.getPlyWon()) {
				preGameScreen.updateWinCount();
			}
			
			// If setOnPlayAgain has been called, run onPlayAgain
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
