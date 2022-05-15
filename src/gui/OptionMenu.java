package gui;

import config.MovementConfig;

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

        // TODO: config dynamic panel size
        setPreferredSize(new Dimension(690, 690));
    }

    public void updateKeyProp() {
        MovementConfig key = MovementConfig.getInstance();
        // player one
        oneUp.setText(key.getKeyText("ONE_UP"));
        oneDown.setText(key.getKeyText("ONE_DOWN"));
        oneLeft.setText(key.getKeyText("ONE_LEFT"));
        oneRight.setText(key.getKeyText("ONE_RIGHT"));
        oneFire.setText(key.getKeyText("ONE_FIRE"));
        // player two
        twoUp.setText(key.getKeyText("TWO_UP"));
        twoDown.setText(key.getKeyText("TWO_DOWN"));
        twoLeft.setText(key.getKeyText("TWO_LEFT"));
        twoRight.setText(key.getKeyText("TWO_RIGHT"));
        twoFire.setText(key.getKeyText("TWO_FIRE"));
    }

    private void initMenu() {
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
                MovementConfig key = MovementConfig.getInstance();
                // player one
                key.updateProp("ONE_UP", KeyEvent.getExtendedKeyCodeForChar(oneUp.getText().charAt(0)));
                key.updateProp("ONE_DOWN", KeyEvent.getExtendedKeyCodeForChar(oneDown.getText().charAt(0)));
                key.updateProp("ONE_LEFT", KeyEvent.getExtendedKeyCodeForChar(oneLeft.getText().charAt(0)));
                key.updateProp("ONE_RIGHT", KeyEvent.getExtendedKeyCodeForChar(oneRight.getText().charAt(0)));
                key.updateProp("ONE_FIRE", KeyEvent.getExtendedKeyCodeForChar(oneFire.getText().charAt(0)));
                // player two
                key.updateProp("TWO_UP", KeyEvent.getExtendedKeyCodeForChar(twoUp.getText().charAt(0)));
                key.updateProp("TWO_DOWN", KeyEvent.getExtendedKeyCodeForChar(twoDown.getText().charAt(0)));
                key.updateProp("TWO_LEFT", KeyEvent.getExtendedKeyCodeForChar(twoLeft.getText().charAt(0)));
                key.updateProp("TWO_RIGHT", KeyEvent.getExtendedKeyCodeForChar(twoRight.getText().charAt(0)));
                key.updateProp("TWO_FIRE", KeyEvent.getExtendedKeyCodeForChar(twoFire.getText().charAt(0)));
                // save properties
                key.saveProp();
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
