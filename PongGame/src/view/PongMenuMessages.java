package view;

/**
 * The PongMenuMessages class contains the dynamic messages displayed in the pre-game menu
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class PongMenuMessages {

	/**
	 * The default PongMenuMessages constructor
	 */
	public PongMenuMessages() { }
	
	
	/**
	 * Welcome the user back
	 * 
	 * @param username
	 * @return welcome message
	 */
	public String welcomeMsg(String username) {
		return "WELCOME BACK " + username;
	}
	
	
	/**
	 * Display the user's win count
	 * 
	 * @param winCount
	 * @return win count message
	 */
	public String winCountMsg(String winCount) {
		return winCount + " WINS";
	}
	
}
