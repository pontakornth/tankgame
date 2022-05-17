package wobject.bot.strategy;

import wobject.World;
import wobject.command.GameCommand;

import static wobject.command.CommandFactory.getRandomMove;

public class IdiotStrategy extends Strategy {

    public IdiotStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        return getRandomMove(world, botNumber);
    }
}
