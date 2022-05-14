package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionMenu extends JPanel{

    private BackgroundProcess backgroundProcess;
    private JTextField oneUp;
    private JTextField oneDown;
    private JTextField oneLeft;
    private JTextField oneRight;
    private JTextField twoUp;
    private JTextField twoDown;
    private JTextField twoLeft;
    private JTextField twoRight;
    private JButton saveButton;
    private JButton backButton;

    OptionMenu(BackgroundProcess backgroundProcess) {
        this.backgroundProcess = backgroundProcess;

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
        add(backButton);
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
        // TODO: add action listener for save button
        backButton = new JButton("return to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
    }
}
