package de.tkunkel.omd.overlay;

import com.google.gson.Gson;
import de.tkunkel.omd.overlay.controls.ControlFrame;
import de.tkunkel.omd.overlay.types.CrTexts;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class Overlay {

    public Overlay() {
    }

    public void start(String fileNameToRead) {
        InfoFrame infoFrame = new InfoFrame();

        ControlFrame controlFrame = new ControlFrame();
        controlFrame.addStateChangedListener(infoFrame);
        controlFrame.addDarkModeChangedListener(infoFrame);
        controlFrame.addInfoTextChangedListener(infoFrame);
        controlFrame.setCrTexts(readTexts(fileNameToRead));

    }

    private CrTexts readTexts(String fileNameToRead) {
        Collection<String> rc = new ArrayList<>();
        try {
            String lines = Files.readString(Paths.get(fileNameToRead));
            CrTexts crTexts = new Gson().fromJson(lines, CrTexts.class);
            return crTexts;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}