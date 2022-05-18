package gui;

import config.MovementConfig;
import util.Observer;
import wobject.Direction;
import wobject.GameEvent;
import wobject.WObject;
import wobject.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import config.MovementConfig.KeyProp;
import wobject.bot.strategy.StrategyFactory.StrategyEnum;

public class BattleField extends JPanel implements Observer<GameEvent> {

    private World world;
    // Map filename to image to reduce redundant load.
    private Map<String, Image> imageMap;

    private int SIZE = 23;
    private int GRID_PIXEL = 30;
    private int playerNumber;

    private BackgroundProcess backgroundProcess;

    BattleField(BackgroundProcess backgroundProcess, int playerNumber, List<String> map, StrategyEnum strategyEnum) {
        this.backgroundProcess = backgroundProcess;
        this.playerNumber = playerNumber;
        world = new World(map, playerNumber, strategyEnum);
        world.addObservers(this);
        imageMap = new HashMap<>();
        setFocusable(true);
        setPreferredSize(new Dimension(SIZE*GRID_PIXEL, SIZE*GRID_PIXEL));
        MovementListener movementListener = new MovementListener(this);
        addKeyListener(movementListener);
        world.init();
    }

    @Override
    public void onNotify(GameEvent message) {
        if (message == GameEvent.Update) {
            repaint();
        } else if (message == GameEvent.PlayerOneWon) {
            JOptionPane.showMessageDialog(backgroundProcess.getWindow(), "Player 1 Wins");
            backgroundProcess.changeToGameMenu();
        } else if (message == GameEvent.PlayerTwoWon) {
            JOptionPane.showMessageDialog(backgroundProcess.getWindow(), "Player 2 Wins");
            backgroundProcess.changeToGameMenu();
        }
    }

    private class MovementListener extends KeyAdapter {
        private Observer<String> observer;
        private final BattleField battleField;

        public MovementListener(BattleField battleField) {
            this.battleField = battleField;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            MovementConfig movementConfig = MovementConfig.getInstance();
            int keyCode = e.getKeyCode();

            // Player One
            if (keyCode == movementConfig.getKeyCode(KeyProp.ONE_UP)) {
                battleField.moveTank(0, Direction.North);
            } else if (keyCode == movementConfig.getKeyCode(KeyProp.ONE_DOWN)) {
                battleField.moveTank(0, Direction.South);
            } else if (keyCode == movementConfig.getKeyCode(KeyProp.ONE_LEFT)) {
                battleField.moveTank(0, Direction.West);
            } else if (keyCode == movementConfig.getKeyCode(KeyProp.ONE_RIGHT)) {
                battleField.moveTank(0, Direction.East);
            } else if (keyCode == movementConfig.getKeyCode(KeyProp.ONE_FIRE)) {
                battleField.fireBullet(0);
            }

            // Player Two or disable for bot
            if(playerNumber > 1) {
                if (keyCode == movementConfig.getKeyCode(KeyProp.TWO_UP)) {
                    battleField.moveTank(1, Direction.North);
                } else if (keyCode == movementConfig.getKeyCode(KeyProp.TWO_DOWN)) {
                    battleField.moveTank(1, Direction.South);
                } else if (keyCode == movementConfig.getKeyCode(KeyProp.TWO_LEFT)) {
                    battleField.moveTank(1, Direction.West);
                } else if (keyCode == movementConfig.getKeyCode(KeyProp.TWO_RIGHT)) {
                    battleField.moveTank(1, Direction.East);
                } else if (keyCode == movementConfig.getKeyCode(KeyProp.TWO_FIRE)) {
                    battleField.fireBullet(1);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            MovementConfig movementConfig = MovementConfig.getInstance();
            int[] player1 = new int[]{
                    movementConfig.getKeyCode(KeyProp.ONE_UP),
                    movementConfig.getKeyCode(KeyProp.ONE_DOWN),
                    movementConfig.getKeyCode(KeyProp.ONE_LEFT),
                    movementConfig.getKeyCode(KeyProp.ONE_RIGHT)
            };
            int[] player2 = new int[]{
                    movementConfig.getKeyCode(KeyProp.TWO_UP),
                    movementConfig.getKeyCode(KeyProp.TWO_DOWN),
                    movementConfig.getKeyCode(KeyProp.TWO_LEFT),
                    movementConfig.getKeyCode(KeyProp.TWO_RIGHT),

            };
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
    }

    private void fireBullet(int playerNumber) {
        world.fireBullet(playerNumber);
    }

    private void stopTank(int playerNumber) {
        world.stopTank(playerNumber);
    }

    private void moveTank(int playerNumber, Direction direction) {
        world.moveTank(playerNumber, direction);
    }

    // paint methods
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintGrid(g);
        paintTanks(g);
        paintBullets(g);
        // So tiles can overwrite tanks
        paintTiles(g);
    }

    private void paintBullets(Graphics g) {
        for (WObject bullet: world.getBullets()) {
            g.setColor(Color.red);

            int x = bullet.getX() * GRID_PIXEL + (int) (GRID_PIXEL / 3);
            int y = bullet.getY() * GRID_PIXEL + (int) (GRID_PIXEL / 3);

            g.fillRect(x, y, (int) (GRID_PIXEL /3) ,(int) (GRID_PIXEL/3));
        }
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
