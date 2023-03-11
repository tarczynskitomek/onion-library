package it.tarczynski.onion.library.patron;

import lombok.Builder;

@Builder
public record PatronSnapshot(PatronId id, PatronName name, PatronType type) {

    public boolean isRegular() {
        return PatronType.REGULAR.equals(type);
    }
}
