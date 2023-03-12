package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.shared.NoOpTransactionsFake
import it.tarczynski.onion.library.shared.TimeFixture
import it.tarczynski.onion.library.shared.TimeMachineFake
import spock.lang.Specification
import spock.lang.Subject

import static it.tarczynski.onion.library.book.Assertions.assertThat
import static it.tarczynski.onion.library.book.BookType.CIRCULATING
import static it.tarczynski.onion.library.book.BookType.RESTRICTED
import static it.tarczynski.onion.library.book.CreateBookCommandBuilder.createBookCommand

class BooksSpec extends Specification {

    private TimeMachineFake timeMachine = new TimeMachineFake()
    private BookConfiguration bookConfiguration = new BookConfiguration(new InMemoryBookRepository(), timeMachine, new NoOpTransactionsFake())

    @Subject
    private Books books = bookConfiguration.bookFacade()

    def "should create new circulating book"() {
        given: 'a command'
            CreateBookCommand createBookCommand = createBookCommand()
                    .withType(CIRCULATING)
                    .withTitle('The Lord of The Rings')
                    .build()

        when: 'new book is created'
            BookSnapshot book = books.handle(createBookCommand)

        then:
            assertThat(book)
                    .hasCreationTime(TimeFixture.NOW)
                    .hasAuthor(createBookCommand.getAuthor().id)
                    .hasTitle('The Lord of The Rings')
                    .isCirculating()
    }

    def "should create new restricted book"() {
        given:
            CreateBookCommand createBookCommand = createBookCommand()
                    .withType(RESTRICTED)
                    .withTitle('The Lord of The Rings')
                    .build()

        when:
            BookSnapshot book = books.handle(createBookCommand)

        then:
            assertThat(book)
                    .hasCreationTime(TimeFixture.NOW)
                    .hasAuthor(createBookCommand.getAuthor().id)
                    .hasTitle('The Lord of The Rings')
                    .isRestricted()
    }
}
