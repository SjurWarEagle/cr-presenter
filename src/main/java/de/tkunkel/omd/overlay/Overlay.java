package de.tkunkel.omd.overlay;

import com.google.gson.Gson;
import de.tkunkel.omd.overlay.controls.ControlFrame;
import de.tkunkel.omd.overlay.types.CrTexts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Overlay {

    public Overlay() {
    }

    public void start(String fileNameToRead) {
        ClockFrame clockFrame = new ClockFrame(100);
        InfoFrame infoFrame = new InfoFrame();

        ControlFrame controlFrame = new ControlFrame();
        controlFrame.addStateChangedListener(infoFrame);
        controlFrame.addDarkModeChangedListener(infoFrame);
        controlFrame.addInfoTextChangedListener(infoFrame);

        controlFrame.addClockLockChangedListener(clockFrame);
        controlFrame.addDarkModeChangedListener(clockFrame);
        controlFrame.addClockLockChangedListener(clockFrame);

        if (Objects.isNull(fileNameToRead)){
            CrTexts texts=new CrTexts();
            controlFrame.setCrTexts(texts);
        }else {
            CrTexts crTexts = readTexts(fileNameToRead);
            controlFrame.setCrTexts(crTexts);
            clockFrame.setDurationInSec(crTexts.countdown);
        }

    }

    private CrTexts readTexts(String fileNameToRead) {
        try {
            String lines = Files.readString(Paths.get(fileNameToRead));
            return new Gson().fromJson(lines, CrTexts.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}