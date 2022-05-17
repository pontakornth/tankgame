package wobject.bot.strategy;

import util.DirUtils;
import wobject.Bullet;
import wobject.Direction;
import wobject.Tank;
import wobject.World;
import wobject.command.GameCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static util.DistanceCalculator.manhattanDist;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class CowardStrategy extends Strategy{

    public CowardStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        Bullet bullet;
        Tank botTank = getBotTank();
        DirUtils dirUtils = new DirUtils();
        // load closest bullet
        try {
            bullet = getIncomingBullet().get(0);
            Direction direction = bullet.getDirection();
        } catch (Exception e) {
            return getMoveCommand(world, botNumber, MoveStop);
        }
        // dodge the bullet
        if(bullet.getX() + bullet.getDx() == botTank.getX() && bullet.getY() + bullet.getDy() == botTank.getY()) {
            Direction dir =  dirUtils.getDirWithout(dirUtils.getOppositeDir(bullet.getDirection()));
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
        if(bullet.getX() + 3*bullet.getDx() == botTank.getX()
                && bullet.getY() + 3*bullet.getDy() == botTank.getY()
                && bullet.getDirection() == dirUtils.getOppositeDir(botTank.getDirection())
        ) {
            return getMoveCommand(world, botNumber, MoveFire);
        }
        return getMoveCommand(world, botNumber, MoveStop);
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
        int nextDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX() + bullet.getDx(), bullet.getY() + bullet.getDy());
        return nextDist < currentDist;
    }

    private Tank getBotTank() {
        return world.getTanks().get(botNumber);
    }
}
