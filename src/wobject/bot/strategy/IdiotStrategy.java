package wobject.bot.strategy;

import wobject.World;
import wobject.command.GameCommand;

import java.util.Random;

import static wobject.command.CommandFactory.getRandomMove;

public class IdiotStrategy extends Strategy {

    public IdiotStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        if(new Random().nextInt() % 3 == 0) {
            return getRandomMove(world, botNumber);
        } else {
            return new StrategyUtils(world, botNumber).moveToObject(world.getTanks().get(0));
        }
    }
}
