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

        // TODO: config dynamic panel size
        setPreferredSize(new Dimension(690, 690));
    }

    private void initMenu() {
        onePlayerButton = new JButton("1 Player");
        twoPlayerButton = new JButton("2 Players");
        optionButton = new JButton("Options");

        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToOptionMenu();
            }
        });

        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add event listener here
            }
        });

        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add event listen here, now it's a template
                backgroundProcess.startTwoPlayerGame();
            }
        });
    }
}
