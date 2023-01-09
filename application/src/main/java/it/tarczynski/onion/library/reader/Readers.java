package it.tarczynski.onion.library.reader;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Readers {

    private final ReaderRepository readerRepository;
    private final Transactions transactions;

    public ReaderSnapshot handle(CreateReaderCommand command) {
        return transactions.execute(() -> {
            final ReaderAge age = command.age();
            final Reader reader = Reader.create(age);
            return readerRepository.create(reader).snapshot();
        });
    }
}
