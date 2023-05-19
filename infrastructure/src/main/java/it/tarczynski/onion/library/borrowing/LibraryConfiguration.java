package it.tarczynski.onion.library.borrowing;

import it.tarczynski.onion.library.patron.PatronQueryRepository;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
class LibraryConfiguration {

    private final HoldRepository holdRepository;
    private final Transactions transactions;
    private final PatronQueryRepository patronQueryRepository;

    @Bean
    Library library() {
        return new Library(holdRepository, transactions, new NoopBookHoldingPolicy(patronQueryRepository));
    }
}
