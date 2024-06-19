package view;

/**
 * The LoginMenuMessages class contains the dynamic messages displayed in the login menu
 * 
 * @author Andrew Polyak
 * @version June 18, 2024
 */
public class LoginMenuMessages {

	/**
	 * The default LoginMenuMessages constructor
	 */
	public LoginMenuMessages() { }


	/**
	 * @return a message informing the user that they need to enter values into the fields
	 */
	public String fieldsBlankMsg() {
		return "Please fill out the fields";
	}
	
	
	/**
	 * @return a message informing the user that they need to enter a username
	 */
	public String usernameBlankMsg() {
		return "Enter a username";
	}
	
	
	/**
	 * @return a message informing the user that they need to enter a password
	 */
	public String passwordBlankMsg() {
		return "Enter a password";
	}


	/**
	 * @return a message informing the user that the account they entered does not exist
	 */
	public String AccountDoesNotExistMsg() {
		return "Account does not exist";
	}
	
}
