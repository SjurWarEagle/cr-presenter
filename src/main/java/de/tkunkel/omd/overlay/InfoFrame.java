package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.starter.Starter;
import de.tkunkel.omd.overlay.types.InfoTextChangedEventListener;
import de.tkunkel.omd.overlay.types.LockStateChangedEventListener;

import javax.swing.*;
import java.awt.*;

public class InfoFrame extends JFrame implements LockStateChangedEventListener, InfoTextChangedEventListener {
    private JLabel infoTextLabel = new JLabel();

    public InfoFrame() {
        setTitle("Move this window to the overlay position");

        this.setLayout(new GridBagLayout());

        this.getContentPane().setBackground(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 200);
        lockStateChanged(true);

        Font mainTextFont = new Font("Serif", Font.PLAIN, 50)
                .deriveFont(Font.PLAIN);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.NONE;

        infoTextLabel.setForeground(Color.CYAN);
        infoTextLabel.setFont(mainTextFont);
        this.add(infoTextLabel, gridBagConstraints);

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/presentation.png")));
        setVisible(true);
    }


    @Override
    public void lockStateChanged(boolean locked) {
        if (!this.isDisplayable()) {
            return;
        }
        if (locked) {
            this.dispose();
            this.setUndecorated(true);
            this.setBackground(new Color(0, 0, 255, 0));
            this.setVisible(true);
            this.setAlwaysOnTop(true);
        } else {
            this.dispose();
            this.setBackground(new Color(0, 0, 255, 255));
            this.setUndecorated(false);
            this.setVisible(true);
            this.setAlwaysOnTop(false);
        }
    }

    @Override
    public void infoTextChanged(String crNumber, String crSubject) {
        this.infoTextLabel.setText(crNumber + ": " + crSubject);
    }
}
