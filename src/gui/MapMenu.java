package gui;

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
    private List<String> stageNames = new ArrayList<>(List.of("plain"));
    private List<String> stageFiles = new ArrayList<>(List.of("maps/plain.txt"));

    private JButton backButton;

    MapMenu(BackgroundProcess backgroundProcess, int playerNumber) {
        this.backgroundProcess = backgroundProcess;
        this.playerNumber = playerNumber;

        initMenu();

        setLayout(new GridLayout(0, 1));
        add(new JLabel("Map Selection"));
        for(JButton button: stageButtons) {
            add(button);
        }
        add(backButton);

        // TODO: config dynamic panel size
        setPreferredSize(new Dimension(690, 690));
    }

    private void initMenu() {
        stageButtons = new ArrayList<JButton>();
        for(int i=0; i<stageNames.size(); i++) {
            stageButtons.add(new JButton(stageNames.get(i)));
        }
        backButton = new JButton("return to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
    }
}
