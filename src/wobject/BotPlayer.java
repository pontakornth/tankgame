package wobject;

import wobject.bot.strategy.Strategy;
import wobject.bot.strategy.StrategyFactory;

import static wobject.bot.strategy.StrategyFactory.StrategyEnum.*;


public class BotPlayer {

    private World world;
    private int botNumber;
    private Strategy strategy;

    BotPlayer(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
        this.strategy = StrategyFactory.getStrategy(world, botNumber, Coward);
    }

    public void execute() {
        strategy.getNextMove().execute();
    }
}