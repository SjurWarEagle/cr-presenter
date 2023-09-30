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

    public void setCrNumber(String givenCrNumber) {
        this.crNumber = givenCrNumber;
    }

    public void setCrSubject(String givenCrSubject) {
        this.crSubject = givenCrSubject;
    }
}
