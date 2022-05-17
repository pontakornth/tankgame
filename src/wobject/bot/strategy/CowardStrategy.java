package wobject.bot.strategy;

import util.DirUtils;
import wobject.*;
import wobject.command.CommandFactory;
import wobject.command.GameCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.min;
import static util.DistanceCalculator.manhattanDist;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;
import static wobject.command.CommandFactory.getRandomMove;

public class CowardStrategy extends Strategy {

    public CowardStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        Bullet bullet = null;
        Tank botTank = getBotTank();
        DirUtils dirUtils = new DirUtils();
        // load closest bullet
        try {
            bullet = getIncomingBullet().get(0);
            Direction direction = bullet.getDirection();
        } catch (Exception e) {

        }

        // dodge the bullet
        if (bullet != null) {
            if (bullet.getX() + bullet.getDx() == botTank.getX() && bullet.getY() + bullet.getDy() == botTank.getY()) {
                Direction dir = dirUtils.getDirWithout(dirUtils.getOppositeDir(bullet.getDirection()));
                switch (dir) {
                    case North:
                        return getMoveCommand(world, botNumber, MoveNorth);
                    case South:
                        return getMoveCommand(world, botNumber, MoveSouth);
                    case East:
                        return getMoveCommand(world, botNumber, MoveEast);
                    case West:
                        return getMoveCommand(world, botNumber, MoveWest);
                }
            }

            // destroy the bullet
            if (bullet.getX() + 3 * bullet.getDx() == botTank.getX()
                    && bullet.getY() + 3 * bullet.getDy() == botTank.getY()
                    && bullet.getDirection() == dirUtils.getOppositeDir(botTank.getDirection())
            ) {
                return getMoveCommand(world, botNumber, MoveFire);
            }
        }

        // hiding in trees
        WObject trees = getClosestObject();
        if (trees != null && trees instanceof Trees) {
            if (bullet != null && !isSameSpot(new Bullet(bullet.getX()+bullet.getDx(), bullet.getY()+bullet.getDy()), trees)) {
                return moveToObject(trees);
            }
        }

        // base command
        return getMoveCommand(world, botNumber, MoveStop);
    }

    private List<Bullet> getIncomingBullet() {
        // get all incoming bullets or bullets moving closer to tank
        Tank botTank = getBotTank();
        List<Bullet> incomingBullets = new ArrayList<Bullet>();
        for (Bullet bullet : world.getBullets()) {
            if (isBulletCloser(botTank, bullet)) {
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

    private WObject getClosestObject() {
        List<WObject> objects = world.getTiles();
        objects.sort(new Comparator<WObject>() {
            @Override
            public int compare(WObject o1, WObject o2) {
                return manhattanDist(getBotTank(), o1) - manhattanDist(getBotTank(), o2);
            }
        });
        return objects.get(0);
    }

    private GameCommand moveToObject(WObject object) {
        Tank botTank = getBotTank();
        if(isSameSpot(botTank, object)) {
            return getMoveCommand(world, botNumber, MoveStop);
        }

        HashMap<CommandFactory.CommandEnum, Integer> commandMap = new HashMap<>();
        commandMap.put(MoveNorth, manhattanDist(object.getX(), object.getY(), botTank.getX(), botTank.getY()-1));
        commandMap.put(MoveSouth, manhattanDist(object.getX(), object.getY(), botTank.getX(), botTank.getY()+1));
        commandMap.put(MoveEast, manhattanDist(object.getX(), object.getY(), botTank.getX()+1, botTank.getY()));
        commandMap.put(MoveWest, manhattanDist(object.getX(), object.getY(), botTank.getX()-1, botTank.getY()));

        int localMin = 999999;
        CommandFactory.CommandEnum commandEnum = null;
        for(CommandFactory.CommandEnum c: commandMap.keySet()) {
            if (localMin > commandMap.get(c)) {
                localMin = commandMap.get(c);
                commandEnum = c;
            }
        }
        return getMoveCommand(world, botNumber, commandEnum);
    }

    private boolean isBulletCloser(Tank botTank, Bullet bullet) {
        int currentDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX(), bullet.getY());
        int nextDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX() + bullet.getDx(), bullet.getY() + bullet.getDy());
        return nextDist <= currentDist;
    }

    private boolean isSameSpot(WObject o1, WObject o2) {
        return o1.getX() == o2.getX() && o1.getY() == o2.getY();
    }

    private Tank getBotTank() {
        return world.getTanks().get(botNumber);
    }
}
