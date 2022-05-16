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

        setLayout(null);
        add(oneUp);
        add(twoUp);
        add(oneDown);
        add(twoDown);
        add(oneLeft);
        add(twoLeft);
        add(oneRight);
        add(twoRight);
        add(oneFire);
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
        Dimension buttonSize = new Dimension(201, 51);
        Dimension textFieldSize = new Dimension(100, 30);

        // player 1 setting
        oneUp = new KeyTextField();
        oneUp.setBounds(214, 188, textFieldSize.width, textFieldSize.height);
        oneDown = new KeyTextField();
        oneDown.setBounds(214, 260, textFieldSize.width, textFieldSize.height);
        oneLeft = new KeyTextField();
        oneLeft.setBounds(214, 332, textFieldSize.width, textFieldSize.height);
        oneRight = new KeyTextField();
        oneRight.setBounds(214, 404, textFieldSize.width, textFieldSize.height);
        oneFire = new KeyTextField();
        oneFire.setBounds(214, 476, textFieldSize.width, textFieldSize.height);
        // player 2 setting
        twoUp = new KeyTextField();
        twoUp.setBounds(563, 188, textFieldSize.width, textFieldSize.height);
        twoDown = new KeyTextField();
        twoDown.setBounds(563, 260, textFieldSize.width, textFieldSize.height);
        twoLeft = new KeyTextField();
        twoLeft.setBounds(563, 332, textFieldSize.width, textFieldSize.height);
        twoRight = new KeyTextField();
        twoRight.setBounds(563, 404, textFieldSize.width, textFieldSize.height);
        twoFire = new KeyTextField();
        twoFire.setBounds(563, 476, textFieldSize.width, textFieldSize.height);
        // save button
        saveButton = new JButton("Save");
        saveButton.setBounds(124, 584, buttonSize.width, buttonSize.height);
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
        backButton = new JButton("Return to Menu");
        backButton.setBounds(364, 584, buttonSize.width, buttonSize.height);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backgroundProcess.changeToGameMenu();
            }
        });
        // update key from properties
        updateKeyProp();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("img/bg-option-menu.png").getImage(), 0, 0, null);
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
