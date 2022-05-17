package wobject.command;

import wobject.Direction;
import wobject.World;

public class MoveEast extends GameCommand {

    public MoveEast(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.moveTank(playerNumber, Direction.East);
    }
}
