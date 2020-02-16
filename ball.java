import java.awt.event.ActionEvent;      //the ActionEvent is generated when a button is clicked or the item of a list is doubled clicked
import java.awt.event.ActionListener;   //the ActionListener is notified whenever you click on the button or menu item.
import java.awt.Graphics;               //the Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components
import java.awt.event.KeyEvent;         //the KeyEvent class generates events: KEY_PRESSED, KEY_RELEASED, KEY_TYPED
import java.awt.Color;                  //the Color class states colors in the default RGB color space
import javax.swing.*;

public class Ball extends JPanel implements ActionListener
{
    // Global Variables
    private final int init_ball_x = 495;
    private final int init_ball_y = 305;
    private int x = 0, y = 0;
    private int delay = 2;
    private double speedX = randomDirection (), speedY = randomDirection ();
    private Timer t = new Timer (delay, this);
    
    public boolean oneTimeStart = true;
    private boolean scoreGuard = true;

    // Recalls the initBall method
    public Ball ()
    {
	initBall ();
    } // Constructor


    // Sets the x and y values
    private void initBall ()
    {
	x = 495;
	y = 305;
    }


    // Draws the ball
    public void draw (Graphics g)
    {
	g.setColor (Color.white);
	g.fillOval (x, y, 25, 25);
    }


    // Using a method to return the x value for the ball
    public int getX ()
    {
	return x;
    }


    // Using a method to return the y value for the ball
    public int getY ()
    {
	return y;
    }


    // Used to randomize which direction of which the ball should go onces spacebar is pressed
    public int randomDirection ()
    {
	int random = (int) (Math.random () * 2);
	if (random == 1)
	    return 1;
	else
	    return -1;
    }


    // Used to stop the ball from scoring multiple of times
    public boolean stopMultiScore ()
    {
	return scoreGuard;
    }


    public void setGuard (boolean tempState)
    {
	scoreGuard = tempState;
    }


    // Restart method to reset the ball location and stop the timer when oneTimeStart is true.
    // Starts the timer when oneTimeStart is false.
    private void restart ()
    {
	if (oneTimeStart == true)
	{
	    resetState ();
	    t.stop ();
	}
	else if (oneTimeStart == false)
	{
	    t.start ();
	}
    }


    // The "move" method for the ball.
    // It bounces off the top and bottom borders, recalls the restart method if ball goes off either side of the dimensions.
    public void actionPerformed (ActionEvent e)
    {

	if (x < 0 || x > 1000 - 10)
	{
	    oneTimeStart = true;
	    restart ();
	}
	if (y < 10 || y > 633)
	{
	    speedY = -speedY;
	}

	x += speedX;
	y += speedY;
    }


    // Checks the ball collision with both paddles
    // If collides, the ball bounces off the other direction
    public void checkCollision (Paddle1 pad1, Paddle2 pad2)
    {
	if (x <= 45)
	{
	    if (y >= pad1.getY () && y <= pad1.getY () + 120)
	    {
		speedX = -speedX;
	    }
	}
	else if (x >= 935)
	{
	    if (y >= pad2.getY () && y <= pad2.getY () + 120)
	    {
		speedX = -speedX;
	    }
	}
    }


    // Timer stops when the ball goes off either side
    // The restart method is called, and when the spacebar is pressed, the ball begins moving.
    public void keyPressed (KeyEvent e)
    {
	int key = e.getKeyCode ();

	if (key == KeyEvent.VK_SPACE)
	{
	    oneTimeStart = false;
	    restart ();
	    scoreGuard = true;
	}
    }


    // Ball reset state.
    // When called, the X value will be returned to the initial x location, y value will be returned to initial y location.
    // Once timer starts, the direction of the ball will be determined randomly with speedX and speedY.
    private void resetState ()
    {
	x = init_ball_x;
	y = init_ball_y;
	speedX = randomDirection ();
	speedY = randomDirection ();
	oneTimeStart = true;
    }
}
