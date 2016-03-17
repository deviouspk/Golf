package Game;

/** All the variables are static and used to determine the game flow
 * Created by nibbla on 16.03.16.
 */
public class Config {
    public static final double maxSpeedToFallIntoHole = 3;
    private static final double ballMass = 1;
    private static final double ballRadius = 20;
    private static final double holeRadius = 35;
    private static final int width=1920;
    private static final int height = 1080;
    private static final int depth = 1;

    public final double GROUND_FRICTION = 0.90;
    public final double AIR_FRICTION = 0.995;
    public final double GRAVITY_FORCE = 9.81;
    public static final double WALL_ENERGY_LOSS = 0.7;

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
