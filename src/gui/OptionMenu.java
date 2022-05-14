package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class OptionMenu extends JPanel{

    private BackgroundProcess backgroundProcess;
    private UneditableTextField oneUp;
    private UneditableTextField oneDown;
    private UneditableTextField oneLeft;
    private UneditableTextField oneRight;
    private UneditableTextField oneFire;
    private UneditableTextField twoUp;
    private UneditableTextField twoDown;
    private UneditableTextField twoLeft;
    private UneditableTextField twoRight;
    private UneditableTextField twoFire;
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
        add(new JLabel("Player 1 FIRE:"));
        add(oneFire);
        add(new JLabel("Player 2 FIRE:"));
        add(twoFire);
        add(saveButton);
        add(backButton);
    }

    private void initMenu() {
        // player 1 setting
        oneUp = new UneditableTextField("W");
        oneDown = new UneditableTextField("S");
        oneLeft = new UneditableTextField("A");
        oneRight = new UneditableTextField("D");
        oneFire = new UneditableTextField("SPACE");
        // player 2 setting
        twoUp = new UneditableTextField("ARROW-UP");
        twoDown = new UneditableTextField("ARROW-DOWN");
        twoLeft = new UneditableTextField("ARROW-LEFT");
        twoRight = new UneditableTextField("ARROW-RIGHT");
        twoFire = new UneditableTextField("ENTER");
        // save button
        saveButton = new JButton("save");
        // TODO: add action listener for save button
        saveButton.setEnabled(false);
        // back button
        backButton = new JButton("return to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
    }

    public class UneditableTextField extends JTextField {
        UneditableTextField(String text) {
            setEditable(false);
            setText(text);
        }
    }
}
