package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.starter.Starter;
import de.tkunkel.omd.overlay.types.DarkModeChangedEventListener;
import de.tkunkel.omd.overlay.types.InfoTextChangedEventListener;
import de.tkunkel.omd.overlay.types.LockStateChangedEventListener;

import javax.swing.*;
import java.awt.*;

public class InfoFrame extends JFrame implements LockStateChangedEventListener, InfoTextChangedEventListener, DarkModeChangedEventListener {
    private final JLabel infoTextLabel = new JLabel();
    private boolean darkMode = false;
    private boolean isLocked = false;

    public InfoFrame() {
        setTitle("Move this window to the overlay position");

        this.setLayout(new GridBagLayout());

        this.getContentPane().setBackground(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, 100));
        lockStateChanged(false);

        Font mainTextFont = new Font("Serif", Font.PLAIN, 50);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;

//        infoTextLabel.setForeground(Color.BLUE);
        infoTextLabel.setFont(mainTextFont);
        this.add(infoTextLabel, gridBagConstraints);

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/presentation.png")));
        infoTextChanged("Ready", "Go");
        refreshDisplay();
        setVisible(true);
    }


    @Override
    public void lockStateChanged(boolean locked) {
        this.isLocked = locked;
        refreshDisplay();
    }

    private void refreshDisplay() {
        if (!this.isDisplayable()) {
            return;
        }
        if (darkMode) {
            infoTextLabel.setForeground(Color.WHITE);
        } else {
            infoTextLabel.setForeground(Color.BLACK);
        }
        if (this.isLocked) {
            this.dispose();
            this.setUndecorated(true);
            this.setBackground(new Color(0, 0, 255, 0));
            this.setVisible(true);
            this.setAlwaysOnTop(true);
        } else {
            this.dispose();
            if (darkMode) {
                this.setBackground(new Color(0, 0, 0, 255));
            } else {
                this.setBackground(new Color(255, 255, 255, 255));
            }
            this.setUndecorated(false);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
        }
    }

    @Override
    public void infoTextChanged(String crNumber, String crSubject) {
        this.infoTextLabel.setText(crNumber + ": " + crSubject);
        pack();
    }

    @Override
    public void modeChanged(boolean newMode) {
        this.darkMode = newMode;
        refreshDisplay();
    }
}
