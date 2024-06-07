package controller;

import java.security.SecureRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import view.GameViewMessages;

/**
 * The PongController class contains the main logic for controlling the pong gameplay
 * 
 * @author Andrew Polyak
 * @version June 7, 2024
 */
public class PongController {

	private AnchorPane gameView; // Represents the entire window
	private Circle ball; // Represents the ball
	private Rectangle plyPaddle; // Represents the user's paddle
	private Rectangle oppPaddle; // Represents the opponent's paddle
	private Text plyScore; // Represents the user's score
	private Text oppScore; // Represents the opponent's score
	private AnimationTimer animate; // Represents the AnimationTimer instance
	private SecureRandom random; // Represents the SecureRandom instance
	private AnchorPane gameEndPane; // Represents the game-end message
	private Text gameResultMsg; // Represents the message within gameEndPane indicating the game winner
	
	private GameViewMessages message; // Contains dynamic game messages
	
	// Define the boundaries of the playing field
	private final int TOP_WALL = 79; // Y positon of the top wall
	private final int BOTTOM_WALL = 575; // Y position of the bottom wall
	private final int LEFT_WALL = 0; // X position of the left wall
	private final int RIGHT_WALL = 1000; // X positon of the right wall
	
	// Define the starting spawn on the ball
	private final double BALL_X_SPAWN_START = 502;
	private final double BALL_Y_SPAWN_START = 325;
	
	private double ballYSpawn = BALL_Y_SPAWN_START; // The Y positon of the spawn may change, but initially it is set to BALL_Y_SPAWN_START
	
	private final int Y_SPAWN_SKEW = 125; // The Y position of the spawn may skew 125 up or down
	
	// Define the velocity of the ball
	private final double X_VELOCITY_START = 4.2; // Controls the starting horizontal speed of the ball**
	private final double Y_VELOCITY_START = 4.2; // Controls the starting vertical speed of the ball**
	
	private final double Y_VELOCITY_MIN = 4.2; // The minimum amount of diagonal Y velocity
	private final double Y_VELOCITY_MAX = 4.5; // The maximum amount of diagonal Y velocity ... influences max vertical trajectory of initial ball movement
	
	private double xVelocity = X_VELOCITY_START; // At the beginning of the game, xVelocity is set to X_VELOCITY_START, but may change throughout
	private double yVelocity = Y_VELOCITY_START; // At the beginning of the game, yVelocity is set to Y_VELOCITY_START, but may change throughout
	
	private final double SPEED_BOOST = 1.1; // Define the speed boost amount
	
	// Define the direction of the ball
	private boolean directionRight;
	private boolean directionUp;
	
	private boolean gameOn; // Define whether the game is running (true) or is over (false)
	
	private int numBounces = 0; // Counts the number of ball bounces in a round
	
	private final long PADDLE_BOUNCE_COOLDOWN = 600; // In milliseconds ... The ball can only bounce off a paddle once every 600 milliseconds
	private long lastBounceTime; // Stores the millisecond value since the ball was last bounced
	
	private final int WINNING_SCORE = 10; // Defines the score a player must reach to win the game
	
	private final double OPP_PADDLE_VELOCITY = 6.4; // Controls the vertical speed of the opponent's paddle
	
	
	/**
	 * The PongController constructor takes in a number of JavaFX objects which represent the pong game stage, paddles, ball, and user interface <br>
	 * It additionally instantiates key objects such as SecureRandom so as to randomly decide the starting direction of the ball <br>
	 * Finally, gameOn, the boolean value representing whether the game is running, is set to true
	 * 
	 * @param ball
	 * @param plyPaddle
	 * @param oppPaddle
	 * @param plyScore
	 * @param oppScore
	 * @param gameEndPane
	 * @param gameResultMsg
	 */
	public PongController(AnchorPane gameView, Circle ball, Rectangle plyPaddle, Rectangle oppPaddle, Text plyScore, Text oppScore, AnchorPane gameEndPane, Text gameResultMsg) {
		this.random = new SecureRandom();
		this.message = new GameViewMessages();
		this.ball = ball;
		this.plyPaddle = plyPaddle;
		this.oppPaddle = oppPaddle;
		this.plyScore = plyScore;
		this.oppScore = oppScore;
		this.gameEndPane = gameEndPane;
		this.gameResultMsg = gameResultMsg;
		this.gameView = gameView;
		this.directionRight = random.nextBoolean();
		this.directionUp = random.nextBoolean();
		this.gameOn = true;
	}
	
	
	/**
	 * The startAnimation method instantiates an AnimationTimer to begin the "frames" of the game
	 */
	public void startAnimation() {
		animate = new AnimationTimer() {
			
			
			/**
			 * The handle method contains the game logic
			 */
			@Override
			public void handle(long now) {
				if (gameOn) {
					moveBall(); // Ball movement, collisions, & bouncing
					checkForScore(); // Detect player scoring
					checkForGameEnd(); // Detect player reaching 10 score
					movePlyPaddle(); // User paddle controls
					moveOppPaddle(); // Opponent paddle AI
				}
			}
		};
		animate.start(); // Begin game
	}
	
	
	/**
	 * The moveBall method contains the key logic for moving the ball around the field <br><br>
	 * 
	 * If gameOn is true (i.e., the game has started and no player has won): <br>
	 * - Call directBall to set ball's velocity & trajectory <br>
	 * - Call bounceBall to detect ball collisions & redirects ball accordingly
	 */
	private void moveBall() {
		directBall(); // Set ball movement
		bounceBall(); // Detect & handle ball collisions
	}


