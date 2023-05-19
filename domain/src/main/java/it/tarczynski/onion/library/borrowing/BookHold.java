package it.tarczynski.onion.library.borrowing;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
class BookHold {

    private final HoldId id;
    private final BookId bookId;
    private final PatronId patronId;

    static BookHold create(BookId bookId, PatronId patronId) {
        return new BookHold(HoldId.next(), bookId, patronId);
    }

    public BookHoldSnapshot snapshot() {
        return BookHoldSnapshot.builder()
                .id(id.value().toString())
                .bookId(bookId.value().toString())
                .patronId(patronId.value().toString())
                .build();
    }
}
