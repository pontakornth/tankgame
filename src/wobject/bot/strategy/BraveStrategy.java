package wobject.bot.strategy;

import wobject.World;
import wobject.command.GameCommand;

import java.util.Random;

import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class BraveStrategy extends Strategy {

    BraveStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        if(new Random().nextInt() % 2 == 0) {
            return new StrategyUtils(world, botNumber).moveToObject(world.getTanks().get(0));
        } else {
            return getMoveCommand(world, botNumber, MoveFire);
        }
    }
}
