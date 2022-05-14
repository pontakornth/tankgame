package gui;

import wobject.WObject;
import wobject.World;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BattleField extends JPanel {

    // TODO: add world object and update its graphic
    private World world;
    // Map filename to image to reduce redundant load.
    private Map<String, Image> imageMap;

    private int SIZE = 23;
    private int GRID_PIXEL = 30;

    BattleField() {
        world = new World();
        imageMap = new HashMap<>();
        setPreferredSize(new Dimension(SIZE*GRID_PIXEL, SIZE*GRID_PIXEL));
    }

    // paint methods
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
        paintTiles(g);
    }

    public void paintGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i=0; i<=SIZE; i++) {
            g.drawLine(0, i*GRID_PIXEL, SIZE*GRID_PIXEL, i*GRID_PIXEL);
            g.drawLine(i*GRID_PIXEL, 0, i*GRID_PIXEL, SIZE*GRID_PIXEL);
        }
    }

    public void paintTiles(Graphics g) {
        for (WObject wObject: world.getTiles()) {
            String fileName = wObject.getSpriteFileName();
            Image image = imageMap.get(fileName);
            if (image == null) {
                image = new ImageIcon(fileName).getImage();
                imageMap.put(fileName, image);
            }
            g.drawImage(image, wObject.getX()*GRID_PIXEL, wObject.getY()*GRID_PIXEL, GRID_PIXEL, GRID_PIXEL, null, null);
        }
    }
}
