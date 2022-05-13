package gui;

import javax.swing.*;

public class Window extends JFrame {
    /**
     * Main game window contain all game components
     */

    private BattleField battleField;

    Window() {
        super();

        battleField = new BattleField();
        add(battleField);
        pack();

        setTitle("Tank Game");

        setAlwaysOnTop(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        Window window = new Window();
    }
}
