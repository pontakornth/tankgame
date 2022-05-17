package gui;

import wobject.bot.strategy.StrategyFactory.StrategyEnum;

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

    public void changeToGame(int playerNumber, List<String> map) {
        window.getContentPane().removeAll();
        window.setBattleField(new BattleField(this, playerNumber, map));
        window.getContentPane().add(window.getBattleField());
        window.getBattleField().requestFocus();
        window.revalidate();
        window.repaint();
    }

    public void changeToBotMenu() {
        window.getContentPane().removeAll();
        window.getContentPane().add(window.getBotMenu());
        window.revalidate();
        window.repaint();
    }

    public Window getWindow() {
        return window;
    }
}
