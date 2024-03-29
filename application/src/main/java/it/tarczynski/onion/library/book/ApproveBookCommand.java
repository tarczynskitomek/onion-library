package it.tarczynski.onion.library.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApproveBookCommand {
    private String id;

    public BookId id() {
        return BookId.from(id);
    }
}
