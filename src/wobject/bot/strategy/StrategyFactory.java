package wobject.bot.strategy;

import wobject.World;

public class StrategyFactory {
    public static enum StrategyEnum {
        Idiot,
        Coward;
    }

    public static Strategy getStrategy(World world, int botNumber, StrategyEnum strategyEnum) {
        switch (strategyEnum) {
            case Idiot:
                return new IdiotStrategy(world, botNumber);
            case Coward:
                return new CowardStrategy(world, botNumber);
        }
        return null;
    }
}
