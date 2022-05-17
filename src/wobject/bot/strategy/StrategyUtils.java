package wobject.bot.strategy;

import wobject.Bullet;
import wobject.Tank;
import wobject.WObject;
import wobject.World;
import wobject.command.CommandFactory;
import wobject.command.GameCommand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static util.DistanceCalculator.manhattanDist;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class StrategyUtils {

    private World world;
    private int botNumber;

    public StrategyUtils(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
    }
    public GameCommand moveToObject(WObject object) {
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


    public boolean isSameSpot(WObject o1, WObject o2) {
        return o1.getX() == o2.getX() && o1.getY() == o2.getY();
    }

    public boolean isBulletCloser(Tank botTank, Bullet bullet) {
        int currentDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX(), bullet.getY());
        int nextDist = manhattanDist(botTank.getX(), botTank.getY(), bullet.getX() + bullet.getDx(), bullet.getY() + bullet.getDy());
        return nextDist <= currentDist;
    }

    public Tank getBotTank() {
        return world.getTanks().get(botNumber);
    }

    public WObject getClosestObject() {
        List<WObject> objects = world.getTiles();
        if (objects.size() == 0) {
            return null;
        }
        objects.sort(new Comparator<WObject>() {
            @Override
            public int compare(WObject o1, WObject o2) {
                return manhattanDist(getBotTank(), o1) - manhattanDist(getBotTank(), o2);
            }
        });
        return objects.get(0);
    }

    public List<Bullet> getIncomingBullet() {
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
}
