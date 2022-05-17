package wobject.command;

import wobject.World;

public class MoveStop extends GameCommand {
    public MoveStop(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.stopTank(playerNumber);
    }
}
