package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
class PatronConfiguration {

    private final PatronRepository patronRepository;
    private final Transactions transactions;

    @Bean
    Patrons patrons() {
        return new Patrons(patronRepository, transactions);
    }

    @Bean
    PatronQueryRepository readerQueryRepository() {
        return new PatronQueryRepository.InMemoryPatronQueryRepository(patronRepository);
    }
}
