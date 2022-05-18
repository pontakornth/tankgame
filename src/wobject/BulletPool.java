package wobject;

import java.util.ArrayList;
import java.util.List;

import static wobject.Direction.*;

public class BulletPool {
    private final List<Bullet> bullets;
    private int currentSize;

    /**
     * Initialize bullet pool with specific size.
     * @param poolSize Size of bullet pool. It will create `poolSize` of bullets.
     */
    public BulletPool(int poolSize) {
        bullets = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            bullets.add(new Bullet(0, 0));
            currentSize++;
        }
    }

    public BulletPool() {
        this(10);
    }

    public Bullet requestBullet(int x, int y, Direction direction, Faction faction) {
        if (bullets.isEmpty()) {
            bullets.add(new Bullet(0, 0));
            currentSize++;
        }
        Bullet bullet = bullets.remove(0);
        switch (direction) {
            case North:
                bullet.refreshState(x, y, 0, -1, faction);
                bullet.setDirection(North);
                break;
            case South:
                bullet.refreshState(x, y, 0, 1, faction);
                bullet.setDirection(South);
                break;
            case East:
                bullet.refreshState(x, y, 1, 0, faction);
                bullet.setDirection(East);
                break;
            case West:
                bullet.refreshState(x, y, -1, 0, faction);
                bullet.setDirection(West);
                break;
        }
        return bullet;
    }

    public void returnBullet(Bullet bullet) {
        bullets.add(bullet);
    }
}
