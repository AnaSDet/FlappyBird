# Flappy Bird Clone

## About the Project

This project is a recreation of the classic Flappy Bird game using Java and Swing. The game features a bird that the player can control to navigate through pipes. The objective is to pass through as many pipes as possible without colliding.

## Technologies Used

* Java
* Swing (for GUI)
* AWT (for graphics and event handling)
* Timer (for game loop and pipe placement)

## Features

* Bird that responds to player input (spacebar to flap)
* Randomly generated pipes
* Score tracking
* Game over state with restart option
* Custom graphics for bird, pipes, and background

## Running The Project

To run the project:

1. Ensure you have Java installed on your system
2. Clone the repository
3. Navigate to the project directory
4. Compile the Java files: 
   javac App.java and FlappyBird.java
5. Run the compiled program: 
   java App

## Game Controls

* Press SPACE to make the bird flap
* Press SPACE after game over to restart the game

## Project Structure

* `App.java`: Contains the main method and sets up the game window
* `FlappyBird.java`: Contains the game logic, drawing, and event handling

## Graphics

The game uses custom images for:
* Bird
* Pipes (top and bottom)
* Background

Note: Ensure all image files are in the correct directory relative to the Java files.


## Acknowledgements

This project was created as a learning exercise in Java game development and does not intend to infringe on any existing copyrights. All graphics used are for educational purposes only.
