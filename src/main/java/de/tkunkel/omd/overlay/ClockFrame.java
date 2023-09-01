package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.starter.Starter;
import de.tkunkel.omd.overlay.types.ClockLockStateChangedEventListener;
import de.tkunkel.omd.overlay.types.DarkModeChangedEventListener;
import de.tkunkel.omd.overlay.types.InfoTextChangedEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClockFrame extends JFrame implements ClockLockStateChangedEventListener, InfoTextChangedEventListener, DarkModeChangedEventListener {
    private final static JLabel infoTextLabel = new JLabel();
    private final static JProgressBar progressBar = new JProgressBar();
    private boolean darkMode = false;
    private boolean isLocked = false;
    private static long remainingDurationInSec;
    private static long initialDurationInSec;

    public ClockFrame(long durationInSec) {
        setTitle("Move this window to the overlay position");

        Thread thread = new Thread(() -> {
            while (true) {
                String timeString;
                ClockFrame.remainingDurationInSec--;
                if (ClockFrame.remainingDurationInSec <= 0) {
                    timeString = "OUT!";
                } else {
                    int hours = Math.toIntExact((ClockFrame.remainingDurationInSec - ClockFrame.remainingDurationInSec % 60) / 60);
                    timeString = String.format("%02d:%02d", hours, (ClockFrame.remainingDurationInSec % 60));
                }
                ClockFrame.infoTextLabel.setText(timeString);

                ClockFrame.progressBar.setValue((int) ClockFrame.remainingDurationInSec);

                ClockFrame.progressBar.updateUI();
//                System.out.println(progressBar.getPercentComplete());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        ClockFrame.initialDurationInSec = durationInSec;
        ClockFrame.remainingDurationInSec = durationInSec - 50;

        ClockFrame.progressBar.setMinimum(0);
        ClockFrame.progressBar.setMaximum(Math.toIntExact(initialDurationInSec));
        ClockFrame.progressBar.updateUI();

        this.setLayout(new GridBagLayout());

        this.getContentPane().setBackground(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, 100));
        clockLockStateChanged(false);

        addTimeText();
        addTimeBar();

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/presentation.png")));
        infoTextChanged("Ready", "Go");
        refreshDisplay();
        setVisible(true);
    }

    private void addTimeBar() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;

        progressBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClockFrame.resetTimer();
            }
        });
        this.add(progressBar, gridBagConstraints);
    }

    private void addTimeText() {
        Font mainTextFont = new Font("Serif", Font.PLAIN, 50);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;

        infoTextLabel.setFont(mainTextFont);
        this.add(infoTextLabel, gridBagConstraints);

        infoTextLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClockFrame.resetTimer();
            }
        });
    }

    private static void resetTimer() {
        ClockFrame.remainingDurationInSec = ClockFrame.initialDurationInSec;
        //System.out.println("resetting timer");
    }


    @Override
    public void clockLockStateChanged(boolean locked) {
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

    public void setDurationInSec(long durationInSec) {
        this.initialDurationInSec = durationInSec;
    }
}
