package model;

/**
 * User represents a user account, including username, password, and pong win count
 * 
 * @author Andrew Polyak
 * @version June 7, 2024
 */
public class User {
	
	private String username;
	private String password;
	private int numPongWins;
	
	
	/**
	 * The User constructor initializes the username, password, and number of pong wins for an account
	 * 
	 * @param username
	 * @param password
	 * @param numPongWins
	 */
	public User(String username, String password, int numPongWins) {
		this.username = username;
		this.password = password;
		this.numPongWins = numPongWins;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getNumPongWins() {
		return numPongWins;
	}


	public void setNumPongWins(int numPongWins) {
		this.numPongWins = numPongWins;
	}
	
	
	/**
	 * Format user data in a way readable and writable by the database
	 * 
	 * @return
	 */
	public String formatUserData() {
		return username + ";" + password + ";" + numPongWins + "\n";
	}
}
