package it.tarczynski.onion.library;

import lombok.Builder;

@Builder
public record BookHoldSnapshot(
        String id,
        String bookId,
        String patronId
) {
}
