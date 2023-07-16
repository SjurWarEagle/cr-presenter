package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.starter.Starter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Objects;

interface LockStateChangedEventListener extends EventListener {
    public void lockStateChanged(boolean newState);
}

interface InfoTextChangedEventListener extends EventListener {
    public void infoTextChanged(String crNumber, String crSubject);
}

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

    public void removeStateChangedListener(LockStateChangedEventListener listener) {
        statusChangedListenerList.remove(listener);
    }

    public void addInfoTextChangedListener(InfoTextChangedEventListener listener) {
        infoTextListenerList.add(listener);
    }

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

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(new JLabel("CR Number:"),gridBagConstraints);

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

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        add(new JLabel("CR Subject:"),gridBagConstraints);

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

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        lockUnlock.setSize(50, 50);
        lockUnlock.setBorder(null);
        lockUnlock.setBackground(null);
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

        pack();
        setVisible(true);

        setIconImage(Toolkit.getDefaultToolkit().getImage(Starter.class.getResource("/icons/presentation.png")));
    }

    private void updateLockedIcon() {
        if (overlayLocked) {
            lockUnlock.setIcon(lockedImage);
        } else {
            lockUnlock.setIcon(unlockedImage);
        }
    }

    private ImageIcon readTransparentImage(String filename) {
        BufferedImage in = null;
        BufferedImage newImage = null;
        try {
            in = ImageIO.read(Objects.requireNonNull(InfoFrame.class.getResource(filename)));
            in.getScaledInstance(50, 50, 0);
            newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            int newWidth = 50;
            int newHeight = newWidth;
            g.drawImage(in, 0, 0, newWidth, newHeight, 0, 0, in.getWidth(),
                    in.getHeight(), null);
            g.dispose();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;
        return new ImageIcon(newImage);
    }

}
