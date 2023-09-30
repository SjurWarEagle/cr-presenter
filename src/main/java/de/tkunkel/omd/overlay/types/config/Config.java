package de.tkunkel.omd.overlay.types.config;

public class Config {

    private int initialCountdownDurationInSeconds = 60;
    private final ConfigFeatures features = new ConfigFeatures();
    private final CrText[] crs = new CrText[0];

    @SuppressWarnings("unused")
    public void setInitialCountdownDurationInSeconds(int givenInitialCountdownDurationInSeconds) {
        this.initialCountdownDurationInSeconds = givenInitialCountdownDurationInSeconds;
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
