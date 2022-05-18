package wobject.command;

import wobject.World;

public class MoveFire extends GameCommand {

    public MoveFire(World world, int playerNumber) {
        super(world, playerNumber);
    }

    @Override
    public void execute() {
        world.fireBullet(playerNumber);
    }
}
