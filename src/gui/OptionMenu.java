package gui;

import javax.swing.*;
import java.awt.*;

public class OptionMenu extends JPanel{

    private JTextField oneUp;
    private JTextField oneDown;
    private JTextField oneLeft;
    private JTextField oneRight;
    private JTextField twoUp;
    private JTextField twoDown;
    private JTextField twoLeft;
    private JTextField twoRight;
    private JButton saveButton;

    OptionMenu() {
        initMenu();

        setLayout(new GridLayout(0, 4));
        add(new JLabel("Player 1 UP:"));
        add(oneUp);
        add(new JLabel("Player 2 UP:"));
        add(twoUp);
        add(new JLabel("Player 1 DOWN:"));
        add(oneDown);
        add(new JLabel("Player 2 DOWN:"));
        add(twoDown);
        add(new JLabel("Player 1 LEFT:"));
        add(oneLeft);
        add(new JLabel("Player 2 LEFT:"));
        add(twoLeft);
        add(new JLabel("Player 1 RIGHT:"));
        add(oneRight);
        add(new JLabel("Player 2 RIGHT:"));
        add(twoRight);
        add(saveButton);
    }

    private void initMenu() {
        // player 1 setting
        oneUp = new JTextField();
        oneDown = new JTextField();
        oneLeft = new JTextField();
        oneRight = new JTextField();
        // player 2 setting
        twoUp = new JTextField();
        twoDown = new JTextField();
        twoLeft = new JTextField();
        twoRight = new JTextField();
        // save button
        saveButton = new JButton("save");
    }
}
