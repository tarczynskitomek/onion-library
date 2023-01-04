package it.tarczynski.onion.library;

import java.util.HashMap;
import java.util.Map;

interface LoanRepository {
    BookLoan save(BookLoan loan);

    class InMemoryLoanRepository implements LoanRepository {

        private final Map<String, BookLoan> loans = new HashMap<>();

        @Override
        public BookLoan save(BookLoan loan) {
            this.loans.put(loan.snapshot().id(), loan);
            return loan;
        }
    }
}
