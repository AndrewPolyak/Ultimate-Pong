package controller;

import java.security.SecureRandom;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Ball;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version TODO
 */
public class PongController {

	private Text plyScore;
	private Text oppScore;
	private AnimationTimer animate;
	private SecureRandom random;
	private Circle ball;
	AnchorPane gameEndPane;
	Text gameResultMsg;
	
	private double VELOCITY_INCREASE = 0.5;
	
	private int TOP_WALL = 75;
	private int BOTTOM_WALL = 575;
	private int LEFT_WALL = 0;
	private int RIGHT_WALL = 1000;
	
	private double BALL_X_SPAWN_START = 502;
	private double BALL_Y_SPAWN_START = 325;
	
	private double ballXSpawn = BALL_X_SPAWN_START;
	private double ballYSpawn = BALL_Y_SPAWN_START;
	
	private double X_VELOCITY_START = 10.0; // Controls the horizontal speed of the ball**
	private double Y_VELOCITY_START = 10.0; // Controls the vertical speed of the ball**
	
	private double Y_VELOCITY_MIN = Y_VELOCITY_START; // Min ball Y velocity
	private double Y_VELOCITY_MAX = 2.0; // Max ball Y velocity
	
	private double xVelocity = X_VELOCITY_START;
	private double yVelocity = Y_VELOCITY_START;
	
	private boolean directionRight;
	private int directionVertical;
	
	private boolean gameOn;
	
	
	public PongController(Circle ball, Text plyScore, Text oppScore, AnchorPane gameEndPane, Text gameResultMsg) {
		this.random = new SecureRandom();
		this.ball = ball;
		this.plyScore = plyScore;
		this.oppScore = oppScore;
		this.gameEndPane = gameEndPane;
		this.gameResultMsg = gameResultMsg;
		this.directionRight = random.nextBoolean();
		this.directionVertical = random.nextInt(3);
		this.gameOn = true;
	}
	
	
	public void startAnimation() {
		animate = new AnimationTimer() {
			@Override
			public void handle(long now) {
				moveBall();
			}
		};
		this.animate.start();
	}
	
	
	private void moveBall() {
		if (gameOn) {
			directBall();
			bounceBall();
			score();
			endGame();
		}
	}


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
	
	
	private void bounceBall() {
		
		// If ball touches floor or ceiling, make it bounce off of that surface
		if (this.ball.getLayoutY() <= (TOP_WALL + ball.getRadius())) { // If ball touches the ceiling, bounce it down & increase speed
			increaseSpeed();
			
			this.yVelocity = -this.yVelocity; // Reverse y direction
			this.ball.setLayoutY(this.ball.getLayoutY() - (this.yVelocity)); // Go down
			
		} else if (this.ball.getLayoutY() >= (BOTTOM_WALL - ball.getRadius())) { // If ball touches floor, bounce it up & increase speed
			increaseSpeed();
			
			this.yVelocity = -this.yVelocity; // Reverse y direction
			this.ball.setLayoutY(this.ball.getLayoutY() + this.yVelocity); // Go up
		}
	}
	
	
	private void increaseSpeed() {
		// Increase speed
		this.xVelocity += this.VELOCITY_INCREASE;
		
		if (this.ball.getLayoutX() > 500) { // If the ball is on the right side of the field
			this.ball.setLayoutX(this.ball.getLayoutX() + this.xVelocity); // Go right, faster
		} else if (this.ball.getLayoutX() < 500) { // If the ball is on the left side of the field
			this.ball.setLayoutX(this.ball.getLayoutX() - this.xVelocity); // Go left, faster
		}
	}
	
	
	private void score() {
		if ((ball.getLayoutX() - ball.getRadius()) <= LEFT_WALL) {
			plyScore.setText((Integer.parseInt(plyScore.getText()) + 1) + "");
			resetBall();
			moveBall();
		} else if ((ball.getLayoutX() + ball.getRadius()) >= RIGHT_WALL) {
			oppScore.setText((Integer.parseInt(oppScore.getText()) + 1) + "");
			resetBall();
			moveBall();
		}
	}
	
	
	private void resetBall() {
		
		boolean randYSpawn= random.nextBoolean(); // If true, then spawn skew will be added, else it will be subtracted
		this.ballYSpawn = BALL_Y_SPAWN_START;
		
		// Randomize the location of the ball spawns
		if (randYSpawn) { // Skew spawn downward
			this.ballYSpawn += random.nextInt(125);
		} else { // Skew spawn upward
			this.ballYSpawn -= random.nextInt(125);
		}
		
		this.yVelocity = random.nextDouble() * (Y_VELOCITY_MAX - Y_VELOCITY_MIN) + Y_VELOCITY_MIN; // Randomize Y velocity (angle of ball trajectory)
		
		this.ball.setLayoutX(ballXSpawn);
		this.ball.setLayoutY(ballYSpawn);
		this.directionRight = random.nextBoolean();
		this.directionVertical = random.nextInt(3);
		this.xVelocity = X_VELOCITY_START;
	}
	
	
	private void endGame() {
		if (Integer.parseInt(plyScore.getText()) == 10) {
			gameOn = false;
			this.ballXSpawn = BALL_X_SPAWN_START;
			this.ballYSpawn = BALL_Y_SPAWN_START;
			resetBall();
			displayResults(true);
		} else if (Integer.parseInt(oppScore.getText()) == 10) {
			gameOn = false;
			this.ballXSpawn = BALL_X_SPAWN_START;
			this.ballYSpawn = BALL_Y_SPAWN_START;
			resetBall();
			displayResults(false);
		}
	}
	
	
	private void displayResults(boolean won) {
		this.xVelocity = 0;
		this.yVelocity = 0;
		this.gameEndPane.setVisible(true);
		
		if (won) {
			gameResultMsg.setText("YOU WIN"); // FIXME MVC
		} else {
			gameResultMsg.setText("YOU LOSE"); // FIXME MVC
		}
	}

}
