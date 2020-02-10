import java.awt.Color;                  //the Color class states colors in the default RGB color space
import java.awt.Font;                   //the Font class represents a font
import java.awt.Graphics;               //the Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components
import java.awt.Graphics2D;
import java.awt.Toolkit;                //the Toolkit class is the abstract superclass of every implementation in the Abstract Window Toolkit
import java.awt.event.ActionEvent;      //the ActionEvent is generated when a button is clicked or the item of a list is doubled clicked
import java.awt.event.ActionListener;   //the ActionListener is notified whenever you click on the button or menu item.
import java.awt.event.KeyAdapter;       //the KeyAdapter class receives keyboard events
import java.awt.event.KeyEvent;         //the KeyEvent class generates events: KEY_PRESSED, KEY_RELEASED, KEY_TYPED

import javax.swing.JPanel;              //the JPanel class is a generic lightweight container
import javax.swing.Timer;               //the Timer class handles thread sharing
import java.util.TimerTask;
import javax.sound.sampled.*;
import java.io.File;

public class Board extends JPanel implements ActionListener
{
    // Global Variables
    private Timer timer;
    private Ball ball;
    private Paddle1 pad1;
    private Paddle2 pad2;
    private final int delay = 1;
    private String str = "Press SPACEBAR to start";
    private String p1con1 = "Player 1 Controls";
    private String p1con2 = "W - UP";
    private String p1con3 = "S - DOWN";
    private String p2con1 = "Player 2 Controls";
    private String p2con2 = "UP ARROW KEY";
    private String p2con3 = "DOWN ARROW KEY";
    private String pong = "PONG";
    private static Clip clip;

    Font verdanabold = new Font ("Verdana", Font.BOLD, 30);
    Font verdanabold1 = new Font ("Verdana", Font.BOLD, 70);

    private int p1score = 0, p2score = 0;

    public Board ()
    {
        initBoard ();
    } // Constructor


    public void initBoard ()
    {
        addKeyListener (new TAdapter ());   // Invokes keylistener methods
        setBackground (Color.black);    // sets background to black
        setFocusable (true);    // Sets the focus on the panel (board)
        ball = new Ball ();     // Uses ball program located within the same folder
        pad1 = new Paddle1 ();  // Uses paddle1 program located within the same folder
        pad2 = new Paddle2 ();  // Uses paddle2 program located within the same folder
        timer = new Timer (delay, this);    // Enables animation
        timer.start ();
    }


    // Used everytime someone scores a poing.
    // Added in because I wanted to know how to input music and why not?
    private static void music ()
    {
        try
        {
            AudioInputStream ding = AudioSystem.getAudioInputStream (new File ("Ding_Sound_Effect.wav").getAbsoluteFile ());
            DataLine.Info info = new DataLine.Info (Clip.class, ding.getFormat ());
            clip = (Clip) AudioSystem.getLine (info);
            clip.open (ding);
            clip.start ();
        }
        catch (Exception ex)
        {
            System.out.println ("Error with playing sound. Please check that all files are in the folder.");
            ex.printStackTrace ();
        }
    }


    // Recalls doDrawing method, draws all graphics within the method
    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        doDrawing (g);
        Toolkit.getDefaultToolkit ().sync ();
    }


    // All graphics are located within this method. Draws ball, both paddles, scoreboard, controls, etc.
    public void doDrawing (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor (Color.white);
        g2.fillRect (0, -2, 1050, 15);
        g2.fillRect (0, 657, 1050, 15);
        pad1.draw (g);
        pad2.draw (g);
        ball.draw (g);
        g2.setFont (verdanabold);

        // Ball starts moving when user presses the spacebar (disappears when pressed)
        if (ball.oneTimeStart == true)
        {
            g2.drawString (str, 300, 295);
        }
        else if (ball.oneTimeStart == false)
        {
        }

        // Displays player 1 and 2 controls at the start of program
        if (ball.oneTimeStart == true)
        {
            g2.drawString (p1con1, 100, 150);
            g2.drawString (p1con2, 200, 180);
            g2.drawString (p1con3, 210, 210);
            g2.drawString (p2con1, 640, 150);
            g2.drawString (p2con2, 640, 180);
            g2.drawString (p2con3, 640, 210);
            g2.setFont (verdanabold1);
            g2.drawString (pong, 385, 100);
        }
        else if (ball.oneTimeStart == false)
        {
            p1con1 = p1con1.replaceAll ("Player 1 Controls", "");
            p1con2 = p1con2.replaceAll ("W - UP", "");
            p1con3 = p1con3.replaceAll ("S - DOWN", "");
            p2con1 = p2con1.replaceAll ("Player 2 Controls", "");
            p2con2 = p2con2.replaceAll ("UP ARROW KEY", "");
            p2con3 = p2con3.replaceAll ("DOWN ARROW KEY", "");
            g2.setFont (verdanabold1);
            pong = pong.replaceAll ("PONG", "");
        }
        // Decor
        for (int a = 0 ; a < 20 ; a++)
        {
            g2.fillRect (500, (13 + a * 35), 15, 15);
        }
        g2.setFont (verdanabold1);
        // Draws scoreboard
        g2.drawString (p1score + "", 200, 100);
        g2.drawString (p2score + "", 750, 100);

        // Either player wins when they reach 11 points. Screen blackens and text displays.
        if (p2score == 11)
        {
            timer.stop ();
            g2.setColor (Color.black);
            g2.fillRect (0, 0, 1000, 700);
            g2.setColor (Color.white);
            g2.drawString ("PLAYER 2 WINS", 180, 250);
        }
        else if (p1score == 11)
        {
            timer.stop ();
            g2.setColor (Color.black);
            g2.fillRect (0, 0, 1000, 700);
            g2.setColor (Color.white);
            g2.drawString ("PLAYER 1 WINS", 180, 250);
        }
    }


    // This method is called every 'delay' millisecs; it increases player 1 and 2 scores.
    // It moves the ball, paddles, checks for collision, and repaints the screen.
    public void actionPerformed (ActionEvent e)
    {

        if (ball.getX () < 0 && ball.stopMultiScore ())
        {
            music ();
            ball.setGuard (false);
            p2score++;
            System.out.println ("P2: " + p2score);
        }
        else if (ball.getX () > 990 && ball.stopMultiScore ())
        {
            music ();
            ball.setGuard (false);
            p1score++;
            System.out.println ("P1: " + p1score);
        }
        pad1.move ();
        pad2.move ();
        ball.checkCollision (pad1, pad2);
        repaint ();
    }


    // The ScheduleTask is triggered every Period milliseconds.
    // In its run() method, it moves the ball, and the paddles.
    // It checks for possible collisions and repaint the screen.
    private class ScheduleTask extends TimerTask
    {
        public void run ()
        {
            pad1.move ();
            pad2.move ();
            ball.checkCollision (pad1, pad2);
            repaint ();
        }
    }


    // This class listens for key events
    private class TAdapter extends KeyAdapter
    {
        public void keyReleased (KeyEvent e)
        {
            pad1.keyReleased (e);
            pad2.keyReleased (e);
        }


        public void keyPressed (KeyEvent e)
        {
            pad1.keyPressed (e);
            pad2.keyPressed (e);
            ball.keyPressed (e);
        }
    }
}


