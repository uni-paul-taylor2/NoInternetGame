import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import JavaGameEngine.*;

/**
 * A game entity.
 *
 * @author (Paul Taylor)
 * @version (7th/2/2025)
 */
public class Dinosaur
{
    private GameObject leftLeg;
    private GameObject rightLeg;
    private GameObject tail;
    private GameObject head;
    private GameObject mouth;
    private GameObject upperBody;
    private GameObject lowerBody;
    private GameObject rod; //invisible shape used for aligning to ground
    private GameObject ground;
    private boolean jumping;
    private int gameTick;
    private double jumpSpeed;
    private double origJumpSpeed(){return -0.4*GRAVITY;}
    private double GRAVITY;
    private double rodX=50, rodY=200;
    private double legX=+0, legY=+0, headX=+10, headY=-20, mouthX=+25, mouthY=-15, uTorsoX=+5, uTorsoY=-20, lTorsoX=-10, lTorsoY=-10, tailX=-30, tailY=0;
    
    public void addToPanel(GamePanel p){
        p.addItem(rod,true,true);
        p.addItem(leftLeg,true,true);
        p.addItem(rightLeg,true,true);
        p.addItem(upperBody,true,true);
        p.addItem(mouth,true,true);
        p.addItem(head,true,true);
        
        p.addItem(tail,true,true);
        p.addItem(lowerBody,true,true);
        
    }
    public boolean jump(){
        if(jumping) return false;
        jumpSpeed = origJumpSpeed();
        jumping = true;
        return true;
    }
    public boolean land(){
        if(!jumping) return false;
        jumpSpeed = 0;
        jumping = false;
        return true;
    }
    
    public Dinosaur(Ground instance)
    {
        jumpSpeed = 0;
        GRAVITY=Constants.GRAVITY;
        ground = instance;
        rod = new GameObject(new Rectangle2D.Double(rodX,rodY,5,20),new Color(0,0,0,0),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                boolean justStartedJumping = origJumpSpeed()==jumpSpeed;
                if(jumping) jumpSpeed+=GRAVITY/Constants.TICK_RATE;
                speedY = jumpSpeed;
                if(!justStartedJumping){
                    for(GameObject gameObject: collisions){
                        if(gameObject == ground){
                            land();
                            speedY = jumpSpeed;
                            break;
                        }
                    }
                }
                onGameTickDefault(tick,collisions);
            }
            @Override
            public void onKeyPress(KeyEvent k){
                jump();
                speedY = jumpSpeed;
            }
        };
        Path2D.Double leftLegShape = new Path2D.Double();
        leftLegShape.append(new Rectangle2D.Double(rodX+legX,rodY+legY,5,20),false);
        leftLeg = new GameObject(leftLegShape,new Color(174,93,4),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                onGameTickDefault(tick,collisions);
                rotate(Math.sin(tick*0.8) * Math.PI/4,  rod.getX()+2.5,  rod.getY());
            }
        };
        Path2D.Double rightLegShape = new Path2D.Double();
        rightLegShape.append(new Rectangle2D.Double(rodX+legX,rodY+legY,5,20),false);
        rightLeg = new GameObject(rightLegShape,new Color(174,93,4),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                onGameTickDefault(tick,collisions);
                rotate(Math.sin(Math.PI+(tick*0.8)) * Math.PI/4,  rod.getX()+2.5,  rod.getY());
            }
        };
        RoundRectangle2D.Double tRect = new RoundRectangle2D.Double(rodX+tailX,rodY+tailY,30,7,5,5);
        tail = new GameObject(GameObject.Rotate(tRect,0.17*Math.PI,rodX+tailX+25,tRect.getCenterY()),  new Color(97,0,0),  true);
        head = new GameObject(new RoundRectangle2D.Double(rodX+headX,rodY+headY,20,20,4,4),  new Color(2,48,32),  true);
        mouth = new GameObject(new RoundRectangle2D.Double(rodX+mouthX,rodY+mouthY,12,12,3,3),  new Color(97,0,0),  true);
        RoundRectangle2D.Double uRect = new RoundRectangle2D.Double(rodX+uTorsoX,rodY+uTorsoY,15,30,4,4);
        upperBody = new GameObject(GameObject.Rotate(uRect,0.3*Math.PI,uRect.getCenterX(),uRect.getCenterY()),  new Color(2,48,32),  true);
        lowerBody = new GameObject(new RoundRectangle2D.Double(rodX+lTorsoX,rodY+lTorsoY,20,20,5,5),  new Color(2,48,32),  true);
        mouth.attatchTo(rod);
        head.attatchTo(rod);
        leftLeg.attatchTo(rod);
        rightLeg.attatchTo(rod);
        upperBody.attatchTo(rod);
        lowerBody.attatchTo(rod);
        tail.attatchTo(rod);
    }
}
