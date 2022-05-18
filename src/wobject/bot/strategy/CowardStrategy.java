package wobject.bot.strategy;

import util.DirUtils;
import wobject.*;
import wobject.command.CommandFactory;
import wobject.command.GameCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static util.DistanceCalculator.manhattanDist;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;
import static wobject.bot.strategy.StrategyUtils.*;

public class CowardStrategy extends Strategy {

    private StrategyUtils strategyUtils;

    public CowardStrategy(World world, int botNumber) {
        super(world, botNumber);
        strategyUtils = new StrategyUtils(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        Bullet bullet = null;
        Tank botTank = strategyUtils.getBotTank();
        DirUtils dirUtils = new DirUtils();
        // load closest bullet
        try {
            bullet = strategyUtils.getIncomingBullet().get(0);
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
        WObject trees = strategyUtils.getClosestObject();
        if (trees != null && trees instanceof Trees) {
            if (bullet != null && !strategyUtils.isSameSpot(new Bullet(bullet.getX()+bullet.getDx(), bullet.getY()+bullet.getDy()), trees)) {
                return strategyUtils.moveToObject(trees);
            }
        }

        // base command
        return getMoveCommand(world, botNumber, MoveStop);
    }
}
