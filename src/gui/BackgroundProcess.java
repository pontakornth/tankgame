package gui;

public class BackgroundProcess {

    private Window window;

    BackgroundProcess(Window window) {
        this.window = window;
    }

    public void changeToOptionMenu() {
        window.getContentPane().removeAll();
        window.getContentPane().add(window.getOptionMenu());
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

    public void startOnePlayerGame() {
        // TODO: add background process for start one player game
    }

    public void startTwoPlayerGame() {
        // TODO: add background process for start two players game
    }
}
