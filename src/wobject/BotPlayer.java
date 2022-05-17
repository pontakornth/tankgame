package wobject;

public class BotPlayer {

    private World world;
    private int botNumber;

    BotPlayer(World world, int botNumber) {
        this.world = world;
        this.botNumber = botNumber;
    }

    public void execute() {
        // TODO: implement execute logic here!
        System.out.println(world.getTanks().get(0).getX() + " " + world.getTanks().get(0).getY());
        world.moveTank(botNumber, Direction.South);
    }

    // TODO: implement bot logic here!
}
