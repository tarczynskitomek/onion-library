package it.tarczynski.onion.library;

import lombok.Builder;

@Builder
public record BookLoanSnapshot(
        String id,
        String bookId,
        String readerId
) {
}
