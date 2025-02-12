import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import JavaGameEngine.*;

/**
 * A game entity.
 *
 * @author (Paul Taylor)
 * @version (11th/2/2025)
 */
public class Cactus 
{
    private static double slideSpeed=10;
    public static void SetSpeedX(double speed){slideSpeed=speed;}
    private GameObject left;
    private GameObject right;
    private GameObject horizontal;
    private GameObject vertical;
    private double leftX=200, leftY=200; //the point every other part of the cactus is attatched to
    private double rightX=+6, rightY=+0, hX=+0, hY=+3, vX=+3, vY=-3;
    //above are all the shapes of the cacti
    public Cactus(int size) 
    {
        left = new GameObject(new RoundRectangle2D.Double(200,200,size*1,size*2,5,5), Color.GREEN, true);
        right = new GameObject(new RoundRectangle2D.Double(), Color.GREEN, true);
    }
}