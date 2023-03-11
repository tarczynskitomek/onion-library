package it.tarczynski.onion.library.patron;

import lombok.Builder;

import java.util.Optional;

@Builder
public class PatronSnapshot {
    private final PatronId id;
    private final PatronName name;
    private final PatronType type;
    private final PatronAffiliation affiliation;

    PatronId id() {
        return id;
    }

    PatronName name() {
        return name;
    }

    PatronType type() {
        return type;
    }

    boolean isRegular() {
        return PatronType.REGULAR.equals(type);
    }

    boolean isResearcher() {
        return PatronType.RESEARCHER.equals(type);
    }

    Optional<PatronAffiliation> affiliation() {
        return Optional.ofNullable(affiliation);
    }
}
