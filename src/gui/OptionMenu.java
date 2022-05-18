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
import java.util.HashMap;

import config.MovementConfig.KeyProp;

public class OptionMenu extends JPanel{

    private BackgroundProcess backgroundProcess;
    private JButton saveButton;
    private JButton backButton;

    private HashMap<Enum, KeyTextField> keyTextFields;
    private HashMap<Enum, Integer> keyCodes;

    private KeyChangeListener keyChangeListener;

    OptionMenu(BackgroundProcess backgroundProcess) {
        this.backgroundProcess = backgroundProcess;

        initMenu();

        setLayout(null);
        add(saveButton);
        add(backButton);
        for(Enum key: keyTextFields.keySet()) {
            add(keyTextFields.get(key));
        }
        keyChangeListener = new KeyChangeListener();
        addKeyListener(keyChangeListener);

        setPreferredSize(new Dimension(690, 690));
    }

    public void updateKeyProp() {
        // re-read config file to get latest saved update
        MovementConfig key = MovementConfig.getInstance();
        for(Enum keyProp: keyTextFields.keySet()) {
            keyTextFields.get(keyProp).setText(key.getKeyText((KeyProp) keyProp));
            keyCodes.put(keyProp, key.getKeyCode((KeyProp) keyProp));
        }
        // reset state of key listener
        if(keyChangeListener != null) {
            keyChangeListener.reset();
        }
    }

    private void initMenu() {
        Dimension buttonSize = new Dimension(201, 51);
        Dimension textFieldSize = new Dimension(100, 30);

        keyTextFields = new HashMap<>();
        keyCodes = new HashMap<>();
        for(KeyProp keyProp: KeyProp.values()) {
            keyTextFields.put(keyProp, new KeyTextField());
        }
        // set layout
        keyTextFields.get(KeyProp.ONE_UP).setBounds(214, 188, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.ONE_DOWN).setBounds(214, 260, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.ONE_LEFT).setBounds(214, 332, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.ONE_RIGHT).setBounds(214, 404, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.ONE_FIRE).setBounds(214, 476, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.TWO_UP).setBounds(563, 188, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.TWO_DOWN).setBounds(563, 260, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.TWO_LEFT).setBounds(563, 332, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.TWO_RIGHT).setBounds(563, 404, textFieldSize.width, textFieldSize.height);
        keyTextFields.get(KeyProp.TWO_FIRE).setBounds(563, 476, textFieldSize.width, textFieldSize.height);
        // save button
        saveButton = new JButton("Save");
        saveButton.setBounds(124, 584, buttonSize.width, buttonSize.height);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MovementConfig key = MovementConfig.getInstance();
                if(!keyChangeListener.isWaiting) {
                    for(Enum keyProp: keyCodes.keySet()) {
                        key.updateProp((KeyProp) keyProp, keyCodes.get(keyProp));
                    }
                    key.saveProp();
                } else {
                    updateKeyProp();
                    JOptionPane.showMessageDialog(backgroundProcess.getWindow(), "Please specify your new keys again");
                }
            }
        });
        saveButton.setFocusable(false); // solving losing focus issue
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
            setFocusable(false);
            setEditable(false);
            ((AbstractDocument) getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        }
    }

    private class KeyChangeListener extends KeyAdapter {
        private boolean isWaiting = false;
        private KeyProp currentKeyProp;

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int inputCode = e.getKeyCode();
            if(!isWaiting) {
                for (Enum keyProp: keyCodes.keySet()) {
                    if(keyCodes.get(keyProp) == inputCode) {
                        isWaiting = true;
                        currentKeyProp = (KeyProp) keyProp;
                        keyTextFields.get(currentKeyProp).setText("PRESS ANY KEY");
                        break;
                    }
                }
            } else {
                boolean isSame = false;
                for (Enum keyProp : keyCodes.keySet()) {
                    if (keyCodes.get(keyProp) == inputCode && (KeyProp) keyProp != currentKeyProp) {
                        isSame = true;
                        break;
                    }
                }
                if (!isSame) {
                    isWaiting = false;
                    keyCodes.put(currentKeyProp, inputCode);
                    keyTextFields.get(currentKeyProp).setText(KeyEvent.getKeyText(inputCode));
                } else {
                    keyTextFields.get(currentKeyProp).setText("TRY AGAIN, KEY EXISTED");
            }}
        }

        private void reset() {
            isWaiting = false;
        }
    }
}
