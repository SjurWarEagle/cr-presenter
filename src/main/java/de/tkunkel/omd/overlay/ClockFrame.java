package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.starter.Starter;
import de.tkunkel.omd.overlay.types.ClockDurationChangedEventListener;
import de.tkunkel.omd.overlay.types.ClockLockStateChangedEventListener;
import de.tkunkel.omd.overlay.types.DarkModeChangedEventListener;
import de.tkunkel.omd.overlay.types.InfoTextChangedEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class ClockFrame extends JFrame implements ClockLockStateChangedEventListener, InfoTextChangedEventListener, DarkModeChangedEventListener, ClockDurationChangedEventListener {
    private static final JLabel INFO_TEXT_LABEL = new JLabel();
    private static final JProgressBar PROGRESS_BAR = new JProgressBar();
    private boolean darkMode = false;
    private boolean isLocked = false;
    private static long remainingDurationInSec;
    private static int initialDurationInSec;
    private boolean use = true;
    private final Font digitsFont;

    public ClockFrame(final int durationInSec) {
        setTitle("Move this window to the overlay position");

        try {
            InputStream is = ClockFrame.class.getResourceAsStream("/DSEG7Modern-Regular.ttf");
            digitsFont = Font.createFont(Font.TRUETYPE_FONT, is);
            INFO_TEXT_LABEL.setFont(digitsFont);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        Thread thread = new Thread(() -> {
            while (true) {
                updateTimeStringInUi();
                try {
                    //noinspection BusyWait
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();

        ClockFrame.initialDurationInSec = durationInSec;
        ClockFrame.remainingDurationInSec = durationInSec - 50;

        ClockFrame.PROGRESS_BAR.setMinimum(0);
        ClockFrame.PROGRESS_BAR.setMaximum(Math.toIntExact(initialDurationInSec));

        this.setLayout(new GridBagLayout());

        this.getContentPane().setBackground(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(200, 100));
        clockLockStateChanged(false);

        addTimeText();
        addTimeBar();

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/clock_unlocked.png")));
        infoTextChanged("Ready", "Go");
        refreshDisplay();
        setVisible(true);
    }

    private void updateTimeStringInUi() {
        SwingUtilities.invokeLater(() -> {

            String timeString;
            ClockFrame.remainingDurationInSec--;
            if (ClockFrame.remainingDurationInSec <= 0) {
                timeString = "OUT!";
                ClockFrame.PROGRESS_BAR.setBackground(Color.YELLOW);
                ClockFrame.PROGRESS_BAR.setForeground(Color.RED);
                ClockFrame.INFO_TEXT_LABEL.setForeground(Color.RED);
            } else if (ClockFrame.remainingDurationInSec <= 60 * 9) {
                int hours = Math.toIntExact((ClockFrame.remainingDurationInSec - ClockFrame.remainingDurationInSec % 60) / 60);
                if ("OUT!".equalsIgnoreCase(ClockFrame.INFO_TEXT_LABEL.getText())) {
                    refreshDisplay();
                }
                timeString = String.format("%02d : %02d", hours, (ClockFrame.remainingDurationInSec % 60));
                ClockFrame.INFO_TEXT_LABEL.setForeground(Color.MAGENTA);
            } else {
                int hours = Math.toIntExact((ClockFrame.remainingDurationInSec - ClockFrame.remainingDurationInSec % 60) / 60);
                if ("OUT!".equalsIgnoreCase(ClockFrame.INFO_TEXT_LABEL.getText())) {
                    refreshDisplay();
                }
                timeString = String.format("%02d : %02d", hours, (ClockFrame.remainingDurationInSec % 60));
            }
            ClockFrame.INFO_TEXT_LABEL.setText(timeString);

            ClockFrame.PROGRESS_BAR.setValue((int) ClockFrame.remainingDurationInSec);
        });
    }

    private void addTimeBar() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;

        ClockFrame.PROGRESS_BAR.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                resetTimer();
            }
        });
        this.add(ClockFrame.PROGRESS_BAR, gridBagConstraints);
    }

    private void addTimeText() {
        Font mainTextFont = digitsFont.deriveFont(50f);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;

        INFO_TEXT_LABEL.setFont(mainTextFont);
        this.add(INFO_TEXT_LABEL, gridBagConstraints);

        INFO_TEXT_LABEL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                resetTimer();
            }
        });
    }

    private void resetTimer() {
        ClockFrame.remainingDurationInSec = ClockFrame.initialDurationInSec;
        refreshDisplay();
    }


    @Override
    public void clockLockStateChanged(final boolean locked) {
        this.isLocked = locked;
        refreshDisplay();
    }

    private void refreshDisplay() {
        SwingUtilities.invokeLater(() -> {

            if (!this.isDisplayable()) {
                return;
            }
            Color bgColor;
            Color fgColor;
            if (darkMode) {
                bgColor = Color.WHITE;
                fgColor = Color.WHITE;
            } else {
                bgColor = Color.BLACK;
                fgColor = Color.BLACK;
            }
            this.setForeground(fgColor);
            this.setBackground(bgColor);

            INFO_TEXT_LABEL.setBackground(bgColor);
            INFO_TEXT_LABEL.setForeground(bgColor);
            ClockFrame.PROGRESS_BAR.setBackground(fgColor);
            ClockFrame.PROGRESS_BAR.setForeground(bgColor);

            if (this.isLocked) {
                this.dispose();
                this.setUndecorated(true);
                this.setBackground(new Color(0, 0, 255, 0));
                this.setVisible(true);
                this.setAlwaysOnTop(true);
            } else {
                this.dispose();
                if (darkMode) {
                    bgColor = Color.BLACK;
                } else {
                    bgColor = Color.WHITE;
                }

                this.setBackground(bgColor);

                this.setUndecorated(false);
                this.setVisible(true);
                this.setAlwaysOnTop(false);
                ClockFrame.PROGRESS_BAR.setBackground(bgColor);
                ClockFrame.PROGRESS_BAR.setForeground(fgColor);
            }
        });
    }

    @Override
    public void infoTextChanged(final String crNumber, final String crSubject) {
        INFO_TEXT_LABEL.setText(crNumber + ": " + crSubject);
        pack();
    }

    @Override
    public void modeChanged(final boolean newMode) {
        if (!use) {
            return;
        }
        this.darkMode = newMode;
        refreshDisplay();
    }

    public void setDurationInSec(final int durationInSec) {
        initialDurationInSec = durationInSec;
        ClockFrame.remainingDurationInSec = durationInSec;
        ClockFrame.PROGRESS_BAR.setMaximum(Math.toIntExact(initialDurationInSec));
        ClockFrame.PROGRESS_BAR.invalidate();
        updateTimeStringInUi();
    }

    public void setUse(final boolean useTimer) {
        this.use = useTimer;
        this.setVisible(use);
        if (!use) {
            this.dispose();
        }
    }

    @Override
    public void durationChanged(final int newDuration) {
        setDurationInSec(newDuration);
    }
}
