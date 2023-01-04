package it.tarczynski.onion.library.reader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
class ReaderRepositoryConfiguration {

    @Bean
    ReaderRepository readerRepository() {
        return new ReaderRepository.InMemoryReaderRepository();
    }
}
