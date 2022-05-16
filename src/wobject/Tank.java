package wobject;

import java.util.HashMap;
import java.util.Map;

public class Tank extends WObject implements Movable{

    public Tank(int x, int y, int lifePoint) {
       super(x, y, lifePoint);
       // TODO: Add information about side.
       directionImageFileName = new HashMap<>();
       direction = Direction.North;
       directionImageFileName.put(Direction.North, "./img/blue-tank-front.png");
       directionImageFileName.put(Direction.South, "./img/blue-tank-back.png");
       directionImageFileName.put(Direction.East, "./img/blue-tank-right.png");
       directionImageFileName.put(Direction.West, "./img/blue-tank-left.png");
    }

    public Tank(int x, int y, int lifePoint, Faction faction) {
        super(x,y);
        this.lifePoint = lifePoint;
        directionImageFileName = new HashMap<>();
        direction = Direction.North;
        directionImageFileName.put(Direction.North, getFileName(faction, "front"));
        directionImageFileName.put(Direction.South, getFileName(faction, "back"));
        directionImageFileName.put(Direction.East, getFileName(faction, "right"));
        directionImageFileName.put(Direction.West, getFileName(faction, "left"));

    }

    private static String getFileName(Faction faction, String direction) {
        return String.format("./img/%s-tank-%s.png", faction, direction);
    }
    private Direction direction;
    private Map<Direction, String> directionImageFileName;


    public void fire() {
        // TODO: implement bullet fire method here
    }

    @Override
    public void turnNorth() {
        direction = Direction.North;
        dx = 0;
        dy = -1;
    }

    @Override
    public void turnSouth() {
        direction = Direction.South;
        dx = 0;
        dy = 1;
    }

    @Override
    public void turnEast() {
        direction = Direction.East;
        dx = 1;
        dy = 0;
    }

    @Override
    public void turnWest() {
        direction = Direction.West;
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

    @Override
    public String getSpriteFileName() {
        // TODO: Change name based on type.
        return directionImageFileName.getOrDefault(direction, "./img/blue-tank-front.png");
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction getDirection() {
        return direction;
    }
    // TODO: Add functionality to tank.
}
