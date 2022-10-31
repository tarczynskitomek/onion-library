package it.tarczynski.onion.library.shared

import java.util.function.Supplier

class NoOpTransactionsFake implements Transactions {

    @Override
    <T> T execute(Supplier<T> operation) {
        operation.get()
    }
}
