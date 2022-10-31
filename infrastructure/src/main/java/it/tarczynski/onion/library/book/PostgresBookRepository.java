package it.tarczynski.onion.library.book;

import org.springframework.stereotype.Component;

@Component
class PostgresBookRepository implements BookRepository {

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Book getById(BookId id) {
        return null;
    }
}
