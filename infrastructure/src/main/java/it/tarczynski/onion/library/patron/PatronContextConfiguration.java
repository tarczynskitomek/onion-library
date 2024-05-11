package it.tarczynski.onion.library.patron;

import it.tarczynski.onion.library.shared.Transactions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class PatronContextConfiguration {

    @Bean
    Patrons patrons(Transactions transactions) {
        return new Patrons(transactions);
    }
}
