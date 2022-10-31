package it.tarczynski.onion.library.shared;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Component
@AllArgsConstructor
class JdbcTransactions implements Transactions {

    private final TransactionTemplate transactionTemplate;

    @Override
    public <T> T execute(Supplier<T> operation) {
        return transactionTemplate.execute(status -> operation.get());
    }
}