	/**
	 * The method directBall moves the ball 1 X & 1 Y coordinate
	 */
	private void directBall() {
		// Set horizontal movement
		if (directionRight) {
			directBallRight();
		} else {
			directBallLeft();
		}
		
		// Set vertical movement (will make movement diagonal)
		if (directionUp) {
			directBallUp();
		} else {
			directBallDown();
		}
	}


	/**
	 * The bounceBall method detects ball collisions and appropriately bounces the ball off of the relevant obstacle <br>
	 * A speed boost is applied a limited number of times for each bounce as well
	 */
	private void bounceBall() {
		// Handle floor & ceiling bouncing
		if (wallCollision()) {
			bounceOffWall();
			increaseVelocity();
		} 
		
		// Handle paddle bouncing
		if (paddleCollision(plyPaddle)) { // If ball touches the player's paddle (right side), bounce it left & increase speed
			bounceOffPaddle(plyPaddle);
			increaseVelocity();
			
		} else if (paddleCollision(oppPaddle)) { // If ball touches the opponent's paddle (left side), bounce it right & increase speed 
			bounceOffPaddle(oppPaddle);
			increaseVelocity();
		}
	}
	
	
	/**
	 * The checkForScore method checks if a player has scored <br>
	 * If a player has scored, then they are awarded 1 point <br>
	 * The ball is then reset and begins moving again
	 */
	private void checkForScore() {
		if (ballTouchingLeftWall()) {
			addScore(plyScore);
			resetBall();
			moveBall();
		} else if (ballTouchingRightWall()) {
			addScore(oppScore);
			resetBall();
			moveBall();
		}
	}
	
	
	/**
	 * The resetBall method resets the ball to the field center and randomly applies a skew to its Y position spawn <br>
	 * The ball's velocity, bounce tracker, and beginning movement parameters are all reset as well
	 */
	private void resetBall() {
		boolean skewYUpward = random.nextBoolean(); // If true, then spawn will skew upward, otherwise it will skew downward
		
		ballYSpawn = BALL_Y_SPAWN_START; // Reset the Y position of the ball to center
			
		// Apply a skew to the Y position of the ball (to decrease repetition)
		if (skewYUpward) {
			ballYSpawn -= random.nextInt(Y_SPAWN_SKEW); // Skew spawn upward
		} else {
			ballYSpawn += random.nextInt(Y_SPAWN_SKEW); // Skew spawn downward
		}
		
		/*
		 * Randomize Y velocity (angle of ball trajectory)
		 * 
		 * max - min = range --> range * [num between 0 & 1 (how much, up to max, will be added to min)] = skew --> min + skew = [randomized Y trajectory]
		 */
		yVelocity = (random.nextDouble() * (Y_VELOCITY_MAX - Y_VELOCITY_MIN)) + Y_VELOCITY_MIN;
		
		// Respawn ball
		ball.setLayoutX(BALL_X_SPAWN_START);
		ball.setLayoutY(ballYSpawn);
		
		// Reset ball movement controllers
		directionRight = random.nextBoolean(); // Reset value (to decrease repetition)
		directionUp = random.nextBoolean(); // Reset value (to decrease repetition)
		xVelocity = X_VELOCITY_START; // Set x velocity to starting value
		
		numBounces = 0; // Reset value to allow for acceleration in new "round"
	}
	
	
	/**
	 * The checkForGameEnd method detects whether a player won <br>
	 * If a player has won, the displayResults method is subsequently called
	 */
	private void checkForGameEnd() {
		if (Integer.parseInt(plyScore.getText()) == WINNING_SCORE) { // If player won
			displayResults(true);
		} else if (Integer.parseInt(oppScore.getText()) == WINNING_SCORE) { // If opponent won
			displayResults(false);
		}
	}
	
	
	/**
	 * The displayResults method stops the ball and displays the end of game message informing the player whether they won or lost
	 * 
	 * @param plyWon
	 */
	private void displayResults(boolean plyWon) {
		gameOn = false; // End game
		
		// Stop ball
		resetBall();
		xVelocity = 0.0;
		yVelocity = 0.0;
		
		// Make game assets invisible
		ball.setVisible(false);
		oppPaddle.setVisible(false);
		plyPaddle.setVisible(false);
		
		// Display game results and menu options
		gameEndPane.setVisible(true);
		
		if (plyWon) {
			gameResultMsg.setText(message.plyWinMsg());
		} else {
			gameResultMsg.setText(message.plyLoseMsg());
		}
	}
	
	
	/**
	 * The bounceOffPaddle method bounces the ball off of the relevant paddle <br>
	 * Bouncing only occurs if the bounce cooldown has elapsed (to prevent ball from getting stuck in paddle) <br>
	 * If the ball touches the top of the paddle, always bounce it upward with a slight speed increase (vice versa for bottom)
	 * 
	 * @param paddle
	 */
	private void bounceOffPaddle(Rectangle paddle) {
		long currentTime = System.currentTimeMillis(); // Get current time in milliseconds
		
		if ((currentTime - lastBounceTime) > PADDLE_BOUNCE_COOLDOWN) { // If elapsed time since last bounce is longer than cooldown timer
			xVelocity *= -1; // Bounce ball
			
			if (ballTouchingTopOfPaddle(paddle)) {
				if (yVelocity > 0) { // If ball is moving downward, send it upward and slightly increase speed
					yVelocity *= -1.1;
				}
				
			} else if (ballTouchingBottomOfPaddle(paddle)) {
				if (this.yVelocity < 0) { // If ball is moving upward, send it downward and slightly increase speed
					this.yVelocity *= -1.1;
				}
			} 
			lastBounceTime = currentTime; 
		}
		numBounces++; // The ball has bounced; increment this value
	}
	
	
	/**
	 * The movePlyPaddle method allows the user to control their paddle <br>
	 * The Y position of their paddle is equal to the Y position of their mouse
	 */
	private void movePlyPaddle() {
		// Enable mouse control of the player paddle
		gameView.setOnMouseMoved(e -> {
			plyPaddle.setLayoutY(e.getY() - (plyPaddle.getHeight() / 2)); // Set the center of the paddle equal to the mouse's Y position
		});
		
		// Ensure paddle does not clip out of the playing field
		if (plyPaddle.getLayoutY() < TOP_WALL) {
			plyPaddle.setLayoutY(TOP_WALL);
		} else if (plyPaddle.getLayoutY() > BOTTOM_WALL - plyPaddle.getHeight()) {
			plyPaddle.setLayoutY(BOTTOM_WALL - plyPaddle.getHeight());
		}
	}
	
	
	/**
	 * The moveOppPaddle method controls the AI of the opponents paddle <br>
	 * If the ball is above the paddle, it moves up (and vice versa for below) <br>
	 */
	private void moveOppPaddle() {
		if (ballAboveOppPaddle()) {
			moveOppPaddleUp();
		} else if (ballBelowOppPaddle()) {
			moveOppPaddleDown();
		}
		
		// Ensure paddle does not clip out of the playing field
		if (oppPaddle.getLayoutY() < TOP_WALL) {
			oppPaddle.setLayoutY(TOP_WALL);
		} else if (oppPaddle.getLayoutY() > BOTTOM_WALL - oppPaddle.getHeight()) {
			oppPaddle.setLayoutY(BOTTOM_WALL - oppPaddle.getHeight());
		}
	}
	

