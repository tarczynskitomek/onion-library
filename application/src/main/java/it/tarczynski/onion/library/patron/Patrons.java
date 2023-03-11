package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Patrons {

    private final PatronRepository patronRepository;
    private final Transactions transactions;

    public PatronSnapshot handle(CreatePatronCommand command) {
        return transactions.execute(() -> {
            final Patron patron = Patron.create();
            return patronRepository.create(patron).snapshot();
        });
    }
}
