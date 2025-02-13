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
public class Cactus implements CompositeGameObject
{
    private static double slideSpeed=120;
    private static double origSlideSpeed=120;
    public static void setSlideSpeed(double speed){slideSpeed=speed;}
    public static double getSlideSpeed(){return slideSpeed;}
    private GameObject left;
    private GameObject right;
    private GameObject horizontal;
    private GameObject vertical;
    private double leftX=600, leftY=219; //the point every other part of the cactus is attatched to
    private double rightX=+6, rightY=+0, hX=+0, hY=+3, vX=+3, vY=-3;
    GamePanel panel;
    //above are all the shapes of the cacti
    public void addToPanel(GamePanel p){
        panel = p;
        p.addItem(left,true,true);
        p.addItem(right,true,true);
        p.addItem(horizontal,true,true);
        p.addItem(vertical,true,true);
    }
    public void removeFromPanel(GamePanel p){
        panel = p;
        p.removeItem(left);
        p.removeItem(right);
        p.removeItem(horizontal);
        p.removeItem(vertical);
    }
    public Cactus(double size) 
    {
        rightX *= size;
        rightY *= size;
        hX *= size;
        hY *= size;
        vX *= size;
        vY *= size;
        
        leftX += origSlideSpeed-slideSpeed;
        leftY -= size*8;
        left = new GameObject(new RoundRectangle2D.Double(leftX,leftY,size*2,size*4,5,5), Color.GREEN, true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                speedX = -slideSpeed;
                if(right.getX()+(size*2) < 0){
                    removeFromPanel(panel);
                    return;
                }
                onGameTickDefault(tick,collisions);
            }
        };
        right = new GameObject(new RoundRectangle2D.Double(leftX+rightX,leftY+rightY,size*2,size*4,5,5), Color.GREEN, true);
        horizontal = new GameObject(new RoundRectangle2D.Double(leftX+hX,leftY+hY,size*8,size*2,5,5), Color.GREEN, true);
        vertical = new GameObject(new RoundRectangle2D.Double(leftX+vX,leftY+vY,size*2,size*12,5,5), Color.GREEN, true);
        right.attatchTo(left);
        horizontal.attatchTo(left);
        vertical.attatchTo(left);
    }
}