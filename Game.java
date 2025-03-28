import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import JavaGameEngine.*;

/**
 * The game's entry point.
 *
 * @author (Paul Taylor)
 * @version (7th/2/2025)
 */
public class Game
{
    private static GamePanel panel;
    private static JLabel pointsLabel;
    private static boolean stopped = true;
    private static boolean pausedOnce = false;
    private static ScrollingImage background = null;
    private static void start(){
        if(!stopped) return;
        background = new ScrollingImage("images/Scroll-Background.png");
        Ground ground = new Ground(panel);
        Dinosaur dino = new Dinosaur();
        panel.start();
        panel.addItem(background);
        panel.addItem(dino);
        panel.addItem(ground,true,true);
        stopped = false;
    }
    public static void stop(){
        if(stopped) return;
        panel.stop();
        stopped = true;
        pointsLabel.setText("Game Over: "+panel.getTick()+" points! Press any key to restart");
    }
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setSize(Constants.DEFAULT_PANEL_WIDTH, Constants.DEFAULT_PANEL_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel(){
            @Override
            public void onKeyDown(KeyEvent e){
                Game.start();
                if(getTick()==1 && pausedOnce) resume();
            }
            @Override
            public void perTickCallback(){
                pointsLabel.setText("Points: "+getTick());
                background.scroll(-0.6*(Cactus.getSlideSpeed()/Constants.TICK_RATE), 0);
                if(getTick()!=1) return;
                if(!pausedOnce) pause();
                pausedOnce = true;
                pointsLabel.setText("Press any key to play :D");
            }
        };
        JPanel pointsPanel = new JPanel();
        pointsLabel = new JLabel("Points: 0");
        pointsPanel.add(pointsLabel);
        frame.setLayout(new BorderLayout());
        frame.add(pointsPanel, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
        start();
    }
}
