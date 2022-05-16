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

    // Only bullets on screen are listed here.
    private List<Bullet> bullets;
    private BulletPool bulletPool;
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
        bullets = new ArrayList<>();
        bulletPool = new BulletPool();
    }

    public World(List<String> map, int playerNumber) {
        // TODO: handle one/two players game
        tanks = new ArrayList<>();
        tiles = new ArrayList<>();
        bullets = new ArrayList<>();
        for(int i=0; i<23; i++) {
            for(int j=0; j<23; j++) {
                char c = map.get(i).charAt(j);
                switch (c) {
                    case '1':
                        tanks.add(new Tank(j, i, 1, Faction.Blue));
                        break;
                    case '2':
                        // TODO: Handle case for singleplayer.
                        tanks.add(new Tank(j, i, 2, Faction.Red));
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
        bulletPool = new BulletPool();
    }

    public void init() {
        Thread thread = new Thread(() -> {
            // TODO: Add win condition.
            while (true) {
                for (Tank tank: tanks) {
                    if (!willCollide(tank)) {
                            tank.update();
                    }
                }
                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<WObject> tilesToRemove = new ArrayList<>();
                for (Bullet bullet : bullets) {
                    // TODO: Check collision against brick and tanks
                    if (bullet.isOutsideBorder(23, 23)) {
                        bulletPool.returnBullet(bullet);
                        bulletsToRemove.add(bullet);
                    }
                    ;
                    bullet.update();
                }
                for (WObject tile : tiles) {
                    // Only check for solid tile.
                    if (tile.isSolid()) {
                        for (Bullet bullet : bullets) {
                            if (tile.getX() == bullet.getX() && tile.getY() == bullet.getY()) {
                                // Collide!
                                boolean hit = tile.damage();
                                if (hit) {
                                    bulletsToRemove.add(bullet);
                                    bulletPool.returnBullet(bullet);
                                    if (tile.getLifePoint() == 0) {
                                        tilesToRemove.add(tile);
                                    }
                                }
                            }
                        }
                    }
                }
                for (Bullet indexBulletToRemove: bulletsToRemove) {
                    bullets.remove(indexBulletToRemove);
                }
                for (WObject tileToRemove: tilesToRemove) {
                    tiles.remove(tileToRemove);
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
//        int index = newY*23 + newX;
//        if (index < 0 || index >= tiles.size())
//            return false;
//        WObject tile = tiles.get(index);
//        return tile.isSolid();
        if(newX < 0 || newX >= 23 || newY < 0 || newY >= 23) {
            return true;
        }
        for(WObject t: tiles) {
            if(t.getX() == newX && t.getY() == newY) {
                return t.isSolid();
            }
        }
        return false;
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

    public void fireBullet(int tankIndex) {
        Tank tank = tanks.get(tankIndex);
        if (tank != null) {
            int tankX = tank.getX();
            int tankY = tank.getY();
            Bullet bullet;
            Direction direction = tank.getDirection();
            Faction faction = tank.getFaction();
            if (direction == Direction.North) {
                bullet = bulletPool.requestBullet(tankX, tankY, 0, -1, faction);
            } else if (direction == Direction.South) {
                bullet = bulletPool.requestBullet(tankX, tankY, 0, 1, faction);
            } else if (direction == Direction.East) {
                bullet = bulletPool.requestBullet(tankX, tankY, 1, 0, faction);
            } else {
                // West is the only direction left.
                bullet = bulletPool.requestBullet(tankX, tankY, -1, 0, faction);
            }
            bullets.add(bullet);
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
