package wobject.command;

import wobject.Direction;
import wobject.World;

public class MoveNorth extends GameCommand {

    public MoveNorth(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.moveTank(playerNumber, Direction.North);
    }
}
