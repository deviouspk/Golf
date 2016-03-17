package Game;

public class Ball {

    //Color c;
    double mass = 1;
    double radius = Config.getBallRadius();
    double bounciness;
    public double speedX, speedY, speedZ;
    public boolean isMoving = false;
    private Coordinate coordinate = new Coordinate();
    private PhysicsEngine physics = new PhysicsEngine();



    public Ball(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void shootBall(double speedX, double speedY, double speedZ) {
        System.out.println(inPlay());
        System.out.println(isMoving);
        if (!isMoving && inPlay()) {
            isMoving = true;
            this.speedX = speedX;
            this.speedY = speedY;
            this.speedZ = speedZ;
        } else {
            System.out.println("ball is still moving or not in play");
        }
    }

    public void shootBall3D(double speed1, double speed2, double angle) {

    }

    public boolean inPlay() {
        return true;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getSpeedZ() {
        return speedZ;
    }

    public double getRadius() {
        return radius;
    }

    public void reverseBallDirectionX() {
        speedX = -speedX;
    }
    public void reverseBallDirectionY() {
        speedY = -speedY;
    }
    public void reverseBallDirectionZ() {
        speedZ = -speedZ;
    }
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public PhysicsEngine getPhysics() {
        return physics;
    }
    public void checkBallStopped(){
        if(Math.abs(speedX)<=0.2 && Math.abs(speedY)<=0.3 && Math.abs(speedZ)<=1){
            isMoving=false;
            System.out.println("ballStopped");
        }

    }
    public void printBallInfo(){
        System.out.println("X: " + getCoordinate().getX());
        System.out.println("Y: " + getCoordinate().getY());
        System.out.println("Z: " + getCoordinate().getZ());
        System.out.println("SpeedX: " + speedX);
        System.out.println("SpeedY: " + speedY);
        System.out.println("SpeedZ: " + speedZ);
        System.out.println("ball radius: " + getRadius());
        if(!isMoving)
            System.out.println("ballStopped");
    }

    public double getSpeed() {
       return Math.sqrt(speedX*speedX+speedY*speedY+speedZ*speedZ);
    }
}