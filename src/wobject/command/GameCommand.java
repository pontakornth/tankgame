package wobject.command;

import wobject.World;

public abstract class GameCommand {

    protected World world;
    protected int playerNumber;

    public GameCommand(World world, int playerNumber) {
        this.world = world;
        this.playerNumber = playerNumber;
    }

    public abstract void execute();
}
