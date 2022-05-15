package wobject;

import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

public class World implements Observable<String> {

    // TODO: implement game logic here!
    // tiles are for fixed tiles such as bricks, steel, or trees
    private List<WObject> tiles;

    private List<Tank> tanks;
    private Observer<String> observer;

    public World() {
        // TODO: Load map from files instead.
        // TODO: Tiles must be located based on index.
        tiles = new ArrayList<>() {{
            add(new Brick(0, 0));
            add(new Steel(1, 0));
            add(new Trees(2, 0));
        }};
        tanks = new ArrayList<>(){{
            add(new Tank(5, 20, 1));
            add(new Tank(5, 5, 2));
        }};
    }

    public World(List<String> map, int playerNumber) {
        // TODO: handle one/two players game
        tanks = new ArrayList<>();
        tiles = new ArrayList<>();
        for(int i=0; i<23; i++) {
            for(int j=0; j<23; j++) {
                char c = map.get(i).charAt(j);
                switch (c) {
                    case '1':
                        tanks.add(new Tank(j, i, 1));
                        break;
                    case '2':
                        tanks.add(new Tank(j, i, 2));
                        break;
                    case 'B':
                        tiles.add(new Brick(j, i));
                        break;
                    case 'T':
                        tiles.add(new Trees(j, i));
                        break;
                    case 'S':
                        tiles.add(new Steel(j, i));
                        break;
                }
            }
        }
    }

    public void init() {
        Thread thread = new Thread(() -> {
            while (true) {
                for (Tank tank: tanks) {
                    if (!willCollide(tank)) {
                            tank.update();
                    }
                }
                notifyObservers("UPDATE");
                try {
                    Thread.sleep(100 * 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private boolean willCollide(Tank tank) {
        int newX = tank.getX() + tank.getDx();
        int newY = tank.getY() + tank.getDy();
        // TODO: Handle tank collision
        // TODO: Replace 23 with world size
        int index = newY*23 + newX;
        if (index < 0 || index >= tiles.size())
            return false;
        WObject tile = tiles.get(index);
        return tile.isSolid();
    }

    public List<WObject> getTiles() {
        return tiles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    @Override
    public void addObservers(Observer<String> observer) {
        if (this.observer == null)
            this.observer = observer;
    }

    @Override
    public void notifyObservers(String message) {
        observer.onNotify(message);
    }

    public void moveTank(int tankIndex, Direction direction) {
        Tank tank = tanks.get(tankIndex);
        if (tank != null) {
            if (direction == Direction.North) {
                tank.turnNorth();
            }
            if (direction == Direction.South) {
                tank.turnSouth();
            }
            if (direction == Direction.East) {
                tank.turnEast();
            }
            if (direction == Direction.West) {
                tank.turnWest();
            }
        }
    }

    public void stopTank(int tankIndex) {
        Tank tank = tanks.get(tankIndex);
        if (tank != null)
            tank.setStop();
    }
}
