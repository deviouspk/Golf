package Game;

/**
 * Created by tony on 16/03/2016.
 */
public class PhysicsEngine {
    private Course course;
    private Ball ball;

    private final double GROUND_FRICTION = Config.GROUND_FRICTION;
    private final double AIR_FRICTION = Config.AIR_FRICTION;
    private final double GRAVITY_FORCE = Config.GRAVITY_FORCE;
    private final double WALL_ENERGY_LOSS = Config.WALL_ENERGY_LOSS;


    public static boolean enable3D = Config.ENABLED3D;

    public void init(Course course, Ball ball) {
        this.course = course;
        this.ball = ball;
    }

    public void processNaturalForces() {

        /*********************************************/
        /** Process Ground & Air Friction + Gravity **/
        /*********************************************/

        /* Check if ball is on the ground else it's in the air */
        if (ball.getCoordinate().getZ() <= 1) {
            ball.speedX *= GROUND_FRICTION;
            ball.speedY *= GROUND_FRICTION;
            if (enable3D)
                ball.speedZ *= GROUND_FRICTION;
            //System.out.println("ball is on the ground");
        } else {
            ball.speedX *= AIR_FRICTION;
            if (enable3D)
                ball.speedZ *= AIR_FRICTION;
            ball.speedY *= AIR_FRICTION;

            if (enable3D)
                ball.speedZ -= GRAVITY_FORCE;
        }
    }

    public double calculateAngle(double speedX, double speedY) {
        double angle;
        angle = Math.atan(speedY / speedX);
        return angle;
    }


