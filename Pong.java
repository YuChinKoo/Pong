// The "Pong" class.
// By: Eugene Koo
////////////////////// PONG GAME //////////////////////
// In this game, 2 players moves a paddle on the screen
// The objective is to get the ball to go off your opponent's screen and to reach 11 points
// You have to prevent the ball from going off your side of the screen
// There is 2 paddles, and one ball
// This game uses a timer to create a game cycle
// The winner will be decided once either player reaches 11 points.
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Pong extends JFrame
{
    public Pong ()
    {
        setSize (1000, 700);   // Set the frame's size
        setTitle ("Pong");    // Title of the frame
        getContentPane ().add (new Board ());   // add the 'Board' panel to the window
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);    // Application closes when you click on the close button
        setResizable (false);   // Window cannot be resized
        setLocationRelativeTo (null);   // Centers the window on the user's screen

    } // Constructor


    public static void main (String[] args)
    {
        EventQueue.invokeLater (new Runnable ()
        {
            public void run ()
            {
                JFrame c = new Pong (); // Create a Pong frame
                c.setVisible (true);    // Makes the window visible
            }
        }
        );
    } // main method
} // Pong class
