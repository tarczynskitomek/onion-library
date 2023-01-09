package it.tarczynski.onion.library.reader;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class Reader {

    private final ReaderId id;
    private final ReaderAge age;

    public static Reader create(ReaderAge age) {
        return new Reader(ReaderId.next(), age);
    }

    public static Reader from(ReaderSnapshot snapshot) {
        final ReaderId id = ReaderId.from(snapshot.id());
        final ReaderAge age = ReaderAge.of(snapshot.age());
        return new Reader(id, age);
    }

    ReaderSnapshot snapshot() {
        return ReaderSnapshot.builder()
                .id(id.value().toString())
                .age(age.value())
                .build();
    }
}
