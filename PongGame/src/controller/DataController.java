package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.User;

/**
 * The DataController class contains the app's logic for loading the user database and saving user data to it
 * 
 * @author Andrew Polyak
 * @version June 16, 2024
 */
public class DataController {

	private ArrayList<User> users; // Represents the ArrayList containing the database of users
	private User user; // Represents an individual user from the users database
	
	private final File USER_DB = new File("res/users.txt"); // Represents the user database File
	private final String DELIMITER = ";"; // Represents the delimiter for the user database
	
	
	/**
	 * The loadUsers method reads the database of users and returns an ArrayList of User objects which represents the full database
	 * 
	 * @return users, an ArrayList containing the database of users
	 */
	public ArrayList<User> loadUsers() {
		users = new ArrayList<>();
		
		Scanner fileReader;
		String[] userData;
		
		try {
			fileReader = new Scanner(USER_DB); // Create fileReader to parse the database
			
			while (fileReader.hasNext()) { // For each data line of the database
				userData = fileReader.nextLine().split(DELIMITER); // Take the line data (user data) and split it into a List
				
				// Create a new User object from the line data (user data) and add it to the users ArrayList
				user = new User(userData[0], userData[1], Integer.parseInt(userData[2]));
				users.add(user);
			}
			fileReader.close(); // Close the reader once the database has been fully processed
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	
	/**
	 * The saveUser method takes in the users ArrayList and an updatedUser, which may or may not represent an existing user, as it may represent a new user <br>
	 * If the user exists, then their new data will represent an updated win count (via updatedUser) <br>
	 * If the user exists, then their old data will be removed from users, and their new data (via updatedUser) will be added to the users ArrayList <br>
	 * If the user is new, then their data will be added to the users ArrayList <br>
	 * From the users ArrayList, the data will be written to the users database for persistent storage
	 * 
	 * @param updatedUser
	 * @param users
	 */
	public void saveUser(User updatedUser, ArrayList<User> users) {
		
		/*
		 * existingUserIndex represents an *existing* user's index within the users ArrayList.
		 * It represents the index to remove (and subsequently update and re-add) for an existing user, 
		 * thus it is set to -1 in the case that a new user is being handled (and thus isn't on the ArrayList to begin with, and shouldn't be removed)
		 */
		int existingUserIndex = -1;
		
		/*
		 * For each user in the users ArrayList, if the user exists (i.e., the updatedUser matches a user on the ArrayList),
		 * then retrieve that existing user's index location in the ArrayList and assign that value to existingUserIndex
		 * 
		 */
		for (User user : users) {
			if (user.getUsername().equals(updatedUser.getUsername())) { // If the usernames match
				if (user.getPassword().equals(updatedUser.getPassword())) { // If the passwords match
					existingUserIndex = users.indexOf(user); // Get the index location
					break;
				}
			}
		}
		
		// If updatedUser exists within users (indicated by the existingUserIndex >=0), remove them from users
		if (existingUserIndex >= 0) {
			users.remove(existingUserIndex);
		}
		
		users.add(updatedUser); // Add the updated user to users
		
		FileWriter fileWriter;
		
		try {
			fileWriter = new FileWriter(USER_DB); // Create fileWriter to write to the database (overwriting the entire thing)
			
			// For each user in users, format their data and write it to the database
			for (User user : users) {
				fileWriter.write(user.formatUserData());
			}
			fileWriter.close(); // Close the writer once the database has been fully written to
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
