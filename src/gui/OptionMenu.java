package gui;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;

public class OptionMenu extends JPanel{

    private BackgroundProcess backgroundProcess;
    private KeyTextField oneUp;
    private KeyTextField oneDown;
    private KeyTextField oneLeft;
    private KeyTextField oneRight;
    private KeyTextField oneFire;
    private KeyTextField twoUp;
    private KeyTextField twoDown;
    private KeyTextField twoLeft;
    private KeyTextField twoRight;
    private KeyTextField twoFire;
    private JButton saveButton;
    private JButton backButton;

    // Player Control Properties
    Properties prop;

    OptionMenu(BackgroundProcess backgroundProcess) {
        this.backgroundProcess = backgroundProcess;

        initMenu();

        setLayout(new GridLayout(0, 4));
        add(new JLabel("Player 1 UP:"));
        add(oneUp);
        add(new JLabel("Player 2 UP:"));
        add(twoUp);
        add(new JLabel("Player 1 DOWN:"));
        add(oneDown);
        add(new JLabel("Player 2 DOWN:"));
        add(twoDown);
        add(new JLabel("Player 1 LEFT:"));
        add(oneLeft);
        add(new JLabel("Player 2 LEFT:"));
        add(twoLeft);
        add(new JLabel("Player 1 RIGHT:"));
        add(oneRight);
        add(new JLabel("Player 2 RIGHT:"));
        add(twoRight);
        add(new JLabel("Player 1 FIRE:"));
        add(oneFire);
        add(new JLabel("Player 2 FIRE:"));
        add(twoFire);
        add(saveButton);
        add(backButton);
    }

    public void readKeyProp() {
        String CONFIG_FILE = "config/game-control.properties";
        prop = new Properties();
        try {
            prop.load(new FileInputStream(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateKeyProp() {
        // player one
        oneUp.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("ONE_UP"))));
        oneDown.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("ONE_DOWN"))));
        oneLeft.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("ONE_LEFT"))));
        oneRight.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("ONE_RIGHT"))));
        oneFire.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("ONE_FIRE"))));
        // player two
        twoUp.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("TWO_UP"))));
        twoDown.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("TWO_DOWN"))));
        twoLeft.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("TWO_LEFT"))));
        twoRight.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("TWO_RIGHT"))));
        twoFire.setText(KeyEvent.getKeyText(Integer.parseInt(prop.getProperty("TWO_FIRE"))));
    }

    private void initMenu() {
        // read properties file
        readKeyProp();
        // player 1 setting
        oneUp = new KeyTextField();
        oneDown = new KeyTextField();
        oneLeft = new KeyTextField();
        oneRight = new KeyTextField();
        oneFire = new KeyTextField();
        // player 2 setting
        twoUp = new KeyTextField();
        twoDown = new KeyTextField();
        twoLeft = new KeyTextField();
        twoRight = new KeyTextField();
        twoFire = new KeyTextField();
        // save button
        saveButton = new JButton("save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: add action listener for save button
                System.out.println(KeyEvent.VK_P);
            }
        });
        // back button
        backButton = new JButton("return to menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
        // update key from properties
        updateKeyProp();
    }

    class UppercaseDocumentFilter extends DocumentFilter {
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                                 String text, AttributeSet attr) throws BadLocationException {

            fb.insertString(offset, text.toUpperCase(), attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                            String text, AttributeSet attrs) throws BadLocationException {

            fb.replace(offset, length, text.toUpperCase(), attrs);
        }
    }

    public class KeyTextField extends JTextField {
        KeyTextField() {
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if(getText().length() >= 1) {
                        e.consume();
                    }
                }
            });
            ((AbstractDocument) getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        }

        KeyTextField(String text) {
            this();
            setText(text);
        }
    }
}
