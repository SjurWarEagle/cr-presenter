package de.tkunkel.omd.overlay.controls;

import de.tkunkel.omd.overlay.InfoFrame;
import de.tkunkel.omd.overlay.starter.Starter;
import de.tkunkel.omd.overlay.types.InfoTextChangedEventListener;
import de.tkunkel.omd.overlay.types.LockStateChangedEventListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ControlFrame extends JFrame {
    ImageIcon lockedImage = readTransparentImage("/icons/locked.png");
    ImageIcon unlockedImage = readTransparentImage("/icons/unlocked.png");
    JLabel lockUnlock = new JLabel();
    JTextField crNumber = new JTextField("CR????");
    JTextField crSubject = new JTextField("to do something");

    protected ArrayList<LockStateChangedEventListener> statusChangedListenerList = new ArrayList<>();
    protected ArrayList<InfoTextChangedEventListener> infoTextListenerList = new ArrayList<>();
    private boolean overlayLocked = false;

    public void addStateChangedListener(LockStateChangedEventListener listener) {
        statusChangedListenerList.add(listener);
    }

    @SuppressWarnings("unused")
    public void removeStateChangedListener(LockStateChangedEventListener listener) {
        statusChangedListenerList.remove(listener);
    }

    public void addInfoTextChangedListener(InfoTextChangedEventListener listener) {
        infoTextListenerList.add(listener);
    }

    @SuppressWarnings("unused")
    public void removeInfoTextChangedListener(InfoTextChangedEventListener listener) {
        infoTextListenerList.remove(listener);
    }

    public ControlFrame() {
        this.setLayout(new GridBagLayout());

        setTitle("Control");
        setBackground(Color.BLUE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocation(300, 300);
        setVisible(true);

        addCrNumberControl();
        addCrSubjectControl();
        addLockUnlockControl();

        pack();
        setVisible(true);

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/presentation.png")));
        setFocusOrdering();
    }

    private void setFocusOrdering() {
        crSubject.setNextFocusableComponent(crNumber);
        crNumber.setNextFocusableComponent(crSubject);
    }

    private void addLockUnlockControl() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        lockUnlock.setSize(new Dimension(60, 60));
        lockUnlock.setPreferredSize(lockUnlock.getSize());
        lockUnlock.setBorder(null);
        lockUnlock.setBackground(null);
        lockUnlock.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        lockUnlock.setDoubleBuffered(true);
        lockUnlock.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                overlayLocked = !overlayLocked;
                updateLockedIcon();
                statusChangedListenerList.forEach(lockStateChangedEventListener -> lockStateChangedEventListener.lockStateChanged(overlayLocked));
            }
        });
        updateLockedIcon();
        add(lockUnlock, gridBagConstraints);
    }

    private void addCrSubjectControl() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(new JLabel("CR Subject:"), gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(crSubject, gridBagConstraints);
        crSubject.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                infoTextListenerList.forEach(infoTextChangedEventListener -> infoTextChangedEventListener.infoTextChanged(crNumber.getText(), crSubject.getText()));
            }
        });
    }

    private void addCrNumberControl() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(new JLabel("CR Number:"), gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        add(crNumber, gridBagConstraints);
        crNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                infoTextListenerList.forEach(infoTextChangedEventListener -> infoTextChangedEventListener.infoTextChanged(crNumber.getText(), crSubject.getText()));
            }
        });

    }

    private void updateLockedIcon() {
        if (overlayLocked) {
            lockUnlock.setIcon(lockedImage);
        } else {
            lockUnlock.setIcon(unlockedImage);
        }
    }

    private ImageIcon readTransparentImage(String filename) {
        int newWidth = 50;
        @SuppressWarnings({"UnnecessaryLocalVariable", "SuspiciousNameCombination"})
        int newHeight = newWidth;
        BufferedImage in;

        try {

            in = ImageIO.read(Objects.requireNonNull(InfoFrame.class.getResource(filename)));
            return new ImageIcon(in.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
