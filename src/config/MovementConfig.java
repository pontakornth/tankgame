package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class MovementConfig {
    private static MovementConfig movementConfig;
    private static Properties prop;
    private static String CONFIG_FILE = "config/game-control.properties";

    private MovementConfig() {
        prop = new Properties();
    }

    public static MovementConfig getInstance() {
        if (movementConfig == null) {
            movementConfig = new MovementConfig();
        }
        readProp();
        return movementConfig;
    }

    public int getKeyCode(String propName) {
        return Integer.parseInt(prop.getProperty(propName));
    }

    public static void readProp() {
        try {
            prop.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProp() {

    }
}