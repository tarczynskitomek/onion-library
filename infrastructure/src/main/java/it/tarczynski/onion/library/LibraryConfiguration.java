package it.tarczynski.onion.library;

import it.tarczynski.onion.library.reader.ReaderQueryRepository;
import it.tarczynski.onion.library.shared.Transactions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
class LibraryConfiguration {

    private final LoanRepository loanRepository;
    private final Transactions transactions;
    private final ReaderQueryRepository readerQueryRepository;

    @Bean
    Library library() {
        return new Library(loanRepository, transactions, new AgeBasedBookBorrowingPolicy(readerQueryRepository));
    }
}
