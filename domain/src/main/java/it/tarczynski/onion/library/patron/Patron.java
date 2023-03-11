package it.tarczynski.onion.library.patron;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class Patron {

    private final PatronId id;
    private final ReaderAge age;

    public static Patron create(ReaderAge age) {
        return new Patron(PatronId.next(), age);
    }

    public static Patron from(PatronSnapshot snapshot) {
        final PatronId id = PatronId.from(snapshot.id());
        final ReaderAge age = ReaderAge.of(snapshot.age());
        return new Patron(id, age);
    }

    PatronSnapshot snapshot() {
        return PatronSnapshot.builder()
                .id(id.value().toString())
                .age(age.value())
                .build();
    }
}
