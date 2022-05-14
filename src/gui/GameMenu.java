package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {

    private BackgroundProcess backgroundProcess;
    private JButton onePlayerButton;
    private JButton twoPlayerButton;
    private JButton optionButton;

    GameMenu(BackgroundProcess backgroundProcess) {
        this.backgroundProcess = backgroundProcess;

        initMenu();
        setLayout(new GridLayout(0, 1));
        add(new JLabel("TANK GAME"));
        add(onePlayerButton);
        add(twoPlayerButton);
        add(optionButton);
    }

    private void initMenu() {
        onePlayerButton = new JButton("1 Player");
        twoPlayerButton = new JButton("2 Players");
        optionButton = new JButton("Options");

        // TODO: add event listeners and changing panel methods
        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToOptionMenu();
            }
        });
    }
}
