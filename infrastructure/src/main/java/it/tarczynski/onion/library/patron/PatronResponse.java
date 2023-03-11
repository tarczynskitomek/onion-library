package it.tarczynski.onion.library.patron;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.UUID;

@Builder(access = AccessLevel.PRIVATE)
public record PatronResponse(
        UUID id,
        String name,
        boolean regular,
        boolean researcher,
        String affiliation
) {

    public static PatronResponse from(PatronSnapshot snapshot) {
        return PatronResponse.builder()
                .id(snapshot.id().value())
                .name(snapshot.name().value())
                .regular(snapshot.isRegular())
                .researcher(snapshot.isResearcher())
                .affiliation(snapshot.affiliation().map(PatronAffiliation::value).orElse(null))
                .build();
    }
}
