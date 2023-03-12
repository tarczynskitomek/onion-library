package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {

    private BookType type;
    private String title;
    private Author author;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String id;
    }

    public Title title() {
        return Title.of(title);
    }

    public AuthorId author() {
        return AuthorId.from(author.id);
    }

    public BookType type() {
        return type;
    }
}
