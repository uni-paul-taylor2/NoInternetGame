import java.awt.*;
import java.io.File;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.sound.sampled.*;
import JavaGameEngine.*;

/**
 * A game entity.
 *
 * @author (Paul Taylor)
 * @version (7th/2/2025)
 */
public class Dinosaur implements CompositeGameObject
{
    //added sound management here because why not ;-;
    public static Clip jumpSound=SoundManager.getAudio("sounds/Jump.wav");
    public static Clip failSound=SoundManager.getAudio("sounds/Crash.wav");
    public static Clip perFiveSecSound=SoundManager.getAudio("sounds/Achievement.wav");
    public static Clip perHalfSecSound=SoundManager.getAudio("sounds/Steps.wav");
    public static Clip backgroundMusic=SoundManager.getAudio("sounds/MusicLoop.wav");
    
    private GameObject leftLeg;
    private GameObject rightLeg;
    private GameObject tail;
    private GameObject head;
    private GameObject mouth;
    private GameObject upperBody;
    private GameObject lowerBody;
    private GameObject rod; //invisible shape used for aligning to ground
    private boolean jumping;
    private boolean gameEnds=false;
    private int gameTick;
    private int tickMarker=0;
    private double jumpSpeed;
    private double origJumpSpeed(){return -0.35*GRAVITY;}
    private double GRAVITY;
    private double rodX=50, rodY=200;
    private double legX=+0, legY=+0, headX=+10, headY=-20, mouthX=+25, mouthY=-15, uTorsoX=+5, uTorsoY=-20, lTorsoX=-10, lTorsoY=-10, tailX=-30, tailY=0;
    
    @Override
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
    @Override
    public void removeFromPanel(GamePanel p){
        p.removeItem(rod);
        p.removeItem(leftLeg);
        p.removeItem(rightLeg);
        p.removeItem(upperBody);
        p.removeItem(mouth);
        p.removeItem(head);
        p.removeItem(tail);
        p.removeItem(lowerBody);
    }
    public boolean jump(){
        if(jumping) return false;
        SoundManager.playAudio(jumpSound);
        jumpSpeed = origJumpSpeed();
        jumping = true;
        rod.setAcceleration(0,GRAVITY);
        return true;
    }
    public boolean land(){
        if(!jumping) return false;
        jumpSpeed = 0;
        jumping = false;
        rod.setAcceleration(0,0,true);
        return true;
    }
    
    public Dinosaur()
    {
        jumpSpeed = 0;
        GRAVITY=Constants.GRAVITY;
        rod = new GameObject(new Rectangle2D.Double(rodX,rodY,5,20),new Color(0,0,0,0),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                if(tick==2) SoundManager.playAudio(backgroundMusic,true);
                if(tick>0){
                    int rate=Constants.TICK_RATE;
                    if(tick%(rate*5)==0) SoundManager.playAudio(perFiveSecSound);
                    if(tick-tickMarker >= rate*0.5){
                        tickMarker = tick;
                        SoundManager.playAudio(perHalfSecSound);
                    }
                }
                boolean justStartedJumping = origJumpSpeed()==speedY;
                if(!justStartedJumping){
                    for(GameObject gameObject: collisions){
                        if(gameObject instanceof Ground){
                            land();
                            break;
                        }
                    }
                }
                onGameTickDefault(tick,collisions);
            }
            @Override
            public void onKeyPress(KeyEvent k){
                if(!jumping){
                    jump();
                    speedY = jumpSpeed;
                }
            }
        };
        Path2D.Double leftLegShape = new Path2D.Double();
        leftLegShape.append(new Rectangle2D.Double(rodX+legX,rodY+legY,5,20),false);
        leftLeg = new GameObject(leftLegShape,new Color(174,93,4),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
                double runningSpeed = tick*(16.0/Constants.TICK_RATE);
                rotate(Math.sin(runningSpeed) * Math.PI/4,  rod.getX()+2.5,  rod.getY());
            }
        };
        Path2D.Double rightLegShape = new Path2D.Double();
        rightLegShape.append(new Rectangle2D.Double(rodX+legX,rodY+legY,5,20),false);
        rightLeg = new GameObject(rightLegShape,new Color(174,93,4),true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
                double runningSpeed = tick*(16.0/Constants.TICK_RATE);
                rotate(Math.sin(Math.PI+runningSpeed) * Math.PI/4,  rod.getX()+2.5,  rod.getY());
            }
        };
        
        RoundRectangle2D.Double tRect = new RoundRectangle2D.Double(rodX+tailX,rodY+tailY,30,7,5,5);
        RoundRectangle2D.Double uRect = new RoundRectangle2D.Double(rodX+uTorsoX,rodY+uTorsoY,15,30,4,4);
        Path2D tailShape = GameObject.Rotate(tRect,0.17*Math.PI,rodX+tailX+25,tRect.getCenterY());
        RoundRectangle2D.Double headShape = new RoundRectangle2D.Double(rodX+headX,rodY+headY,20,20,4,4);
        RoundRectangle2D.Double mouthShape = new RoundRectangle2D.Double(rodX+mouthX,rodY+mouthY,12,12,3,3);
        Path2D upperBodyShape = GameObject.Rotate(uRect,0.3*Math.PI,uRect.getCenterX(),uRect.getCenterY());
        RoundRectangle2D.Double lowerBodyShape = new RoundRectangle2D.Double(rodX+lTorsoX,rodY+lTorsoY,20,20,5,5);
        
        tail = new GameObject(tailShape,  new Color(97,0,0),  true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
            }
        };
        head = new GameObject(headShape,  new Color(2,48,32),  true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
            }
        };
        mouth = new GameObject(mouthShape,  new Color(97,0,0),  true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
            }
        };
        upperBody = new GameObject(upperBodyShape,  new Color(2,48,32),  true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
            }
        };
        lowerBody = new GameObject(lowerBodyShape,  new Color(2,48,32),  true){
            @Override
            public void onGameTick(int tick, ArrayList<GameObject> collisions){
                endGameIfCollision(collisions);
                onGameTickDefault(tick,collisions);
            }
        };
        mouth.attatchTo(rod);
        head.attatchTo(rod);
        leftLeg.attatchTo(rod);
        rightLeg.attatchTo(rod);
        upperBody.attatchTo(rod);
        lowerBody.attatchTo(rod);
        tail.attatchTo(rod);
    }
    
    private void endGameIfCollision(ArrayList<GameObject> collisions){
        if(gameEnds) return;
        for(GameObject item: collisions){
            if(
                item==rod || item==leftLeg || item==rightLeg || item==upperBody || item==mouth
                || item==head || item==tail || item==lowerBody || (item instanceof Ground)
            ) continue;
            gameEnds = true;
            break;
        }
        if(!gameEnds) return;
        backgroundMusic.stop();
        SoundManager.playAudio(failSound);
        Game.stop();
    }
}
