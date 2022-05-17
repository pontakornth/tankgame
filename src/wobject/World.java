package wobject;

import util.Observable;
import util.Observer;
import wobject.bot.strategy.StrategyFactory.StrategyEnum;

import java.util.ArrayList;
import java.util.List;

public class World implements Observable<GameEvent> {

    // TODO: implement game logic here!
    // tiles are for fixed tiles such as bricks, steel, or trees
    private List<WObject> tiles;

    private Tank playerOneTank;
    private Tank playerTwoTank;
    private BotPlayer botPlayer;

    // Only bullets on screen are listed here.
    private List<Bullet> bullets;
    private BulletPool bulletPool;
    private Observer<GameEvent> observer;

    public World(List<String> map, int playerNumber, StrategyEnum strategyEnum) {
        tiles = new ArrayList<>();
        bullets = new ArrayList<>();
        for(int i=0; i<23; i++) {
            for(int j=0; j<23; j++) {
                char c = map.get(i).charAt(j);
                switch (c) {
                    case '1':
                        playerOneTank = new Tank(j, i, 1, Faction.Blue);
                        break;
                    case '2':
                        if (playerNumber == 2) {
                            playerTwoTank = new Tank(j, i, 1, Faction.Red);
                        } else {
                            botPlayer = new BotPlayer(this, 1, strategyEnum);
                            playerTwoTank = new Tank(j, i, 1, Faction.Gray);
                        }
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
            while (true) {
                // TODO: implement bot player here
                if(botPlayer != null) {
                    botPlayer.execute();
                }

                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<WObject> tilesToRemove = new ArrayList<>();

                boolean someoneWon = checkWinCondition();
                if (someoneWon)
                    break;
                updateTankIfNoCollision();
                removeOffScreenBullets(bulletsToRemove);
                checkTilesAndBulletsCollision(bulletsToRemove, tilesToRemove);
                handleBulletsAndBulletsCollision(bulletsToRemove);
                handleBulletsAndTanksCollision(bulletsToRemove);

                for (Bullet bulletToRemove: bulletsToRemove) {
                    bulletPool.returnBullet(bulletToRemove);
                    bullets.remove(bulletToRemove);
                }
                for (WObject tileToRemove: tilesToRemove) {
                    tiles.remove(tileToRemove);
                }
                notifyObservers(GameEvent.Update); // BattleField will repaint this
                try {
                    Thread.sleep(100 * 3);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private boolean checkWinCondition() {
        boolean playerOneWon = playerTwoTank.getLifePoint() == 0;
        boolean playerTwoWon = playerOneTank.getLifePoint() == 0;
        if (playerOneWon) {
            notifyObservers(GameEvent.PlayerOneWon);
        } else if (playerTwoWon) {
            notifyObservers(GameEvent.PlayerTwoWon);

        }
        return playerOneWon || playerTwoWon;
    }

    private void handleBulletsAndTanksCollision(List<Bullet> bulletsToRemove) {
        for (Bullet bullet: bullets) {
            // TODO: Remove duplicate(?)
            if (bullet.hit(playerOneTank) && !bullet.sameFaction(playerOneTank)) {
                // Bullet hit different
                playerOneTank.damage();
                bulletsToRemove.add(bullet);
            }
            if (bullet.hit(playerTwoTank) && !bullet.sameFaction(playerTwoTank)) {
                // Bullet hit different
                playerTwoTank.damage();
                bulletsToRemove.add(bullet);
            }
        }
    }

    private void handleBulletsAndBulletsCollision(List<Bullet> bulletsToRemove) {
        // Bullet-bullet collision.
        for (int i = 0; i < bullets.size(); i++) {
            Bullet firstBullet = bullets.get(i);
            for (int j = i + 1; j < bullets.size(); j++) {
                Bullet secondBullet = bullets.get(j);
                if (firstBullet.hit(secondBullet) && !firstBullet.sameFaction(secondBullet)) {
                    bulletsToRemove.add(firstBullet);
                    bulletsToRemove.add(secondBullet);
                }
            }
        }
    }

    private void checkTilesAndBulletsCollision(List<Bullet> bulletsToRemove, List<WObject> tilesToRemove) {
        for (WObject tile : tiles) {
            // Only check for solid tile.
            if (tile.isSolid()) {
                for (Bullet bullet : bullets) {
                    if (tile.hit(bullet)) {
                        // Collide!
                        boolean hit = tile.damage();
                        if (hit) {
                            bulletsToRemove.add(bullet);
                            if (tile.getLifePoint() == 0) {
                                tilesToRemove.add(tile);
                            }
                        }
                    }
                }
            }
        }
    }

    private void removeOffScreenBullets(List<Bullet> bulletsToRemove) {
        for (Bullet bullet : bullets) {
            // TODO: Check collision against brick and tanks
            if (bullet.isOutsideBorder(23, 23)) {
                bulletsToRemove.add(bullet);
            }
            bullet.update();
        }
    }

    private void updateTankIfNoCollision() {
        if (checkNoCollision(playerOneTank, playerTwoTank)) {
                playerOneTank.update();
        }
        if (checkNoCollision(playerTwoTank, playerOneTank)) {
                playerTwoTank.update();
        }
    }

    private boolean checkNoCollision(Tank tank, Tank otherTank) {
        int newX = tank.getX() + tank.getDx();
        int newY = tank.getY() + tank.getDy();
        if(newX < 0 || newX >= 23 || newY < 0 || newY >= 23) {
            return false;
        }
        if (newX == otherTank.getX() && newY == otherTank.getY())
            return false;
        for(WObject t: tiles) {
            if(t.getX() == newX && t.getY() == newY) {
                return !t.isSolid();
            }
        }
        return true;
    }

    public List<WObject> getTiles() {
        return tiles;
    }

    public List<Tank> getTanks() {
        return new ArrayList<>(){{ add(playerOneTank); add(playerTwoTank); }};
    }

    @Override
    public void addObservers(Observer<GameEvent> observer) {
        if (this.observer == null)
            this.observer = observer;
    }

    @Override
    public void notifyObservers(GameEvent message) {
        observer.onNotify(message);
    }

    public void moveTank(int playerNumber, Direction direction) {
        Tank tank = null;
        if (playerNumber == 0) {
            tank = playerOneTank;
        } else if (playerNumber == 1) {
            tank = playerTwoTank;
        }

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

    public void stopTank(int playerNumber) {
        Tank tank = null;
        if (playerNumber == 0)  {
            tank = playerOneTank;
        } else if (playerNumber == 1) {
            tank = playerTwoTank;
        }
        if (tank != null)
            tank.setStop();
    }

    public void fireBullet(int playerNumber) {
        Tank tank = null;
        if (playerNumber == 0) {
            tank = playerOneTank;
        } else if (playerNumber == 1) {
            tank = playerTwoTank;
        }
        // unable to move when firing a bullet
        stopTank(playerNumber);
        if (tank != null) {
            int tankX = tank.getX();
            int tankY = tank.getY();
            Bullet bullet;
            Direction direction = tank.getDirection();
            Faction faction = tank.getFaction();
            if (direction == Direction.North) {
                bullet = bulletPool.requestBullet(tankX, tankY, Direction.North, faction);
            } else if (direction == Direction.South) {
                bullet = bulletPool.requestBullet(tankX, tankY, Direction.South, faction);
            } else if (direction == Direction.East) {
                bullet = bulletPool.requestBullet(tankX, tankY, Direction.East, faction);
            } else {
                // West is the only direction left.
                bullet = bulletPool.requestBullet(tankX, tankY, Direction.West, faction);
            }
            bullets.add(bullet);
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}
