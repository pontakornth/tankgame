package wobject;

import java.util.HashMap;
import java.util.Map;

public class Tank extends WObject implements Movable, FactionObject {

    private final Faction faction;

    public Tank(int x, int y, int lifePoint) {
       this(x, y, lifePoint, Faction.Blue);
    }

    public Tank(int x, int y, int lifePoint, Faction faction) {
        super(x,y);
        this.lifePoint = lifePoint;
        this.faction = faction;
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
    private final Map<Direction, String> directionImageFileName;


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

    @Override
    public Faction getFaction() {
        return faction;
    }

    @Override
    public boolean sameFaction(FactionObject factionObject) {
        return faction == factionObject.getFaction();
    }
}
