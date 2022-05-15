package config;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public String getKeyText(String propName) {
        return KeyEvent.getKeyText(getKeyCode(propName));
    }

    public static void readProp() {
        try {
            prop.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProp(String propName, int keyCode) {
        prop.setProperty(propName, String.valueOf(keyCode));
    }

    public void saveProp() {
        // TODO: change to real one
        try {
            prop.store(new FileOutputStream(CONFIG_FILE), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
