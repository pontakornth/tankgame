package wobject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import util.DirectionRandomizer;
import util.DirectionRandomizer.*;
import static wobject.Direction.*;

import static java.lang.Math.abs;

public class BotPlayer {

    private World world;
    private int botNumber;
    private DirectionRandomizer randomDir = new DirectionRandomizer();

    BotPlayer(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
    }

    public void execute() {
        // TODO: implement execute logic here!
        randomMove();
        // defense the bullet
    }

    // TODO: implement bot logic here!

    public void randomMove() {
        world.moveTank(botNumber, randomDir.getDir());
    }

    public boolean defense() {
        Bullet bullet;
        Tank botTank = getBotTank();
        // load closest bullet
        try {
            bullet = getIncomingBullet().get(0);
            Direction direction = bullet.getDirection();
            System.out.println("Bullet " + bullet.getDirection().name());
        } catch (Exception e) {
            return false;
        }
        // dodge the bullet
        if(bullet.getX() + bullet.getDx() == botTank.getX() && bullet.getY() + bullet.getDy() == botTank.getY()) {
            // TODO: implement here
            System.out.println("dodge");
            return true;
        }

        // destroy the bullet

        return true;
    }

    private List<Bullet> getIncomingBullet() {
        // get all incoming bullets or bullets moving closer to tank
        Tank botTank = getBotTank();
        List<Bullet> incomingBullets = new ArrayList<Bullet>();
        for(Bullet bullet: world.getBullets()) {
            if(isBulletCloser(botTank, bullet)) {
                incomingBullets.add(bullet);
            }
        }
        incomingBullets.sort(new Comparator<Bullet>() {
            @Override
            public int compare(Bullet o1, Bullet o2) {
                return manhattanDist(o1, o2);
            }
        });
        return incomingBullets;
    }

    private boolean isBulletCloser(Tank botTank, Bullet bullet) {
        int currentDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX(), bullet.getY());
        int nextDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX()+ bullet.getDx(), bullet.getY()+ bullet.getDy());
        return nextDist < currentDist;
    }

    private Tank getBotTank() {
        return world.getTanks().get(botNumber);
    }

    private int manhattanDist(WObject o1, WObject o2) {
        return abs(o1.getX() - o2.getX()) + abs(o1.getY() - o2.getY());
    }

    private int manhattanDist(int x1, int y1, int x2, int y2) {
        return abs(x1 - x2) + abs(y1 - y2);
    }
}