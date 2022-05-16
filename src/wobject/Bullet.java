package wobject;

public class Bullet extends WObject implements Movable {

    private int dx;
    private int dy;

    Bullet(int x, int y) {
        super(x, y);
    }

    @Override
    public void turnNorth() {
         dy = -1;
         dx = 0;
    }

    @Override
    public void turnSouth() {
        dy = 1;
        dx = 0;
    }

    @Override
    public void turnEast() {
        dy = 0;
        dx = 1;
    }

    @Override
    public void turnWest() {
        dy = 0;
        dx = -1;
    }

    @Override
    public void setStop() {
        dy = 0;
        dx = 0;
    }

    @Override
    public int getDx() {
        return dx;
    }

    @Override
    public int getDy() {
        return dy;
    }

    @Override
    public void update() {
        x += dx;
        y += dy;
    }

    @Override
    public String getSpriteFileName() {
        return null;
    }

    public void refreshState(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public boolean isOutsideBorder(int borderHorizontalSize, int borderVerticalSize) {
        return (x < 0 || x >= borderHorizontalSize || y < 0 || y >= borderVerticalSize);
    }
}
