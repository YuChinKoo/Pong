import java.awt.event.KeyEvent;         //the KeyEvent class generates events: KEY_PRESSED, KEY_RELEASED, KEY_TYPED
import java.awt.Graphics;               //the Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components
import java.awt.*;

public class Paddle1
{
    // Global Variables
    private final int init_paddle_x = 10;
    private final int init_paddle_y = 250;
    private int dx, dy;
    private int x, y = 250;
    private boolean scoreGuard = true;

    public Paddle1 ()
    {
        x = 10;
    } // Constructor


    // Draws the paddle at x and y location.
    public void draw (Graphics g)
    {
        g.setColor (Color.white);
        g.fillRect (x, y, 25, 120);
    }


    // Enables the paddle to move up or down
    public void move ()
    {
        y = y + dy;

        if (y < 15)
            y = 15;
        if (y > 535)
            y = 535;
    }


    // Returns the x value for the paddle
    public int getX ()
    {
        return x;
    }


    // Returns the y value for the paddle
    public int getY ()
    {
        return y;
    }


    // When W or S is pressed, the paddle moves up or down.
    public void keyPressed (KeyEvent e)
    {
        int key = e.getKeyCode ();

        if (key == KeyEvent.VK_W)
        {
            dy = -1;    // When pressed, moves up by 1
        }


        if (key == KeyEvent.VK_S)
        {
            dy = 1;     // When pressed, moves down by 1
        }
    }


    // When W or S is released, the paddle will stop moving.
    public void keyReleased (KeyEvent e)
    {
        int key = e.getKeyCode ();

        if (key == KeyEvent.VK_W)
        {
            dy = 0;         // When released, sets the dy variable to 0
        }

        if (key == KeyEvent.VK_S)
        {
            dy = 0;         // When released, sets the dy variable to 0
        }
    }
}


