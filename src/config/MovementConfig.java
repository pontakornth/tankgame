package config;

public class MovementConfig {
    private static MovementConfig playerOne;
    private static MovementConfig playerTwo;

    private MovementConfig() {

    }

    public static MovementConfig getPlayerOne() {
        return playerOne;
    }

    public static MovementConfig getPlayerTwo() {
        return playerTwo;
    }
}
