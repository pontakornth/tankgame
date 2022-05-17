package wobject.command;

import wobject.Direction;
import wobject.World;

public class MoveSouth extends GameCommand{
    public MoveSouth(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.moveTank(playerNumber, Direction.South);
    }
}
