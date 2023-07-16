package de.tkunkel.omd.overlay.starter;

import de.tkunkel.omd.overlay.Overlay;

import java.awt.*;

public class Starter {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Overlay jFrameOpacityExample = new Overlay();
            }
        });
    }
}
