package gui;

import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {

    private JButton onePlayerButton;
    private JButton twoPlayerButton;
    private JButton optionButton;

    GameMenu() {
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
    }
}
