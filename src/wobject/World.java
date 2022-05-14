package wobject;

import java.util.ArrayList;
import java.util.List;

public class World {

    // TODO: implement game logic here!
    // tiles are for fixed tiles such as bricks, steel, or trees
    private List<WObject> tiles;

    private List<Tank> tanks;

    public World() {
        // TODO: Load map from files instead.
        tiles = new ArrayList<>() {{
            add(new Brick(0, 0));
            add(new Steel(1, 1));
            add(new Trees(2, 2));
        }};
        // TODO: Add tanks
        tanks = new ArrayList<>();

    }

    public List<WObject> getTiles() {
        return tiles;
    }
}
