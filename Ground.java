import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import JavaGameEngine.*;

/**
 * A game entity.
 *
 * @author (Paul Taylor)
 * @version (7th/2/2025)
 */
public class Ground extends GameObject
{
    GamePanel panel;
    private int lastSpawned;
    public Ground(GamePanel instance)
    {
        super(new Rectangle2D.Double(0,219,599,179),new Color(92,67,39),true);
        panel = instance;
        Cactus.resetSlideSpeed();
    }
    @Override
    public void onGameTick(int tick, ArrayList<GameObject> collisions){
        //logic to randomly spawn cacti of different sizes here
        double increment = Constants.TICK_RATE*2.5;
        double range = Constants.TICK_RATE*1.5;
        double gap = Constants.TICK_RATE*0.6;
        if(tick>0 && tick%increment==0) Cactus.setSlideSpeed(Cactus.getSlideSpeed()+10);
        if(tick-lastSpawned>(Math.random()*range+gap)) spawnRandomCactus(tick,0.8);
        onGameTickDefault(tick,collisions);
    }
    public void spawnRandomCactus(int tick){
        Cactus plant = new Cactus((Math.random()*2)+2);
        plant.addToPanel(panel);
        lastSpawned = tick;
    }
    public void spawnRandomCactus(int tick, double chance){
        if(Math.random()<=chance) spawnRandomCactus(tick);
    }
}