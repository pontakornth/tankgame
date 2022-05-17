package wobject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.DirUtils;
import static util.DistanceCalculator.manhattanDist;

import wobject.command.GameCommand;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class BotPlayer {

    private World world;
    private int botNumber;
    private DirUtils dirUtils = new DirUtils();

    private List<GameCommand> commands;

    BotPlayer(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
        this.commands = new ArrayList<>();
    }

    public void execute() {
        // TODO: implement execute logic here!
        // defense the bullet
        defense();
        GameCommand currentCommand;
        try {
            currentCommand = commands.remove(0);
            currentCommand.execute();
        } catch (Exception e) {
            defense();
        }
    }

    // TODO: implement bot logic here!

    public void randomMove() {
        world.moveTank(botNumber, dirUtils.getDir());
    }

    public void defense() {
        Bullet bullet;
        Tank botTank = getBotTank();
        // load closest bullet
        try {
            bullet = getIncomingBullet().get(0);
            Direction direction = bullet.getDirection();
        } catch (Exception e) {
            commands.add(getMoveCommand(world, botNumber, MoveStop));
            return;
        }
        // dodge the bullet
        if(bullet.getX() + bullet.getDx() == botTank.getX() && bullet.getY() + bullet.getDy() == botTank.getY()) {
            Direction dir =  dirUtils.getDirWithout(dirUtils.getOppositeDir(bullet.getDirection()));
            switch (dir) {
                case North:
                    commands.add(getMoveCommand(world, botNumber, MoveNorth));
                    break;
                case South:
                    commands.add(getMoveCommand(world, botNumber, MoveSouth));
                    break;
                case East:
                    commands.add(getMoveCommand(world, botNumber, MoveEast));
                    break;
                case West:
                    commands.add(getMoveCommand(world, botNumber, MoveWest));
                    break;
            }
            return;
        }
        // destroy the bullet
        if(bullet.getX() + 3*bullet.getDx() == botTank.getX()
                && bullet.getY() + 3*bullet.getDy() == botTank.getY()
                && bullet.getDirection() == dirUtils.getOppositeDir(botTank.getDirection())
        ) {
            commands.add(getMoveCommand(world, botNumber, MoveFire));
            return;
        }
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