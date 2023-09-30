package de.tkunkel.omd.overlay.types;

import java.util.EventListener;

public interface ClockLockStateChangedEventListener extends EventListener {
    void clockLockStateChanged(boolean newState);
}
