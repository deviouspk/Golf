package Game;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by tony on 16/03/2016.
 */
public class Main {
    public static void main(String [] args){
        Course course = new Course("Golf Deluxe", 800, 600, 1, Type.Grass, 1 );
        course.addArea(20,40,30,50,0,Type.Sand);
        ArrayList<Player> pp = new ArrayList<>(2);
        Player p = new Player("PlayerEins");
        Player p2 = new Player("PlayerZwei");
        pp.add(p);
        pp.add(p2);

        Frame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(800, 600);

        DrawPanel dp = new DrawPanel();
        frame.add(dp);

        p.setBallPosition(course.getStartTile().getCoordinate());
        dp.setPlayers(pp);
        dp.setCurrentPlayer(p);
        dp.setCourse(course);
        dp.repaint();


        PhysicsEngineFinal physics = new PhysicsEngineFinal();

        physics.init(course, p.getBall());
        while(true){
            if(p.getBall().isMoving){
            p.getBall().getPhysics().processPhysics();
                p.getBall().getPhysics().processNaturalForces();
            }
            dp.repaint();

        }
    }
}
