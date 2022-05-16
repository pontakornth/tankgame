package gui;

import config.MapReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MapMenu extends JPanel {
    private BackgroundProcess backgroundProcess;
    private int playerNumber;

    private List<JButton> stageButtons;
    private List<String> stageNames = new ArrayList<>(List.of("plain", "fortress", "maze"));
    private List<String> stageFiles = new ArrayList<>(List.of("maps/plain.txt", "maps/fortress.txt", "maps/maze.txt"));

    private JButton backButton;

    MapMenu(BackgroundProcess backgroundProcess, int playerNumber) {
        this.backgroundProcess = backgroundProcess;
        this.playerNumber = playerNumber;

        initMenu();

        setLayout(null);
        for(JButton button: stageButtons) {
            add(button);
        }
        add(backButton);

        // TODO: config dynamic panel size
        setPreferredSize(new Dimension(690, 690));
    }

    private void initMenu() {
        Dimension buttonSize = new Dimension(201, 51);
        stageButtons = new ArrayList<JButton>();
        for(int i=0; i<stageNames.size(); i++) {
            String stageFile = stageFiles.get(i);
            JButton button = new JButton(stageNames.get(i));
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundProcess.changeToGame(playerNumber, MapReader.read(stageFile));
                }
            });
            button.setBounds(244, 249+i*70, buttonSize.width, buttonSize.height);
            stageButtons.add(button);
        }
        backButton = new JButton("return to menu");
        backButton.setBounds(244, 459, buttonSize.width, buttonSize.height);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("img/bg-map-menu.png").getImage(), 0, 0, null);
    }
}
