package wobject;

public class Brick extends WObject {

    Brick(int x, int y) {
        super(x, y, 4);
    }

    @Override
    public void update() {

    }

    @Override
    public String getSpriteFileName() {
        if (lifePoint == 4) {
            return "./img/wall-1.png";
        } else if (lifePoint == 3) {
            return "./img/wall-2.png";
        } else if (lifePoint == 2) {
            return "./img/wall-3.png";
        }

        return "./img/wall-4.png";
    }

    @Override
    public boolean damage() {
        lifePoint--;
        return true;
    }
}
