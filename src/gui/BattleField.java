package gui;

import util.Observable;
import util.Observer;
import wobject.Direction;
import wobject.WObject;
import wobject.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class BattleField extends JPanel implements Observer<String> {

    // TODO: add world object and update its graphic
    private World world;
    // Map filename to image to reduce redundant load.
    private Map<String, Image> imageMap;

    private int SIZE = 23;
    private int GRID_PIXEL = 30;

    private BackgroundProcess backgroundProcess;

    BattleField(BackgroundProcess backgroundProcess, int playerNumber, List<String> map) {
        this.backgroundProcess = backgroundProcess;
        // TODO: update this to handle player number
        if(map == null) {
            world = new World();
        } else {
            world = new World(map, playerNumber);
        }
        world.addObservers(this);
        imageMap = new HashMap<>();
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE*GRID_PIXEL, SIZE*GRID_PIXEL));
        MovementListener movementListener = new MovementListener(this);
        movementListener.addObservers(this);
        addKeyListener(movementListener);
        world.init();
    }

    @Override
    public void onNotify(String message) {
        // TODO: Handle update from world such as losing.
        repaint();
    }

    private class MovementListener extends KeyAdapter implements Observable<String> {
        private Observer<String> observer;
        private BattleField battleField;

        public MovementListener(BattleField battleField) {
            this.battleField = battleField;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // TODO: Handle second tank and stopping.
            if (keyCode == KeyEvent.VK_UP) {
                battleField.moveTank(0, Direction.North);
            } else if (keyCode == KeyEvent.VK_DOWN) {
                battleField.moveTank(0, Direction.South);
            } else if (keyCode == KeyEvent.VK_LEFT) {
                battleField.moveTank(0, Direction.West);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                battleField.moveTank(0, Direction.East);
            }

            if (keyCode == KeyEvent.VK_W) {
                battleField.moveTank(1, Direction.North);
            } else if (keyCode == KeyEvent.VK_S) {
                battleField.moveTank(1, Direction.South);
            } else if (keyCode == KeyEvent.VK_A) {
                battleField.moveTank(1, Direction.West);
            } else if (keyCode == KeyEvent.VK_D) {
                battleField.moveTank(1, Direction.East);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO: Receive array of keys from config instead
            int[] player1 = new int[]{KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
            int[] player2 = new int[]{KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D};
            for (int key: player1) {
                if (key == e.getKeyCode()) {
                    battleField.stopTank(0);
                }
            }
            for (int key: player2) {
                if (key == e.getKeyCode()) {
                    battleField.stopTank(1);
                }
            }
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
    }

    private void stopTank(int tankIndex) {
        world.stopTank(tankIndex);
    }

    private void moveTank(int tankIndex, Direction direction) {
        world.moveTank(tankIndex, direction);
    }

    // paint methods
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
        paintTanks(g);
        // So tiles can overwrite tanks
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
            paintWObject(g, wObject);
        }
    }

    public void paintTanks(Graphics g) {
        for (WObject wObject: world.getTanks()) {
            paintWObject(g, wObject);
        }
    }

    private void paintWObject(Graphics g, WObject wObject) {
        Image image = getImage(wObject);
        g.drawImage(image, wObject.getX()*GRID_PIXEL, wObject.getY()*GRID_PIXEL, GRID_PIXEL, GRID_PIXEL, null, null);
    }

    private Image getImage(WObject wObject) {
        String fileName = wObject.getSpriteFileName();
        Image image = imageMap.get(fileName);
        if (image == null) {
            image = new ImageIcon(fileName).getImage();
            imageMap.put(fileName, image);
        }
        return image;
    }
}
