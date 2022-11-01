package it.tarczynski.onion.library.book;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class PostgresQueryRepository implements BookQueryRepository {

    private final BookRepository delegate;

    @Override
    public BookSnapshot getBy(BookId id) {
        return delegate.getById(id).snapshot();
    }
}
