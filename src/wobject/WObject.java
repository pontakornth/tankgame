package wobject;

public abstract class WObject {
    /**
     * Base world object
     */
    // movement attributes
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    // other attributes
    protected int lifePoint;


    WObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

    WObject(int x, int y, int lifePoint) {
        this(x, y);
        this.lifePoint = lifePoint;
    }

    public boolean hit(WObject wObject) {
      return  x == wObject.x && y == wObject.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    // update to next stage
    public abstract void update();

    public abstract String getSpriteFileName();

    /**
     * If solid that's mean it is passable.
     * @return The WObject is solid or not.
     */
    public boolean isSolid() {
        return true;
    }
}
