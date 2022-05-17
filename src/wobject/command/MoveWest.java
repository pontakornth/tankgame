package wobject.command;

import wobject.Direction;
import wobject.World;

public class MoveWest extends GameCommand {

    public MoveWest(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.moveTank(playerNumber, Direction.West);
    }
}
