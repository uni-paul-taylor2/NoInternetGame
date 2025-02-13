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
        Cactus cactus = new Cactus(3.5);
        cactus.addToPanel(panel);
    }
    @Override
    public void onGameTick(int tick, ArrayList<GameObject> collisions){
        //logic to randomly spawn cacti of different sizes here
        if(tick>0 && tick%250==0) Cactus.setSlideSpeed(Cactus.getSlideSpeed()+10);
        if(tick==0) spawnRandomCactus(tick);
        else if(tick-lastSpawned>50) spawnRandomCactus(tick,(tick-lastSpawned)/75);
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