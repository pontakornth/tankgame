package wobject;

public class Trees extends WObject {
    Trees(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public String getSpriteFileName() {
        return "./img/tree.png";
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
