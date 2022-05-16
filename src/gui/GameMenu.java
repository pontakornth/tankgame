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

        setLayout(null);
        add(onePlayerButton);
        add(twoPlayerButton);
        add(optionButton);

        // TODO: config dynamic panel size
        setPreferredSize(new Dimension(690, 690));
    }

    private void initMenu() {
        Dimension buttonSize = new Dimension(201, 51);
        onePlayerButton = new JButton("1 Player");
        onePlayerButton.setBounds(244, 249, buttonSize.width, buttonSize.height);
        twoPlayerButton = new JButton("2 Players");
        twoPlayerButton.setBounds(244, 319, buttonSize.width, buttonSize.height);
        optionButton = new JButton("Options");
        optionButton.setBounds(244, 389, buttonSize.width, buttonSize.height);

        optionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToOptionMenu();
            }
        });

        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToMapMenu(1);
            }
        });
        // TODO: after implement tank bot, remove line below
        onePlayerButton.setEnabled(false);


        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToMapMenu(2);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("img/bg-game-menu.png").getImage(), 0, 0, null);
    }
}
