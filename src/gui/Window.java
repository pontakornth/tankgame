package gui;

import config.MapReader;

import javax.swing.*;

public class Window extends JFrame {
    /**
     * Main game window contain all game components
     */

    // game components
    private BattleField battleField;

    // menu components
    private BackgroundProcess backgroundProcess;
    private GameMenu gameMenu;
    private OptionMenu optionMenu;
    private MapMenu mapMenu;

    Window() {
        super();

        initMenu();

        add(gameMenu);
        pack();

        setTitle("Tank Game");
        setAlwaysOnTop(true);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initMenu() {
        backgroundProcess = new BackgroundProcess(this);
        gameMenu = new GameMenu(backgroundProcess);
        optionMenu = new OptionMenu(backgroundProcess);
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public GameMenu getGameMenu() {
        return gameMenu;
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public MapMenu getMapMenu() {
        return mapMenu;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public void setMapMenu(MapMenu mapMenu) {
        this.mapMenu = mapMenu;
    }

    public static void main(String[] args) {
        Window window = new Window();
    }
}
