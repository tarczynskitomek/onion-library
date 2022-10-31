package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.author.AuthorId
import it.tarczynski.onion.library.shared.NoOpTransactionsFake
import it.tarczynski.onion.library.shared.TimeFixture
import it.tarczynski.onion.library.shared.TimeMachineFake
import it.tarczynski.onion.library.shared.Title
import spock.lang.Specification
import spock.lang.Subject

import java.time.temporal.ChronoUnit

import static it.tarczynski.onion.library.book.Assertions.assertThat

class BooksSpec extends Specification {

    private TimeMachineFake timeMachine = new TimeMachineFake()
    private BookConfiguration bookConfiguration = new BookConfiguration(new InMemoryBookRepository(), timeMachine, new NoOpTransactionsFake())

    @Subject
    private Books books = bookConfiguration.bookFacade()

    def "should create new book in awaiting approval status"() {
        when: 'new book is created'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        then: 'it is awaiting approval'
            assertThat(book)
                    .hasCreationTime(TimeFixture.NOW)
                    .isAwaitingApproval()
    }

    def "should approve new book"() {
        given: 'new book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'some time has passed'
            timeMachine.advanceBy(61, ChronoUnit.SECONDS)

        when: 'the book is approved'
            BookSnapshot approved = books.approve(BookId.from(book.id()))

        then: 'the book is approved and approval date is set'
            assertThat(approved)
                    .isApproved()
                    .hasCreationTime(TimeFixture.NOW)
                    .hasApprovalDate(TimeFixture.NOW.plusSeconds(61))
    }

    def "should should do nothing when approving already approved book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'the book is approved'
            books.approve(BookId.from(book.id()))

        and: 'some time has passed'
            timeMachine.advanceBy(12, ChronoUnit.HOURS)

        when: 'it is approved for the second time'
            BookSnapshot approvedTwice = books.approve(BookId.from(book.id()))

        then: 'nothing happens'
            notThrown(UnsupportedOperationException)

        and: 'the original approval timestamp is preserved'
            assertThat(approvedTwice)
                    .isApproved()
                    .hasApprovalDate(TimeFixture.NOW)
    }

    def "should reject new book"() {
        given: 'new book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'some time has passed'
            timeMachine.advanceBy(1, ChronoUnit.MINUTES)

        when: 'the book is approved'
            BookSnapshot rejected = books.reject(BookId.from(book.id()))

        then: 'the book is approved and approval date is set'
            assertThat(rejected)
                    .isRejected()
                    .hasApprovalDate(null)
                    .hasRejectionDate(TimeFixture.NOW.plusSeconds(60))
    }

    def "should do nothing when rejecting already rejected book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'the book is rejected'
            books.reject(BookId.from(book.id()))

        and: 'some time has passed'
            timeMachine.advanceBy(12, ChronoUnit.HOURS)

        when: 'it is rejected for the second time'
            BookSnapshot rejectedTwice = books.reject(BookId.from(book.id()))

        then: 'nothing happens'
            notThrown(UnsupportedOperationException)

        and: 'the original rejection timestamp is preserved'
            assertThat(rejectedTwice)
                    .isRejected()
                    .hasRejectionDate(TimeFixture.NOW)
    }

    def "should not reject approved book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'which is already approved'
            BookSnapshot approved = books.approve(BookId.from(book.id()))

        when: 'trying to reject'
            books.reject(BookId.from(approved.id()))

        then: 'an exception is thrown'
            UnsupportedOperationException ex = thrown(UnsupportedOperationException)

        and: 'informing about the issue'
            ex.message == 'Unsupported state transition. Cannot reject book in state [APPROVED]'

        and: 'the book remains in approved state'
            assertThat(approved).isApproved()
    }

    def "should not approve rejected book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'the book is rejected'
            books.reject(BookId.from(book.id()))

        when: 'it is approved'
            books.approve(BookId.from(book.id()))

        then: 'an exception is thrown'
            UnsupportedOperationException ex = thrown(UnsupportedOperationException)

        and: 'it states the error reason'
            ex.message == 'Unsupported state transition. Cannot approve book in state [REJECTED]'
    }

    def "should archive approved the book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'the book is approved'
            books.approve(BookId.from(book.id()))

        and: 'some time has passed'
            timeMachine.advanceBy(3, ChronoUnit.SECONDS)

        when: 'the book is archived'
            BookSnapshot archived = books.archive(BookId.from(book.id()))

        then: 'its status is changed and archivisation date is set'
            assertThat(archived)
                    .isArchived()
                    .hasApprovalDate(TimeFixture.NOW)
                    .hasArchivisationDate(TimeFixture.NOW.plus(3, ChronoUnit.SECONDS))
    }

    def "should archive rejected book"() {
        given: 'a book'
            BookSnapshot book = books.create(AuthorId.next(), new Title('The Title'))

        and: 'the book is rejected'
            books.reject(BookId.from(book.id()))

        and: 'some time has passed'
            timeMachine.advanceBy(12, ChronoUnit.DAYS)

        when: 'the book is archived'
            BookSnapshot archived = books.archive(BookId.from(book.id()))

        then: 'its status is changed and archivisation date is set'
            assertThat(archived)
                    .isArchived()
                    .hasRejectionDate(TimeFixture.NOW)
                    .hasArchivisationDate(TimeFixture.NOW.plus(12, ChronoUnit.DAYS))
    }
}
