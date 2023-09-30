package de.tkunkel.omd.overlay.types.config;

public class Config {

    private final int initialCountdownDurationInSeconds = 60;
    private ConfigFeatures features;
    private final CrText[] crs = new CrText[0];

    public int getInitialCountdownDurationInSeconds() {
        return initialCountdownDurationInSeconds;
    }

    public ConfigFeatures getFeatures() {
        return features;
    }

    public CrText[] getCrs() {
        return crs;
    }
}
