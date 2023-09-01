package de.tkunkel.omd.overlay;

import com.google.gson.Gson;
import de.tkunkel.omd.overlay.controls.ControlFrame;
import de.tkunkel.omd.overlay.types.Config;

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
        controlFrame.addClockDurationChangedListenerList(clockFrame);

        if (Objects.isNull(fileNameToRead)) {
            Config texts = new Config();
            controlFrame.setConfigCrTexts(texts);
            clockFrame.setUse(false);
            infoFrame.setUse(false);
        } else {
            Config config = readTexts(fileNameToRead);
            controlFrame.setConfigCrTexts(config);
            clockFrame.setDurationInSec(config.initialCountdownDurationInSeconds);

            clockFrame.setUse(config.features.useTimer);
            infoFrame.setUse(config.features.useTexts);
        }


    }

    private Config readTexts(String fileNameToRead) {
        try {
            String lines = Files.readString(Paths.get(fileNameToRead));
            return new Gson().fromJson(lines, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}