	/**
	 * The ballAboveOppPaddle method checks whether the ball's Y coordinates are above the center of the opponent's paddle
	 * 
	 * @return true if the ball is above, false otherwise
	 */
	private boolean ballAboveOppPaddle() {
		return ball.getLayoutY() < (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2); // If ball is above paddle
	}
	
	
	/**
	 * The ballBelowOppPaddle method checks whether the ball's Y coordinates are below the center of the opponent's paddle
	 * 
	 * @return true if the ball is below, false otherwise
	 */
	private boolean ballBelowOppPaddle() {
		return ball.getLayoutY() > (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2); // If ball is below paddle
	}
	
	
	/**
	 * The moveOppPaddleUp method moves the opponent's paddle upward
	 */
	private void moveOppPaddleUp() {
		oppPaddle.setLayoutY(oppPaddle.getLayoutY() - OPP_PADDLE_VELOCITY);
	}
	
	
	/**
	 * The moveOppPaddleDown method moves the opponent's paddle downward
	 */
	private void moveOppPaddleDown() {
		oppPaddle.setLayoutY(oppPaddle.getLayoutY() + OPP_PADDLE_VELOCITY);
	}
	
	
	/**
	 * The method directBallLeft moves the ball 1 X coordinate to the left
	 */
	private void directBallLeft() {
		ball.setLayoutX(ball.getLayoutX() - xVelocity); // Go left
	}
	
	
	/**
	 * The method directBallRight moves the ball 1 X coordinate to the right
	 */
	private void directBallRight() {
		ball.setLayoutX(ball.getLayoutX() + xVelocity); // Go right
	}
	
	
	/**
	 * The method directBallUp moves the ball 1 Y coordinate upward
	 */
	private void directBallUp() {
		ball.setLayoutY(ball.getLayoutY() - yVelocity); // Go up
	}
	
	
	/**
	 * The method directBallUp moves the ball 1 Y coordinate downward
	 */
	private void directBallDown() {
		ball.setLayoutY(ball.getLayoutY() + yVelocity); // Go down
	}
	
	
	/**
	 * The wallCollision method detects ball contact with the ceiling or floor
	 * 
	 * @return true if in contact, false otherwise
	 */
	private boolean wallCollision() {
		return (ball.getLayoutY() <= (TOP_WALL + ball.getRadius())) || 
				(ball.getLayoutY() >= (BOTTOM_WALL - ball.getRadius())); // FIXME: Ball sometimes gets stuck in floor or ceiling if paddle pushes it into it
	}
	
	
	/**
	 * The bounceOffWall method reverses ball's Y-axis movement & increments numBounces
	 */
	private void bounceOffWall() {
		yVelocity *= -1; // Reverse y direction
		numBounces++;
	}
	
	
	/**
	 * The paddleCollision method detects whether the ball is in contact with a paddle (whether it be the player's or opponent's)
	 * 
	 * @param paddle
	 * @return true if the ball contacts the paddle, false otherwise
	 */
	private boolean paddleCollision(Rectangle paddle) {
        return ball.getBoundsInParent().intersects(paddle.getBoundsInParent());
    }
	
	
	/**
	 * The increaseVelocity method applies a speed boost to the x and y velocity up to a specified limit
	 */
	private void increaseVelocity() {
		// Increase xVelocity up to a limit
		if (numBounces <= 9) { // Initial xVelocity is multiplied by SPEED_BOOST (1.1) 9 times
			xVelocity *= SPEED_BOOST;
		}
		
		// Increase yVelocity up to a limit
		if (numBounces <= 5) { // Initial yVelocity is multiplied by SPEED_BOOST (1.1) 5 times
			yVelocity *= SPEED_BOOST;
		}
	}
	
	
	/**
	 * The ballTouchingLeftWall method detects if the ball is touching the left wall of the field (i.e., the opponent's goal post)
	 * 
	 * @return true if ball is touching the left wall, false otherwise
	 */
	private boolean ballTouchingLeftWall() {
		return (ball.getLayoutX() - ball.getRadius()) <= LEFT_WALL;
	}
	
	
	/**
	 * The ballTouchingRightWall method detects if the ball is touching the right wall of the field (i.e., the player's goal post)
	 * 
	 * @return true if ball is touching the right wall, false otherwise
	 */
	private boolean ballTouchingRightWall() {
		return (ball.getLayoutX() + ball.getRadius()) >= RIGHT_WALL;
	}
	
	
	/**
	 * The addScore method increments a score tracker (whether it be the player's or opponent's) by 1
	 * 
	 * @param scoreTracker
	 */
	private void addScore(Text scoreTracker) {
		scoreTracker.setText((Integer.parseInt(scoreTracker.getText()) + 1) + "");
	}
	
	
	/**
	 * The ballTouchingTopOfPaddle method detects whether the ball touches the top 20px of the paddle
	 * 
	 * @param paddle
	 * @return true if the ball touches the top portion of the paddle, false otherwise
	 */
	private boolean ballTouchingTopOfPaddle(Rectangle paddle) {
		return (this.ball.getLayoutY() < paddle.getLayoutY() + 20) || // If the ball is in the top portion of the paddle
				(this.ball.getLayoutX() > paddle.getLayoutX() && // If the ball is within the left side of the paddle
						this.ball.getLayoutX() < paddle.getLayoutX() + paddle.getWidth()); // And if the ball is within the right side of the paddle
	}
	
	
	/**
	 * The ballTouchingBottomOfPaddle method detects whether the ball touches the bottom 20px of the paddle
	 * 
	 * @param paddle
	 * @return true if the ball touches the bottom portion of the paddle, false otherwise
	 */
	private boolean ballTouchingBottomOfPaddle(Rectangle paddle) {
		return (this.ball.getLayoutY() > paddle.getLayoutY() + 50) || // If the ball is in the bottom portion of the paddle
				(this.ball.getLayoutX() > paddle.getLayoutX() && // If the ball is within the left side of the paddle
						this.ball.getLayoutX() < paddle.getLayoutX() + paddle.getWidth()); // And if the ball is within the right side of the paddle
	}

}
