package wobject;

public class Tank extends WObject implements Movable{

    public Tank(int x, int y, int lifePoint) {
        super(x, y, lifePoint);
    }

    public void fire() {
        // TODO: implement bullet fire method here
    }

    @Override
    public void turnNorth() {
        dx = 0;
        dy = -1;
    }

    @Override
    public void turnSouth() {
        dx = 0;
        dy = 1;
    }

    @Override
    public void turnEast() {
        dx = 1;
        dy = 0;
    }

    @Override
    public void turnWest() {
        dx = -1;
        dy = 0;
    }

    @Override
    public void setStop() {
        dx = 0;
        dy = 0;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
    }
}
