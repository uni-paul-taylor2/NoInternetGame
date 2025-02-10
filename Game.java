import javax.swing.*;
import JavaGameEngine.*;

/**
 * The game's entry point.
 *
 * @author (Paul Taylor)
 * @version (7th/2/2025)
 */
public class Game
{
    public static void main(String[] args)
    {
        Ground ground = new Ground();
        JFrame frame = new JFrame();
        frame.setSize(Constants.DEFAULT_PANEL_WIDTH, Constants.DEFAULT_PANEL_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();
        Dinosaur dino = new Dinosaur(ground);
        dino.addToPanel(panel); //this works
        panel.addItem(ground,true,true); //but ground does not?
        frame.add(panel);
        frame.setVisible(true);
    }
}
