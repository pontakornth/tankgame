package wobject;

import java.util.ArrayList;
import java.util.List;

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

    public Bullet requestBullet(int x, int y, int dx, int dy) {
        if (bullets.isEmpty()) {
            bullets.add(new Bullet(0, 0));
            currentSize++;
        }
        Bullet bullet = bullets.remove(0);
        bullet.refreshState(x, y, dx, dy);
        return bullet;
    }

    public void returnBullet(Bullet bullet) {
        bullets.add(bullet);
    }
}
