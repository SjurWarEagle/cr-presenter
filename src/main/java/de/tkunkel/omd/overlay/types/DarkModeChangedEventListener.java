package de.tkunkel.omd.overlay.types;

import java.util.EventListener;

public interface DarkModeChangedEventListener extends EventListener {
    void modeChanged(boolean newMode);
}