    public void checkColission() {
        double angle = calculateAngle(ball.getSpeedX(), ball.getSpeedY());
        double coordinateX, coordinateY, futureXCoordinate, futureYCoordinate;
        Type nextBallCoordinateType = null;

        if (Math.abs(ball.getSpeedX()) > Math.abs(ball.getSpeedY())) {
            System.out.println("SpeedX bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedX()); i += 5) {
                Game.dp.repaint();
                coordinateX = i;
                coordinateY = (i * Math.tan(angle));
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {

                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {

                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate - ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        ball.getCoordinate().setX(futureXCoordinate);
                        ball.getCoordinate().setX(futureYCoordinate);
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();

                        break forloop;
                    }
                }

            }

        } else {
            System.out.println("SpeedY bigger");
            forloop:
            for (int i = 0; i <= Math.abs(ball.getSpeedY()); i += 1) {
                Game.dp.repaint();
                coordinateX = (i / Math.tan(angle));
                coordinateY = i;
                futureXCoordinate = (ball.getCoordinate().getX() + coordinateX);
                futureYCoordinate = (ball.getCoordinate().getY() + coordinateY);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futureXCoordinate + ball.getRadius() >= Config.getWidth() || futureXCoordinate - ball.getRadius() <= 0 && futureYCoordinate + ball.getRadius() >= Config.getHeight() || futureYCoordinate - ball.getRadius() <= 0) {
                    System.out.println("bigger!!!!!");
                    break forloop;
                } else {
                    nextBallCoordinateType = course.getTile((int) (futureXCoordinate + ball.getRadius()), (int) (futureYCoordinate + ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();
                    System.out.println(1 + (i / 5) + ". coordinateX: " + futureXCoordinate + " coordinateY: " + futureYCoordinate);
                    if (nextBallCoordinateType == Type.OBJECT) {
                        System.out.println("colission!");
                        ball.speedX *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionX();
                        ball.speedY *= WALL_ENERGY_LOSS;
                        ball.reverseBallDirectionY();
                        break forloop;
                    }
                }

            }
        }
        /*
        try {
            Type nextBallCoordinateType = null;

            if (ball.getSpeedX() > ball.getSpeedY()) {
                nextBallCoordinateType = course.getTile((int) (ball.getCoordinate().getX() + ball.getSpeedX()), (int) (ball.getCoordinate().getY() + ball.getSpeedY() - ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

            } else {
                nextBallCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius() + ball.getSpeedX()), (int) (ball.getCoordinate().getY() + ball.getSpeedY()), (int) ball.getCoordinate().getZ()).getType();
            }

            if (nextBallCoordinateType == Type.OBJECT) {
                System.out.println("colission!");
                ball.speedX *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionX();
                ball.speedY *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionY();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

    }


    public void processPhysics() {
        Type ballCoordinateType = null;
        Coordinate b = ball.getCoordinate();
        double ballSpeed = ball.getSpeed();
        Hole h = course.getHole();
        /*********************************/
        /** Process Hole **/
        /*********************************/
        //t = (2*Rh - R)/vf
        //Rh = radius of hole
        //R = radius ball
        //        vf = speed of ball when it reaches the hole
        //g*t^2/2 > R
        //g = gravity = 9.81
        //or expressed in vf: vf < (2Rh - R)(g / 2R)^1/2

        // checkColission();

        if (Math.abs(b.getX() - h.getX()) <= ball.getRadius() + h.radius && Math.abs(b.getY() - h.getY()) <= ball.getRadius() + h.radius) {
            double distance = Math.sqrt((b.getX() - h.getX()) * (b.getX() - h.getX()) + (b.getY() - h.getY()) * (b.getY() - h.getY()));

            if (distance + ball.getRadius() < +h.radius) {
                //inAir
                if (ballSpeed < (2 * ball.radius - h.radius) * Math.sqrt(10 / 2 * h.radius)) {
                    ball.speedX = 0;
                    ball.speedZ = 0;
                    ball.speedY = 0;
                }
            } else if (distance <= ball.getRadius() + h.radius) {
                Coordinate c = new Coordinate(h.getX() - b.getX(), h.getY() - b.getY(), h.getZ() - b.getZ());
                double factor = (1 - distance / (ball.getRadius() + h.radius)) * h.getFriction();
                ball.redirect(c, factor);

            }

        }


        /*********************************/
        /** Process Border Colissions X **/
        /*********************************/

        // check right X border
        if (ball.getCoordinate().getX() + ball.getSpeedX() >= course.getWidth() - ball.getRadius()) {
            ball.getCoordinate().setX(course.getWidth() - ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit right border");
        }
        //check left X border
        else if (ball.getCoordinate().getX() + ball.getSpeedX() <= ball.getRadius()) {
            ball.getCoordinate().setX(ball.getRadius());
            ball.speedX *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionX();
            System.out.println("ball hit left border");
        }
        // Add speed if no colission
        else {
            ball.getCoordinate().setX(ball.getCoordinate().getX() + ball.getSpeedX());
        }

        /*********************************/
        /** Process Border Colissions Y **/
        /*********************************/

        // check bottom border
        if (ball.getCoordinate().getY() + ball.getSpeedY() >= course.getHeight() - ball.getRadius()) {
            ball.getCoordinate().setY(course.getHeight() - ball.getRadius());
            ball.speedY *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionY();
            //System.out.println(ball.getRadius() + " ball hit bottom border");

        }

        //check top border
        else if (ball.getCoordinate().getY() + ball.getSpeedY() <= ball.getRadius()) {
            ball.getCoordinate().setY(ball.getRadius());
            ball.speedY *= WALL_ENERGY_LOSS;
            ball.reverseBallDirectionY();
            System.out.println("ball hit top border");
        } else {
            ball.getCoordinate().setY(ball.getCoordinate().getY() + ball.getSpeedY());
        }

        /*********************************/
        /** Process Border Colissions Z **/
        /*********************************/
        if (enable3D) {
            //check back border
            if (ball.getCoordinate().getZ() >= course.getLength() - ball.getRadius()) {
                ball.getCoordinate().setZ(course.getLength() - ball.getRadius());
                ball.speedZ *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionZ();
                System.out.println("ball hit back border");
            }
            //check front border
            else if (ball.getCoordinate().getZ() + ball.getSpeedZ() <= ball.getRadius()) {
                ball.getCoordinate().setZ(ball.getRadius());
                ball.speedZ *= WALL_ENERGY_LOSS;
                ball.reverseBallDirectionZ();
                System.out.println("ball hit front border");
            }
            // Add speed if no colission
            else {
                ball.getCoordinate().setZ(ball.getCoordinate().getZ() + ball.getSpeedZ());
            }

        }

        /*********************************/
        /** Process Object Colissions **/
        /*********************************/
        if (Math.abs(ball.getSpeedX()) >= Math.abs(ball.getSpeedY())) {
            if (ball.getSpeedX() > 0) {
                ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() + ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from the left");
                }
            } else {
                ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from the right");

                }
            }
        } else {
            if (ball.getSpeedY() < 0) {
                ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() + ball.getRadius()), (int) (ball.getCoordinate().getY() - ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - 2*ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() -2* ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from below");

                }
            } else if (ball.getSpeedY() >= 0) {
                ballCoordinateType = course.getTile((int) (ball.getCoordinate().getX() - ball.getRadius()), (int) (ball.getCoordinate().getY() + ball.getRadius()), (int) ball.getCoordinate().getZ()).getType();

                if (ballCoordinateType == Type.OBJECT) {
                    ball.getCoordinate().setX(ball.getCoordinate().getX() - 2*ball.getSpeedX());
                    ball.getCoordinate().setY(ball.getCoordinate().getY() - 2*ball.getSpeedY());
                    ball.reverseBallDirectionX();
                    ball.reverseBallDirectionY();
                    System.out.println("ball hit OBJECT from up");

                }

            }
        }
    }
}