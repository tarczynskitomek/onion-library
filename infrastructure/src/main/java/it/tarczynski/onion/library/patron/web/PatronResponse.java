package it.tarczynski.onion.library.patron.web;

import it.tarczynski.onion.library.patron.PatronSnapshot;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record PatronResponse(
        UUID id,
        String name,
        boolean regular
) {

    public static PatronResponse from(PatronSnapshot snapshot) {
        return PatronResponse.builder()
                .id(snapshot.id().value())
                .name(snapshot.name().value())
                .regular(snapshot.isRegular())
                .build();
    }
}
