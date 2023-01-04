package it.tarczynski.onion.library;

import java.util.UUID;

record LoanId(UUID value) {
    public static LoanId next() {
        return new LoanId(UUID.randomUUID());
    }
}
