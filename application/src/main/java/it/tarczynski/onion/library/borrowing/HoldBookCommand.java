package it.tarczynski.onion.library.borrowing;

import it.tarczynski.onion.library.book.BookId;
import it.tarczynski.onion.library.patron.PatronId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HoldBookCommand {

    private String bookId;
    private String patronId;

    BookId bookId() {
        return BookId.from(bookId);
    }

    PatronId patronId() {
        return PatronId.from(patronId);
    }
}
