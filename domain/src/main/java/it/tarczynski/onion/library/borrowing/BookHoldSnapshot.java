package it.tarczynski.onion.library.borrowing;

import lombok.Builder;

@Builder
public record BookHoldSnapshot(
        String id,
        String bookId,
        String patronId
) {
}
