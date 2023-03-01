package it.tarczynski.onion.library.book;

public interface BookQueryRepository {

    BookSnapshot getBy(BookId id);
}
