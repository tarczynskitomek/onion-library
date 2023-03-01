package it.tarczynski.onion.library.book;

interface BookRepository {
    Book create(Book book);

    Book getById(BookId id);

    Book update(Book book);
}
