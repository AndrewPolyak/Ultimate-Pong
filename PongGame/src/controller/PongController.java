package controller;

import java.security.SecureRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class PongController {

	private AnchorPane gameView;
	private Circle ball;
	private Rectangle plyPaddle;
	private Rectangle oppPaddle;
	private Text plyScore;
	private Text oppScore;
	private AnimationTimer animate;
	private SecureRandom random;
	private AnchorPane gameEndPane;
	private Text gameResultMsg;
	
	private final int TOP_WALL = 79;
	private final int BOTTOM_WALL = 575;
	private final int LEFT_WALL = 0;
	private final int RIGHT_WALL = 1000;
	
	private final double BALL_X_SPAWN_START = 502;
	private final double BALL_Y_SPAWN_START = 325;
	
	private double ballYSpawn = BALL_Y_SPAWN_START;
	
	private final double X_VELOCITY_START = 4.2; // Controls the horizontal speed of the ball**
	private final double Y_VELOCITY_START = 4.2; // Controls the vertical speed of the ball**
	
	private final int Y_SPAWN_SKEW = 125;
	
	private final double Y_VELOCITY_MIN = Y_VELOCITY_START; // Min ball Y velocity
	private final double Y_VELOCITY_MAX = 4.0; // Max ball Y velocity ... influences max vertical trajectory of initial ball movement
	
	private final double SPEED_BOOST = 1.1;
	
	private double xVelocity = X_VELOCITY_START;
	private double yVelocity = Y_VELOCITY_START;
	
	private boolean directionRight;
	private boolean directionUp;
	
	private boolean gameOn;
	
	private int numBounces = 0;
	
	private final long BOUNCE_COOLDOWN = 600; // in milliseconds
	private long lastBounceTime;
	
	private final int MAX_POINTS = 10;
	
	
	/**
	 * TODO
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
	 * TODO
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
	 * TODO
	 */
	private void movePlyPaddle() { // TODO add a boolean option for mouse controls OR W and S button controls?
		// Enable mouse control of the player paddle
		gameView.setOnMouseMoved(e -> {
			plyPaddle.setLayoutY(e.getY() - (plyPaddle.getHeight() / 2));
		});
		
		// Ensure paddle does not leave the playing field
		if (plyPaddle.getLayoutY() < TOP_WALL) {
			plyPaddle.setLayoutY(TOP_WALL);
		} else if (plyPaddle.getLayoutY() > BOTTOM_WALL - plyPaddle.getHeight()) {
			plyPaddle.setLayoutY(BOTTOM_WALL - plyPaddle.getHeight());
		}
	}
	
	
	/**
	 * TODO
	 */
	private void moveOppPaddle() {
		
		if (this.ball.getLayoutY() < (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2)) { // If ball is above paddle
			this.oppPaddle.setLayoutY(this.oppPaddle.getLayoutY() - 6.4);
			
		} else if (this.ball.getLayoutY() > (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2)) { // If ball is below paddle
			this.oppPaddle.setLayoutY(this.oppPaddle.getLayoutY() + 6.4); // TODO paddle speed, make this a variable
		}
		
		// Ensure paddle does not leave the playing field
		if (oppPaddle.getLayoutY() < TOP_WALL) {
			oppPaddle.setLayoutY(TOP_WALL);
		} else if (oppPaddle.getLayoutY() > BOTTOM_WALL - oppPaddle.getHeight()) {
			oppPaddle.setLayoutY(BOTTOM_WALL - oppPaddle.getHeight());
		}
		
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
	 * The wallCollision method detects ball contact with the ceiling or floor
	 * 
	 * @return true if in contact, false otherwise
	 */
	private boolean wallCollision() {
		return (ball.getLayoutY() <= (TOP_WALL + ball.getRadius())) || (ball.getLayoutY() >= (BOTTOM_WALL - ball.getRadius())); // FIXME: Ball sometimes gets stuck in floor or ceiling if paddle pushes it into it
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
		if (Integer.parseInt(plyScore.getText()) == MAX_POINTS) { // If player won
			displayResults(true);
		} else if (Integer.parseInt(oppScore.getText()) == MAX_POINTS) { // If opponent won
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
			gameResultMsg.setText("YOU WIN"); // FIXME MVC
		} else {
			gameResultMsg.setText("YOU LOSE"); // FIXME MVC
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
		
		if ((currentTime - lastBounceTime) > BOUNCE_COOLDOWN) { // If elapsed time since last bounce is longer than cooldown timer
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
