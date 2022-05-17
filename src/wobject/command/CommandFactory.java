package wobject.command;

import wobject.Direction;
import wobject.World;

import static wobject.Direction.*;

public class CommandFactory {
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
}
