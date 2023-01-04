package it.tarczynski.onion.library.reader;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
class Reader {

    private final ReaderId id;
    private final ReaderAge age;

    public static Reader create(ReaderAge age) {
        return new Reader(ReaderId.next(), age);
    }

    ReaderSnapshot snapshot() {
        return ReaderSnapshot.builder()
                .id(id.value().toString())
                .age(age.value())
                .build();
    }
}
