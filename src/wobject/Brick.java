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
        return "./img/wall-1.png";
    }
}
