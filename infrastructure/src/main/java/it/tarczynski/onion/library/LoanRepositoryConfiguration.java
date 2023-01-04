package it.tarczynski.onion.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class LoanRepositoryConfiguration {

    @Bean
    LoanRepository loanRepository() {
        return new LoanRepository.InMemoryLoanRepository();
    }
}
