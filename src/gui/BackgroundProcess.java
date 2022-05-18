package gui;

import wobject.bot.strategy.StrategyFactory;

import java.util.List;

public class BackgroundProcess {

    private Window window;

    BackgroundProcess(Window window) {
        this.window = window;
    }

    public void changeToOptionMenu() {
        window.getContentPane().removeAll();
        window.getContentPane().add(window.getOptionMenu());
        window.getOptionMenu().requestFocus();
        window.revalidate();
        window.repaint();
    }

    public void changeToGameMenu() {
        window.getContentPane().removeAll();
        window.getOptionMenu().updateKeyProp();
        window.getContentPane().add(window.getGameMenu());
        window.revalidate();
        window.repaint();
    }

    public void changeToMapMenu(int playerNumber) {
        window.getContentPane().removeAll();
        window.setMapMenu(new MapMenu(this, playerNumber));
        window.getContentPane().add(window.getMapMenu());
        window.revalidate();
        window.repaint();
    }

    public void changeToOnePlayerGame(List<String> map, StrategyFactory.StrategyEnum strategyEnum) {
        window.getContentPane().removeAll();
        window.setBattleField(new BattleField(this, 1, map, strategyEnum));
        window.getContentPane().add(window.getBattleField());
        window.getBattleField().requestFocus();
        window.revalidate();
        window.repaint();
    }

    public void changeToTwoPlayerGame(List<String> map) {
        window.getContentPane().removeAll();
        window.setBattleField(new BattleField(this, 2, map, null));
        window.getContentPane().add(window.getBattleField());
        window.getBattleField().requestFocus();
        window.revalidate();
        window.repaint();
    }

    public void changeToBotMenu(List<String> map) {
        window.getContentPane().removeAll();
        window.setBotMenu(new BotMenu(this, map));
        window.getContentPane().add(window.getBotMenu());
        window.revalidate();
        window.repaint();
    }

    public Window getWindow() {
        return window;
    }
}
