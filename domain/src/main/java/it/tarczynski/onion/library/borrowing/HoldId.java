package it.tarczynski.onion.library.borrowing;

import java.util.UUID;

record HoldId(UUID value) {
    public static HoldId next() {
        return new HoldId(UUID.randomUUID());
    }
}
