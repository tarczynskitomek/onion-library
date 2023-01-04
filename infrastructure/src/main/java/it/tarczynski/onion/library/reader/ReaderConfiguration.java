package it.tarczynski.onion.library.reader;

import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
class ReaderConfiguration {

    private final ReaderRepository readerRepository;
    private final Transactions transactions;

    @Bean
    Readers readers() {
        return new Readers(readerRepository, transactions);
    }

    @Bean
    ReaderQueryRepository readerQueryRepository() {
        return new ReaderQueryRepository.InMemoryReaderQueryRepository(readerRepository);
    }
}
