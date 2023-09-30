package de.tkunkel.omd.overlay.types.config;

public class Config {

    private int initialCountdownDurationInSeconds = 60;
    private ConfigFeatures features = new ConfigFeatures();
    private final CrText[] crs = new CrText[0];

    public void setInitialCountdownDurationInSeconds(int initialCountdownDurationInSeconds) {
        this.initialCountdownDurationInSeconds = initialCountdownDurationInSeconds;
    }

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
