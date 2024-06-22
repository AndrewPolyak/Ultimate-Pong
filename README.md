# Ultimate-Pong
Ultimate Pong is based on the classic Pong video game, and is built on Java.

The application allows a user to create and log into an account which tracks their win count of Pong, as well as play Pong itself.

This represents my first foray into game development. Though simple, Pong felt like a good way to start with video game development.

Here is an overview of Pong Ultimate:

LAUNCH SCREEN:
- This is what you will see when you open the app
- The user has the option to create a new account or log into an existing one
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/053892b1-9e50-4262-a3c0-d4de49d6ab6b)

ACCOUNT CREATION SCREEN:
- If you don't have an ccount, then you can create one here
- Validation exists to ensure both fields are filled in, the new name is between 3 and 25 characters, and the new name is not the same as an existing name
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/b975ac24-7292-48d3-81b7-2275af9c581a)

LOGIN SCREEN:
- If you already have an account, then you can login using your credentials here
- Validation exists to ensure both fields are filled in and also to check whether the account exists in the database
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/718dc38d-6662-45e9-a6fa-6cc2c8c3b864)

PRE-GAME SCREEN:
- This screen is shown once the user logs in; it takes the saved user data to display their name and win count
- The user has the option to start playing or save and exit (save and exit takes the user back to the launch screen)
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/d52d98f8-aa1f-4a48-8d44-7c24fe7fa5da)

GAME SCREEN:
- This is the actual gameplay of Pong; the mechanics and rules are very similar to the traditional game, and the first player to 10 points wins
- The user has the option to forfeit and end the game early
- The player controls their paddle with their mouse positioning
- The opponent's paddle is controlled by an algorithm that attempts to center the paddle with the ball's position, but at a set, balanced speed
- If either paddle hits the ball with the top or bottom edges of their paddles, then the ball becomes more vertically skewed, which makes it possible to beat the AI opponent
- The paddles are coded to always remain within the playing field (and so is the ball: it bounces off of the paddles and the walls)
- If you win, your account's score count is increased by one
- The balancing is good: the difficulty feels fair but also satisfyingly challenging
- Ball spawning, direction, and trajectory is randomized somewhat to keep the game interesting (the ball always spawns in the same X-coord; the Y-coord changes)
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/e598a684-9621-4e09-9b47-0b775d73168d)

POST-GAME SCREEN
- Once a player wins, the post-game screen is displayed
- The user has the option to return to the pre-game screen or play another game
- Once this screen occurs, if the user won, then their win count is updated (but not saved to the database; they need to save and exit to save their data)
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/7f444eac-27f4-4f19-be20-0c506348acde)
![image](https://github.com/AndrewPolyak/Ultimate-Pong/assets/157662133/a6452256-8119-4027-a45f-fe6813fea630)


That's it for my app! Thank you for checking out my project!
