package view;

/**
 * The RegisterMenuMessages class contains the dynamic messages displayed in the register menu
 * 
 * @author Andrew Polyak
 * @version June 18, 2024
 */
public class RegisterMenuMessages {

	/**
	 * The default RegisterMenuMessages constructor
	 */
	public RegisterMenuMessages() { }
	
	
	/**
	 * @return a message about an invalid username length
	 */
	public String nameLengthMsg() {
		return "Name must be between 3 & 25 chars";
	}
	
	
	/**
	 * @return a message informing the user that the entered username already exists
	 */
	public String nameAlreadyExistsMsg() {
		return "Username already exists";
	}

	
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
	
}
