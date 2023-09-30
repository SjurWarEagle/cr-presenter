package de.tkunkel.omd.overlay.types.config;

public class CrText {
    private String crNumber;
    private String crSubject;

    public String getCrSubject() {
        return crSubject;
    }

    public String getCrNumber() {
        return crNumber;
    }

    @SuppressWarnings("unused")
    public void setCrNumber(String givenCrNumber) {
        this.crNumber = givenCrNumber;
    }

    @SuppressWarnings("unused")
    public void setCrSubject(String givenCrSubject) {
        this.crSubject = givenCrSubject;
    }
}
