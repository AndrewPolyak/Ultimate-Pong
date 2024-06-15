package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import model.User;

public class DataController {

	
	private ArrayList<User> users;
	private User user;
	private final File USER_DB = new File("res/users.txt");
	
	private final String DELIMITER = ";";
	
	
	
	public ArrayList<User> loadUsers() {
		
		users = new ArrayList<>();
		
		Scanner fileReader;
		String[] userData;
		
		try {
			fileReader = new Scanner(USER_DB);
			
			while (fileReader.hasNext()) {
				userData = fileReader.nextLine().split(DELIMITER);
				
				user = new User(userData[0], userData[1], Integer.parseInt(userData[2]));
				
				users.add(user);
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;
	}

	
	
	public void saveUser(User updatedUser, ArrayList<User> users) {
		int index = -1;
		
		
		for (User user : users) {
			if (user.getUsername().equals(updatedUser.getUsername())) { // Update existing user
				if (user.getPassword().equals(updatedUser.getPassword())) {
					index = users.indexOf(user);
					break;
				}
			}
		}
		
		if (index > -1) {
			users.remove(index);
		}
		
		users.add(updatedUser);
		
		
		FileWriter fileWriter;
		
		try {
			fileWriter = new FileWriter(USER_DB);
			
			for (User user : users) {
				fileWriter.write(user.formatUserData());
			}
			fileWriter.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
    
}
