package de.tkunkel.omd.overlay.types;

import java.util.EventListener;

public interface InfoTextChangedEventListener extends EventListener {
    void infoTextChanged(String crNumber, String crSubject);
}
