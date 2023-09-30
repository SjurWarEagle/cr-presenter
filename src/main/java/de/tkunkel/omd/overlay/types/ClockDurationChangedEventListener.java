package de.tkunkel.omd.overlay.types;

import java.util.EventListener;

public interface ClockDurationChangedEventListener extends EventListener {
    void durationChanged(int newDuration);
}
