package wobject.command;

import wobject.Direction;
import wobject.World;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static wobject.Direction.*;

public class CommandFactory {

    public static enum CommandEnum {
        MoveNorth,
        MoveSouth,
        MoveEast,
        MoveWest,
        MoveStop,
        MoveFire;

        public static CommandEnum randomCommand() {
            return Arrays.asList(values()).get(new Random().nextInt(values().length));
        }
    }

    public static GameCommand getMoveCommand(World world, int playerNumber, CommandEnum commandEnum) {
        switch (commandEnum) {
            case MoveNorth:
                return new MoveNorth(world, playerNumber);
            case MoveSouth:
                return new MoveSouth(world, playerNumber);
            case MoveEast:
                return new MoveEast(world, playerNumber);
            case MoveWest:
                return new MoveWest(world, playerNumber);
            case MoveFire:
                return new MoveFire(world, playerNumber);
            case MoveStop:
                return new MoveStop(world, playerNumber);
        }
        return null;
    }

    public static GameCommand getRandomMove(World world, int playerNumber) {
        return getMoveCommand(world, playerNumber, CommandEnum.randomCommand());
    }
}
