package it.tarczynski.onion.library.patron;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class Patron {

    private final PatronId id;

    public static Patron create() {
        return new Patron(PatronId.next());
    }

    public static Patron from(PatronSnapshot snapshot) {
        final PatronId id = PatronId.from(snapshot.id());
        return new Patron(id);
    }

    PatronSnapshot snapshot() {
        return PatronSnapshot.builder()
                .id(id.value().toString())
                .build();
    }
}
