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
	
	private double VELOCITY_INCREASE = 0.5;
	
	private int TOP_WALL = 79;
	private int BOTTOM_WALL = 575;
	private int LEFT_WALL = 0;
	private int RIGHT_WALL = 1000;
	
	private double PLY_PADDLE_X = 830;
	private double OPP_PADDLE_X = 170;
	
	private double BALL_X_SPAWN_START = 502;
	private double BALL_Y_SPAWN_START = 325;
	
	private double ballXSpawn = BALL_X_SPAWN_START;
	private double ballYSpawn = BALL_Y_SPAWN_START;
	
	private double X_VELOCITY_START = 1.0; // Controls the horizontal speed of the ball**
	private double Y_VELOCITY_START = 1.0; // Controls the vertical speed of the ball**
	
	private double Y_VELOCITY_MIN = Y_VELOCITY_START; // Min ball Y velocity
	private double Y_VELOCITY_MAX = 2.0; // Max ball Y velocity
	
	private double xVelocity = X_VELOCITY_START;
	private double yVelocity = Y_VELOCITY_START;
	
	private boolean directionRight;
	private int directionVertical;
	
	private boolean gameOn;
	
	
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
		this.directionVertical = random.nextInt(3);
		this.gameOn = true;
	}
	
	
	/**
	 * TODO
	 */
	public void startAnimation() {
		this.animate = new AnimationTimer() {
			
			/**
			 * TODO
			 */
			@Override
			public void handle(long now) {
				moveBall();
				movePlyPaddle();
			}
		};
		this.animate.start();
	}
	
	
	/**
	 * TODO
	 */
	private void moveBall() {
		if (this.gameOn) {
			this.directBall();
			this.bounceBall(); // FIXME, doesn't detect paddle on first ball movement
			this.score();
			this.endGame();
		}
	}
	
	
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
	private void directBall() {
		// Pick the horizontal movement
		if (this.directionRight) {
			this.ball.setLayoutX(this.ball.getLayoutX() + this.xVelocity); // Go right
			
		} else {
			this.ball.setLayoutX(this.ball.getLayoutX() - this.xVelocity); // Go left
		}
		
		/*
		 * Pick the vertical movement (will make movement diagonal)
		 * 1 represents down, 2 represents up, and 3 represents neither (default straight horizontal)
		 */
		if (directionVertical == 1) {
			this.ball.setLayoutY(this.ball.getLayoutY() + this.yVelocity); // Go down
			
		} else if (this.directionVertical == 2) {
			this.ball.setLayoutY(this.ball.getLayoutY() - this.yVelocity); // Go up
		}
	}
	
	
	/**
	 * TODO
	 */
	private void bounceBall() {
		// Handle floor & ceiling bouncing
		if (this.ball.getLayoutY() <= (TOP_WALL + this.ball.getRadius())) { // If ball touches the ceiling, bounce it down & increase speed
			this.increaseSpeed();
			this.yVelocity = -this.yVelocity; // Reverse y direction
			this.ball.setLayoutY(this.ball.getLayoutY() - (this.yVelocity)); // Go down
			
		} else if (this.ball.getLayoutY() >= (this.BOTTOM_WALL - this.ball.getRadius())) { // If ball touches floor, bounce it up & increase speed
			this.increaseSpeed();
			this.yVelocity = -this.yVelocity; // Reverse y direction
			this.ball.setLayoutY(this.ball.getLayoutY() + this.yVelocity); // Go up
		}
		
		// Handle paddle bouncing
		// If ball touches the player's paddle (right side), bounce it left & increase speed 
		if (((this.ball.getLayoutX() >= this.PLY_PADDLE_X - 7.5) && (this.ball.getLayoutX() <= this.PLY_PADDLE_X + 7.5)) && // If the ball & paddle are within the same x-coords
				(((this.plyPaddle.getLayoutY()) <= this.ball.getLayoutY() + this.ball.getRadius()) && // If the ball is below the top of the paddle
				(this.plyPaddle.getLayoutY() + plyPaddle.getHeight()) >= this.ball.getLayoutY() + this.ball.getRadius())) { // If the ball is above the bottom of the paddle
			this.increaseSpeed();
			this.bounceOffPaddle();
			System.out.println("ply");
			
		// If ball touches the opponent's paddle (left side), bounce it right & increase speed 
		} else if (((this.ball.getLayoutX() >= this.OPP_PADDLE_X - 7.5) && (this.ball.getLayoutX() <= this.OPP_PADDLE_X + 7.5)) && // If the ball & paddle are within the same x-coords
				(((this.oppPaddle.getLayoutY()) <= this.ball.getLayoutY() + this.ball.getRadius()) && // If the ball is below the top of the paddle
				(this.oppPaddle.getLayoutY() + oppPaddle.getHeight()) >= this.ball.getLayoutY() + this.ball.getRadius())) { // If the ball is above the bottom of the paddle
			this.increaseSpeed();
			this.bounceOffPaddle();
			System.out.println("opp");
		}
	}
	
	
	/**
	 * TODO
	 */
	private void increaseSpeed() {
		// Increase speed
		this.xVelocity += this.VELOCITY_INCREASE;
		
		if (this.ball.getLayoutX() > 500) { // If the ball is on the right side of the field
			this.ball.setLayoutX(this.ball.getLayoutX() + this.xVelocity); // Go right, faster
			
		} else if (this.ball.getLayoutX() < 500) { // If the ball is on the left side of the field
			this.ball.setLayoutX(this.ball.getLayoutX() - this.xVelocity); // Go left, faster
		}
	}
	
	
	/**
	 * TODO
	 */
	private void score() {
		if ((this.ball.getLayoutX() - this.ball.getRadius()) <= this.LEFT_WALL) {
			this.plyScore.setText((Integer.parseInt(this.plyScore.getText()) + 1) + "");
			this.resetBall();
			this.moveBall();
			
		} else if ((this.ball.getLayoutX() + this.ball.getRadius()) >= this.RIGHT_WALL) {
			this.oppScore.setText((Integer.parseInt(this.oppScore.getText()) + 1) + "");
			this.resetBall();
			this.moveBall();
		}
	}
	
	
	/**
	 * TODO
	 */
	private void resetBall() {
		boolean randYSpawn = this.random.nextBoolean(); // If true, then spawn skew will be added, else it will be subtracted
		this.ballYSpawn = this.BALL_Y_SPAWN_START;
		
		// Randomize the location of the ball spawns
		if (randYSpawn) { // Skew spawn downward
			this.ballYSpawn += this.random.nextInt(125);
			
		} else { // Skew spawn upward
			this.ballYSpawn -= this.random.nextInt(125);
		}
		
		this.yVelocity = this.random.nextDouble() * (this.Y_VELOCITY_MAX - this.Y_VELOCITY_MIN) + this.Y_VELOCITY_MIN; // Randomize Y velocity (angle of ball trajectory)
		
		this.ball.setLayoutX(this.ballXSpawn);
		this.ball.setLayoutY(this.ballYSpawn);
		this.directionRight = this.random.nextBoolean();
		this.directionVertical = this.random.nextInt(3);
		this.xVelocity = this.X_VELOCITY_START;
	}
	
	
	/**
	 * TODO
	 */
	private void endGame() {
		if (Integer.parseInt(this.plyScore.getText()) == 10) {
			this.gameOn = false;
			this.ballXSpawn = this.BALL_X_SPAWN_START;
			this.ballYSpawn = this.BALL_Y_SPAWN_START;
			this.resetBall();
			this.displayResults(true);
			
		} else if (Integer.parseInt(oppScore.getText()) == 10) {
			this.gameOn = false;
			this.ballXSpawn = this.BALL_X_SPAWN_START;
			this.ballYSpawn = this.BALL_Y_SPAWN_START;
			this.resetBall();
			this.displayResults(false);
		}
	}
	
	
	/**
	 * TODO
	 * 
	 * @param won
	 */
	private void displayResults(boolean won) {
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.gameEndPane.setVisible(true);
		
		if (won) {
			this.gameResultMsg.setText("YOU WIN"); // FIXME MVC
			
		} else {
			this.gameResultMsg.setText("YOU LOSE"); // FIXME MVC
		}
	}
	
	
	/**
	 * TODO
	 */
	private void bounceOffPaddle() {
		// TODO
	}
	

}
