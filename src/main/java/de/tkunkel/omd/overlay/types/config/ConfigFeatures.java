package de.tkunkel.omd.overlay.types.config;

public class ConfigFeatures {
    private boolean useTexts;
    private boolean useTimer;

    public boolean isUseTimer() {
        return useTimer;
    }

    public boolean isUseTexts() {
        return useTexts;
    }

    @SuppressWarnings("unused")
    public void setUseTimer(boolean givenUseTimer) {
        this.useTimer = givenUseTimer;
    }

    @SuppressWarnings("unused")
    public void setUseTexts(boolean givenUseTexts) {
        this.useTexts = givenUseTexts;
    }
}
