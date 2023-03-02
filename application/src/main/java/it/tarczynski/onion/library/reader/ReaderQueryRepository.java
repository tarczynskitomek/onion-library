package it.tarczynski.onion.library.reader;

import lombok.AllArgsConstructor;

public interface ReaderQueryRepository {
    ReaderSnapshot getBy(ReaderId id);

    @AllArgsConstructor
    class InMemoryReaderQueryRepository implements ReaderQueryRepository {

        private final ReaderRepository readerRepository;

        @Override
        public ReaderSnapshot getBy(ReaderId id) {
            return readerRepository.getBy(id).snapshot();
        }
    }
}
