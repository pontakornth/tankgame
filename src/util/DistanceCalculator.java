package util;

import wobject.WObject;

public class DistanceCalculator {
    public static int manhattanDist(WObject o1, WObject o2) {
        return Math.abs(o1.getX() - o2.getX()) + Math.abs(o1.getY() - o2.getY());
    }

    public static int manhattanDist(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
}