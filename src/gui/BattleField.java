package gui;

import javax.swing.*;
import java.awt.*;

public class BattleField extends JPanel {

    // TODO: add world object and update its graphic

    private int SIZE = 23;
    private int GRID_PIXEL = 30;

    BattleField() {
        setPreferredSize(new Dimension(SIZE*GRID_PIXEL, SIZE*GRID_PIXEL));
    }

    // paint methods
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
    }

    public void paintGrid(Graphics g) {
        g.setColor(Color.black);
        for(int i=0; i<=SIZE; i++) {
            g.drawLine(0, i*GRID_PIXEL, SIZE*GRID_PIXEL, i*GRID_PIXEL);
            g.drawLine(i*GRID_PIXEL, 0, i*GRID_PIXEL, SIZE*GRID_PIXEL);
        }
    }
}
