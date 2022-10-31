package it.tarczynski.onion.library.book;

import it.tarczynski.onion.library.shared.TimeMachine;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
class BookConfiguration {

    private final BookRepository bookRepository;
    private final TimeMachine timeMachine;
    private final Transactions transactions;

    @Bean
    Books bookFacade() {
        return new Books(bookRepository, timeMachine, transactions);
    }
}
