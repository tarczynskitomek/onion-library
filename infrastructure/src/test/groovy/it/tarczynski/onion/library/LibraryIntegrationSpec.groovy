package it.tarczynski.onion.library

import it.tarczynski.onion.library.book.BookCommandClient
import it.tarczynski.onion.library.book.web.CreateBookCommand
import it.tarczynski.onion.library.reader.CreateReaderCommand
import it.tarczynski.onion.library.reader.ReaderCommandsClient
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LibraryIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private BookCommandClient bookCommands

    @Autowired
    private ReaderCommandsClient readerCommands

    @Autowired
    private LibraryCommandsClient libraryCommands

    def "should borrow a book and create a loan"() {
        given: 'a book'
            ResponseEntity<Map> bookResponse = bookCommands.execute(
                    new CreateBookCommand("The Title", new CreateBookCommand.Author(UUID.randomUUID().toString()))
            )

        and: 'an adult reader'
            ResponseEntity<Map> readerResponse = readerCommands.exectue(
                    new CreateReaderCommand(18)
            )

        when: 'the book is borrowed the reader'
            ResponseEntity<Map> borrowCommandResponse = libraryCommands.execute(
                    new BorrowBookCommand(extractId(bookResponse), extractId(readerResponse))
            )

        then: 'a loan entry is created for the book and the reader'
            // @formatter:off
            ResponseAssertions.assertThat(borrowCommandResponse)
                    .isAccepted()
                    .hasLoanThat()
                        .hasId()
                        .hasBookId(extractId(bookResponse))
                        .hasReaderId(extractId(readerResponse))
            // @formatter:on
    }

    def "should reject borrow for under aged reader"() {
        given: 'a book'
            ResponseEntity<Map> bookResponse = bookCommands.execute(
                    new CreateBookCommand("The Title", new CreateBookCommand.Author(UUID.randomUUID().toString()))
            )

        and: 'a reader below adult age'
            ResponseEntity<Map> readerResponse = readerCommands.exectue(
                    new CreateReaderCommand(17)
            )

        when: 'the book is borrowed'
            ResponseEntity<Map> borrowCommandResponse = libraryCommands.execute(
                    new BorrowBookCommand(extractId(bookResponse), extractId(readerResponse))
            )

        then: 'the loan is rejected'
            // @formatter:off
            ResponseAssertions.assertThat(borrowCommandResponse)
                    .hasStatus(UNPROCESSABLE_ENTITY)
                    .hasErrorThat()
                        .hasMessage("Reader does not meet minimum age requirements")
            // @formatter:on
    }
}
