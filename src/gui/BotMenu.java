package gui;

import wobject.bot.strategy.StrategyFactory.StrategyEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotMenu extends JPanel {
    private BackgroundProcess backgroundProcess;
    private List<String> map;

    private List<StrategyEnum> strategyList = Arrays.asList(StrategyEnum.values());
    private List<JButton> botButtons;

    private JButton backButton;

    public BotMenu(BackgroundProcess backgroundProcess, List<String> map) {
        this.backgroundProcess = backgroundProcess;
        this.map = map;

        initMenu();

        System.out.println(strategyList.get(0).name());

        add(new JLabel("Select Your Enemy"));
        for(JButton button: botButtons) {
            add(button);
        }
        add(backButton);
    }

    private void initMenu() {
        botButtons = new ArrayList<JButton>();
        for(StrategyEnum strategy: strategyList) {
            JButton button = new JButton(strategy.name() + " Tank");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundProcess.changeToOnePlayerGame(map, strategy);
                }
            });
            botButtons.add(button);
        }
        backButton = new JButton("Return to Menu");
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
        // TODO: add background here
    }
}
