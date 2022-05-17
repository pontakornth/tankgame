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

        setLayout(null);
        for(JButton button: botButtons) {
            add(button);
        }
        add(backButton);

        setPreferredSize(new Dimension(690, 690));
    }

    private void initMenu() {
        Dimension buttonSize = new Dimension(201, 51);
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
        for(int i=0; i<botButtons.size(); i++) {
            botButtons.get(i).setBounds(244, 249+i*70, buttonSize.width, buttonSize.height);
        }
        backButton = new JButton("Return to Menu");
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
        g.drawImage(new ImageIcon("img/bg-bot-menu.png").getImage(), 0, 0, null);
    }
}
