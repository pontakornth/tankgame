package wobject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.DirUtils;
import static util.DistanceCalculator.manhattanDist;

import wobject.bot.strategy.CowardStrategy;
import wobject.bot.strategy.Strategy;
import wobject.command.GameCommand;
import static wobject.command.CommandFactory.CommandEnum.*;
import static wobject.command.CommandFactory.getMoveCommand;

public class BotPlayer {

    private World world;
    private int botNumber;
    private Strategy strategy;

    BotPlayer(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
        this.strategy = new CowardStrategy(world, botNumber);
    }

    public void execute() {
        strategy.getNextMove().execute();
    }
}