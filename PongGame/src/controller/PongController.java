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
	
	private double ballXSpawn = BALL_X_SPAWN_START;
	private double ballYSpawn = BALL_Y_SPAWN_START;
	
	private final double X_VELOCITY_START = 4.0; // Controls the horizontal speed of the ball**
	private final double Y_VELOCITY_START = 4.0; // Controls the vertical speed of the ball**
	
	private final double Y_VELOCITY_MIN = Y_VELOCITY_START; // Min ball Y velocity
	private final double Y_VELOCITY_MAX = 6.0; // Max ball Y velocity
	
	private final double SPEED_BOOST = 1.1;
	
	private double xVelocity = X_VELOCITY_START;
	private double yVelocity = Y_VELOCITY_START;
	
	private boolean directionRight;
	private boolean directionUp;
	
	private boolean gameOn;
	
	private int numBounces = 0;
	
	
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
		this.animate = new AnimationTimer() {
			
			/**
			 * TODO
			 */
			@Override
			public void handle(long now) {
				moveBall();
				movePlyPaddle();
				moveOppPaddle();
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
	
	
	private void moveOppPaddle() {
		
		if (this.ball.getLayoutY() < (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2)) { // If ball is above paddle
			this.oppPaddle.setLayoutY(this.oppPaddle.getLayoutY() - 6.6);
			
		} else if (this.ball.getLayoutY() > (oppPaddle.getLayoutY() + oppPaddle.getHeight() / 2)) { // If ball is below paddle
			this.oppPaddle.setLayoutY(this.oppPaddle.getLayoutY() + 6.6); // TODO paddle speed, make this a variable
		}
		
		// Ensure paddle does not leave the playing field
		if (oppPaddle.getLayoutY() < TOP_WALL) {
			oppPaddle.setLayoutY(TOP_WALL);
		} else if (oppPaddle.getLayoutY() > BOTTOM_WALL - oppPaddle.getHeight()) {
			oppPaddle.setLayoutY(BOTTOM_WALL - oppPaddle.getHeight());
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
		if (!directionUp) {
			this.ball.setLayoutY(this.ball.getLayoutY() + this.yVelocity); // Go down
			
		} else {
			this.ball.setLayoutY(this.ball.getLayoutY() - this.yVelocity); // Go up
		}
	}
	
	
	/**
	 * TODO
	 */
	private void bounceBall() {
		// Handle floor & ceiling bouncing
		if ((this.ball.getLayoutY() <= (TOP_WALL + this.ball.getRadius())) ||
		(this.ball.getLayoutY() >= (this.BOTTOM_WALL - this.ball.getRadius()))) {
			this.yVelocity *= -1; // Reverse y direction
			numBounces++;
			increaseVelocity();
		} 
		
		// Handle paddle bouncing
		// If ball touches the player's paddle (right side), bounce it left & increase speed 
		if (this.ballIntersectsPaddle(this.plyPaddle)) {
			bounceOffPaddle(plyPaddle);
			numBounces++;
			increaseVelocity();
			System.out.println("ply");
			
		// If ball touches the opponent's paddle (left side), bounce it right & increase speed 
		} else if (ballIntersectsPaddle(this.oppPaddle)) {
			bounceOffPaddle(oppPaddle);
			numBounces++;
			increaseVelocity();
			System.out.println("opp");
		}
	}
	
	
	/**
	 * 
	 * @param paddle
	 * @return
	 */
	private boolean ballIntersectsPaddle(Rectangle paddle) {
        return this.ball.getBoundsInParent().intersects(paddle.getBoundsInParent());
    }
	
	
	private void increaseVelocity() {
		if (numBounces <= 9) { // max speed will be 9.4
			xVelocity *= SPEED_BOOST;
		}
		
		if (numBounces <= 5) { // max speed will be 6.4
			yVelocity *= SPEED_BOOST;
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
		boolean xandYSpawn = this.random.nextBoolean(); // If true, then spawn skew will be added, else it will be subtracted
		this.ballYSpawn = this.BALL_Y_SPAWN_START;
		
		// Randomize the location of the ball spawns
		if (xandYSpawn) { // Skew spawn downward
			this.ballYSpawn += this.random.nextInt(125);
			
		} else { // Skew spawn upward
			this.ballYSpawn -= this.random.nextInt(125);
		}
		
		this.yVelocity = this.random.nextDouble() * (this.Y_VELOCITY_MAX - this.Y_VELOCITY_MIN) + this.Y_VELOCITY_MIN; // Randomize Y velocity (angle of ball trajectory)
		
		this.ball.setLayoutX(this.ballXSpawn);
		this.ball.setLayoutY(this.ballYSpawn);
		this.directionRight = this.random.nextBoolean();
		this.directionUp = this.random.nextBoolean();
		this.xVelocity = this.X_VELOCITY_START;
		
		numBounces = 0;
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
	 * FIXME need to fix the y changing logic here
	 * FIXME need to account for when the ball hits the top middle of the paddle
	 */
	private void bounceOffPaddle(Rectangle paddle) {
		
		// Reverse x direction
		this.xVelocity *= -1;
		
		
		if (this.ball.getLayoutY() < paddle.getLayoutY() + 15) {
			if (this.yVelocity > 0) {
				this.yVelocity *= -1;
				
			}
			
		} else if (this.ball.getLayoutY() > paddle.getLayoutY() + 55) {
			if (this.yVelocity < 0) {
				this.yVelocity *= -1;
				
			}
		}
	}
	

}
