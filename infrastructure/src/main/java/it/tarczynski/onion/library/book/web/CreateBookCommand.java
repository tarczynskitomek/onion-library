package it.tarczynski.onion.library.book.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {
    private String title;
    private Author author;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private String id;
    }
}
