package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Patrons {

    private final PatronRepository patronRepository;
    private final Transactions transactions;

    public PatronSnapshot handle(CreateRegularPatronCommand command) {
        return transactions.execute(() -> {
            final Patron patron = Patron.createRegular(command.name());
            return patronRepository.create(patron).snapshot();
        });
    }

    public PatronSnapshot handle(CreateResearcherPatronCommand command) {
        return transactions.execute(() -> {
            final Patron patron = Patron.createResearcher(command.name(), command.affiliation());
            return patronRepository.create(patron).snapshot();
        });
    }
}
