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

    public static enum KeyProp {
        ONE_UP("ONE_UP"),
        ONE_DOWN("ONE_DOWN"),
        ONE_LEFT("ONE_LEFT"),
        ONE_RIGHT("ONE_RIGHT"),
        ONE_FIRE("ONE_FIRE"),
        TWO_UP("TWO_UP"),
        TWO_DOWN("TWO_DOWN"),
        TWO_LEFT("TWO_LEFT"),
        TWO_RIGHT("TWO_RIGHT"),
        TWO_FIRE("TWO_FIRE");

        public final String propName;

        private KeyProp(String propName) {
            this.propName = propName;
        }
    }

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

    public int getKeyCode(KeyProp keyProp) {
        return Integer.parseInt(prop.getProperty(keyProp.propName));
    }

    public String getKeyText(KeyProp keyProp) {
        return KeyEvent.getKeyText(getKeyCode(keyProp));
    }

    public static void readProp() {
        try {
            prop.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProp(KeyProp keyProp, int keyCode) {
        prop.setProperty(keyProp.propName, String.valueOf(keyCode));
    }

    public void saveProp() {
        try {
            prop.store(new FileOutputStream(CONFIG_FILE), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
