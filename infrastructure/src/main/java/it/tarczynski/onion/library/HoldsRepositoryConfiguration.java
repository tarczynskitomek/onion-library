package it.tarczynski.onion.library;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class HoldsRepositoryConfiguration {

    @Bean
    HoldRepository holdRepository() {
        return new HoldRepository.InMemoryHoldRepository();
    }
}
