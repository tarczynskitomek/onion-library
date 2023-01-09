package it.tarczynski.onion.library;

import java.util.HashMap;
import java.util.Map;

interface LoanRepository {
    BookLoan create(BookLoan loan);

    class InMemoryLoanRepository implements LoanRepository {

        private final Map<String, BookLoan> loans = new HashMap<>();

        @Override
        public BookLoan create(BookLoan loan) {
            this.loans.put(loan.snapshot().id(), loan);
            return loan;
        }
    }
}
