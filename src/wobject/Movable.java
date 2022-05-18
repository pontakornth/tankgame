package wobject;

public interface Movable {
    /**
     * Interface of movable world object
     */
    public void turnNorth();
    public void turnSouth();
    public void turnEast();
    public void turnWest();
    public void setStop();
    public void update();

    // These coordinates are required for bullet and tank can share same interface.
    int getX();
    int getY();
    int getDx();
    int getDy();
}
