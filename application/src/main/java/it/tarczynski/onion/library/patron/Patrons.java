package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class Patrons {

    private final Transactions transactions;

    PatronResponse createPatron(CreatePatronRequest request) {
        return new PatronResponse("1", request.name(), request.surname(), request.type());
    }
}
