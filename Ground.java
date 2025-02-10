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
    public Ground()
    {
        super(new Rectangle2D.Double(0,219,599,179),new Color(92,67,39),true);
    }
}