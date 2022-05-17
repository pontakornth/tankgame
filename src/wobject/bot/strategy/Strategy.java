package wobject.bot.strategy;

import wobject.World;
import wobject.command.GameCommand;

public abstract class Strategy {

    protected World world;
    protected int botNumber;

    public Strategy(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
    }

    public abstract GameCommand getNextMove();
}
