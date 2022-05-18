package wobject;

public class Steel extends WObject {
    Steel(int x, int y) {
        super(x, y, 1);
    }

    @Override
    public void update() {

    }

    @Override
    public String getSpriteFileName() {
        return "./img/metal.png";
    }

    @Override
    public boolean damage() {
        return true;
    }
}
