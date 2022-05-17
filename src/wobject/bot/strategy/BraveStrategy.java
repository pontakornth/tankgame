package wobject.bot.strategy;

import wobject.World;
import wobject.command.GameCommand;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class BraveStrategy extends Strategy {

    BraveStrategy(World world, int botNumber) {
        super(world, botNumber);
    }

    @Override
    public GameCommand getNextMove() {
        return getMoveCommand(world, botNumber, MoveStop);
    }
}
