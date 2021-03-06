package util;

import wobject.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static wobject.Direction.*;

public class DirUtils {

    private Random random = new Random();
    private List<Direction> directions = Arrays.asList(North, South, East, West);

    public DirUtils() {

    }

    public Direction getDir() {
        return directions.get(random.nextInt(directions.size()));
    }

    public Direction getDirWithout(Direction notInclude) {
        Direction direction;
        while (true) {
            direction = getDir();
            if (direction != notInclude) {
                return direction;
            }
        }
    }

    public Direction getOppositeDir(Direction dir) {
        switch (dir) {
            case North:
                return South;
            case South:
                return North;
            case East:
                return West;
            case West:
                return East;
        }
        return null;
    }
}
