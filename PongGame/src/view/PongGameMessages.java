package view;

/**
 * The PongGameMessages class contains the dynamic messages displayed during game execution
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class PongGameMessages {

	/**
	 * The default GameViewMessages constructor
	 */
	public PongGameMessages() { }
	
	
	/**
	 * Inform the player that they won
	 * 
	 * @return winning message
	 */
	public String plyWinMsg() {
		return "YOU WIN";
	}
	
	
	/**
	 * Inform the player that they lost
	 * 
	 * @return losing message
	 */
	public String plyLoseMsg() {
		return "YOU LOSE";
	}
	
}
