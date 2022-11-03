package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.author.AuthorId
import it.tarczynski.onion.library.shared.ApprovedAt
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import it.tarczynski.onion.library.shared.RejectedAt
import it.tarczynski.onion.library.shared.Title
import it.tarczynski.onion.library.shared.exception.OptimisticLockingException
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

import java.time.Instant

class PostgresBookRepositoryConcurrencySpec extends BaseIntegrationSpec {

    @Autowired
    private Books books

    @Subject
    @Autowired
    private PostgresBookRepository bookRepository

    def "should fail with optimistic locking exception if the same record is modified twice after being read"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'it is modified two times'
            Book first = bookRepository.getById(BookId.from(book.id())).approve(ApprovedAt.from(Instant.now()))
            Book second = bookRepository.getById(BookId.from(book.id())).reject(RejectedAt.from(Instant.now()))

        when: 'both modifications are saved'
            bookRepository.update(first)
            bookRepository.update(second)

        then: 'the second update fails with optimistic locking excpetion'
            OptimisticLockingException ex = thrown(OptimisticLockingException)

        and:
            ex.message == "Book [${book.id()}] has been update concurrently"
    }
}
