package view;

/**
 * The GameViewMessages class contains the dynamic messages displayed during game execution
 * 
 * @author Andrew Polyak
 * @version June 7, 2024
 */
public class GameViewMessages {

	/**
	 * The default GameViewMessages constructor
	 */
	public GameViewMessages() { }
	
	
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
