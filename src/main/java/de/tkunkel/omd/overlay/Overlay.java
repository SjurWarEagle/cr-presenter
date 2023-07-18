package de.tkunkel.omd.overlay;

import de.tkunkel.omd.overlay.controls.ControlFrame;

import java.awt.*;

public class Overlay {

    public Overlay() {
        InfoFrame infoFrame = new InfoFrame();
        ControlFrame controlFrame = new ControlFrame();
        controlFrame.addStateChangedListener(infoFrame);
        controlFrame.addInfoTextChangedListener(infoFrame);
        infoFrame.setBackground(Color.BLUE);


    }

}