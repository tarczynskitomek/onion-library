package it.tarczynski.onion.library.reader;

import java.util.HashMap;
import java.util.Map;

interface ReaderRepository {

    Reader save(Reader reader);

    Reader getBy(ReaderId id);

    class InMemoryReaderRepository implements ReaderRepository {

        private final Map<String, Reader> readers = new HashMap<>();

        @Override
        public Reader save(Reader reader) {
            readers.put(reader.snapshot().id(), reader);
            return reader;
        }

        @Override
        public Reader getBy(ReaderId id) {
            return readers.get(id.value().toString());
        }
    }
}
