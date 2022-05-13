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
}
