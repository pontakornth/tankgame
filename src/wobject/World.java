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
        tiles = new ArrayList<>() {{
            add(new Brick(0, 0));
            add(new Steel(1, 1));
            add(new Trees(2, 2));
        }};
        tanks = new ArrayList<>(){{
            add(new Tank(5, 20, 1));
            add(new Tank(5, 5, 2));
        }};

    }

    public void init() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (Tank tank: tanks) {
                        tank.update();
                    }
                    notifyObservers("UPDATE");
                    try {
                        Thread.sleep(100 * 3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        };
        thread.start();
    }

    public List<WObject> getTiles() {
        return tiles;
    }

    public List<Tank> getTanks() {
        return tanks;
    }

    public void moveTankNorth() {
        // TODO: Handle multiple tanks
        Tank tank = tanks.get(0);
        if (tank != null) {
            tank.turnNorth();
        }
    }

    public void moveTankWest() {
        Tank tank = tanks.get(0);
        if (tank != null) {
            tank.turnWest();
        }
    }

    public void moveTankEast() {
        Tank tank = tanks.get(0);
        if (tank != null) {
            tank.turnEast();
        }
    }

    public void moveTankSouth() {
        Tank tank = tanks.get(0);
        if (tank != null) {
            tank.turnSouth();
        }
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

    public void stopTank() {
        Tank tank = tanks.get(0);
        if (tank != null)
            tank.setStop();
    }
}
