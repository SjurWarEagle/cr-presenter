package de.tkunkel.omd.overlay.controls.types;

import java.util.EventListener;

public interface InfoTextChangedEventListener extends EventListener {
    public void infoTextChanged(String crNumber, String crSubject);
}
