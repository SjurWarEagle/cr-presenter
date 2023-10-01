package de.tkunkel.omd.overlay;

import com.google.gson.Gson;
import de.tkunkel.omd.overlay.controls.ControlFrame;
import de.tkunkel.omd.overlay.types.config.Config;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Overlay {

    public Overlay() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
                 | InstantiationException
                 | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(final String fileNameToRead) {
        ClockFrame clockFrame = new ClockFrame(100);
        InfoFrame infoFrame = new InfoFrame();
        Config config = readConfig(fileNameToRead);

        ControlFrame controlFrame = new ControlFrame(config);
        controlFrame.addStateChangedListener(infoFrame);
        controlFrame.addDarkModeChangedListener(infoFrame);
        controlFrame.addInfoTextChangedListener(infoFrame);

        controlFrame.addClockLockChangedListener(clockFrame);
        controlFrame.addDarkModeChangedListener(clockFrame);
        controlFrame.addClockLockChangedListener(clockFrame);
        controlFrame.addClockDurationChangedListenerList(clockFrame);

        controlFrame.setConfigCrTexts(config);
        clockFrame.setDurationInSec(config.getInitialCountdownDurationInSeconds());

        clockFrame.setUse(config.getFeatures().isUseTimer());
        infoFrame.setUse(config.getFeatures().isUseTexts());
    }


    private Config readConfig(final String fileNameToRead) {
        if (Objects.isNull(fileNameToRead)) {
            return new Config();
        }
        if (!Files.exists(Path.of(fileNameToRead))) {
            System.err.println("'"
                    + fileNameToRead
                    + "' does not exist, using defaults.");
            return new Config();
        }
        try {
            String lines = Files.readString(Paths.get(fileNameToRead));
            return new Gson().fromJson(lines, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
