import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //store all of the pipes in our game
import java.util.Random; //placing our pipes at random positions
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener{ //inherrit JPanel class and implement action listener nd add KeyListener interface
    int boardWidth = 360;
    int boardHeight = 640;

    //Images. all of these variables are going to store our Image objects
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //bird class
    int birdX = boardWidth/8;
    int birdY = boardWidth/2;
    int birdWidth = 34;
    int birdHeight = 24;


    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        //define a constructor for bird 
        Bird(Image img) {
            this.img = img;  
        }
    }

    //pipe class
    int pipeX = boardWidth; //on the right side of our board
    int pipeY = 0; // start from the top of our screen and on the right ide of our screen
    int pipeWidth = 64; //scaled by 1/6
    int pipeHeight = 512;


    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; //this checks if FlappyBird passed the pipe 
        
        //pipe constructor
        Pipe(Image img) {
            this.img = img;
        }
    }

    //Game Logic. I will add a field/variable for bird. 
    Bird bird;
    int velocityX = -4; //move pipes to the left speed (simulates bird moving right)
    int velocityY = 0; //move bird up/down speed.
    int gravity = 1;  //in every frame the bird will slow down by 1 pixel

    //create array var for array, because we have a lot of pipes in our game and we need to store them in the list
    ArrayList<Pipe> pipes;
    Random random = new Random();

    //creating var. for the game loop (60 frames per second). This is going to be a timer
    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    double score = 0; //keeping track of the score (every time we pass the pipe, we increment our score by 1)

    //create a constructor
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        // setBackground(Color.blue);
        setFocusable(true); //this is going to make sure that FlappyBird(JPannel) class is going to make sure that this is the one that takes in our key events 
        addKeyListener(this); //it's going to make sure that we check the 3 functions

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

    //bird object
    bird = new Bird(birdImg);
    pipes = new ArrayList<Pipe>(); //array list of pipes

    //place pipes timer
    placePipeTimer = new Timer(1500, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          // Code to be executed
          placePipes();
        }
    });
    placePipeTimer.start();

    //game timer object
    gameLoop = new Timer(1000/60, this);  //in millisec 1000/60 = 16.6. and this is going to refer to the FlappyBird class

    //start our timer
    gameLoop.start();
}

   //Array list to call a Pipe function
   void placePipes() {
    // (0-1) * pipeHeight/2 -> it will give us a random number between 0 and 256
    //128
    //0 - 128 - (0 - 256) -> pipeHeight/4  -> 3/4 of the pipeHeight

    int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
    int openingSpace = boardHeight/4; //addig a space so that bird has a space to fly between the pipes
    
    Pipe topPipe = new Pipe(topPipeImg);
    topPipe.y = randomPipeY;
    pipes.add(topPipe);

    Pipe bottomPipe = new Pipe(bottomPipeImg); //pass the bottom pipe image
    bottomPipe.y = topPipe.y  + pipeHeight + openingSpace;
    pipes.add(bottomPipe);
   }
  

    //define a function for JPanel(it's going to invoke the function from JPanel)
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
        //draw the background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        //draw the bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);
        
        //draw the pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score function
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    //define a move function. (update all the X & Y positions)
    public void move(){
        //bird
        velocityY += gravity; //update velocity with gravity
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); //bird doesn't move over the screen (upwards)

        //pipes
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX; //update the X posittion with the velocity X(it's -4)
        
        //checking if the bird not pass the pipe, 
        if (!pipe.passed && bird.x > pipe.x + pipe.width) {
            score += 0.5; //0.5 because there are 2 pipes! so 0.5*2 = 1, 1 for each set of pipes
            pipe.passed = true;
        }

        //if the bird hits the pipe = game over
        if (collision(bird, pipe)) {
            gameOver = true;
        }
    }

    //game over function
    if (bird.y > boardHeight) {
        gameOver = true;
     }
    }

    //create a function for detecting collision
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }


    @Override
    public void actionPerformed(ActionEvent e) { //this is going to be "action performed" every 16 milliseconds(60 times a second)
        //update position of the bird
        move(); //call the move() function (runs 60 times per sec)

        //and it's going to be a paint component function
        repaint(); // this will call paint component

        //stop the adding more pipes
        if (gameOver) {
            placePipeTimer.stop();
            gameLoop.stop(); // stop repainting and updating the frames and end the game
        }
    }

    //Key Listener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // System.out.println("JUMP!");
            velocityY = -9;

            if (gameOver) {
                //restart the game by resetting conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    //do not needed
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
