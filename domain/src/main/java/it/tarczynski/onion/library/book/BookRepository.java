package it.tarczynski.onion.library.book;

interface BookRepository {
    Book save(Book book);

    Book getById(BookId id);
}
