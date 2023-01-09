package it.tarczynski.onion.library.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveBookCommand {
    private String id;

    public BookId id() {
        return BookId.from(id);
    }
}
