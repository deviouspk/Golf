package Game.Model;

import Game.Config;

/**
 * Created by nibbla on 16.03.16.
 */
public class Hole extends Tile {
    public double radius =  Config.getHoleRadius();
    public Hole(double radius, int x, int y, int z) {
        super(Type.Hole, x, y, z);
        this.radius = radius;
    }



    /**
     *
     * @param b
     * @return true if the center of the ball is within the radius of the hole
     */
    public boolean isBallIntersectingHole(Ball b){
        Coordinate bc = b.getCoordinate();
        if (Coordinate.getDistance(x, y,z, bc.getX(), bc.getY(), bc.getZ()) < (radius)) {
            return true;
        }
        return false;
    }



}
