package de.tkunkel.omd.overlay.types;

import java.util.EventListener;

public interface LockStateChangedEventListener extends EventListener {
    void lockStateChanged(boolean newState);
}
