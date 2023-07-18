package de.tkunkel.omd.overlay.controls.types;

import java.util.EventListener;

public interface LockStateChangedEventListener extends EventListener {
    public void lockStateChanged(boolean newState);
}
