package Game;

/** All the variables are static and used to determine the game flow
 * Created by nibbla on 16.03.16.
 */
public class Config {

    private static final int width=1024;
    private static final int height = 768;
    private static final int depth = 1;


    private static final double ballMass = 1;
    private static final double ballRadius = 10;
    private static final double holeRadius = 24;
    public static final double GROUND_FRICTION = 0.90;
    public static final double AIR_FRICTION = 0.995;
    public static final double GRAVITY_FORCE = 9.81;
    public static final double WALL_ENERGY_LOSS = 0.7;
    public static final int OFFSET_X_GAME=16;
    public static final int OFFSET_Y_GAME=39;
    public static final int OFFSET_X_EDITOR=16;
    public static final int OFFSET_Y_EDITOR=62;

    public static final boolean ENABLED3D=false;

    public static double getBallMass() {
        return ballMass;
    }

    public static double getBallRadius() {
        return ballRadius;
    }

    public static double getHoleRadius() {
        return holeRadius;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getDepth() {
        return depth;
    }
}
