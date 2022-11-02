package it.tarczynski.onion.library.shared

import java.time.Instant
import java.time.temporal.TemporalUnit

class TimeMachineFake implements TimeMachine {

    private Instant currentTime = TimeFixture.NOW

    @Override
    Instant now() {
        currentTime
    }

    void advanceBy(long value, TemporalUnit unit) {
        currentTime = currentTime.plus(value, unit)
    }

    void reset() {
        this.currentTime = TimeFixture.NOW
    }
}
