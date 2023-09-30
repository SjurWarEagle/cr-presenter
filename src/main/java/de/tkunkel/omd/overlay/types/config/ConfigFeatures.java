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

    public void setUseTimer(boolean useTimer) {
        this.useTimer = useTimer;
    }

    public void setUseTexts(boolean useTexts) {
        this.useTexts = useTexts;
    }
}
