package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.author.AuthorId;
import it.tarczynski.onion.library.shared.Title;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {

    @NotNull
    private BookType type;
    @NotBlank
    private String title;
    @NotNull
    @Valid
    private Author author;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        @NotBlank
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
