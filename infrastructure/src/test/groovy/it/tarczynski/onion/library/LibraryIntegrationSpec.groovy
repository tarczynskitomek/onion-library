package it.tarczynski.onion.library

import it.tarczynski.onion.library.book.BookCommandClient
import it.tarczynski.onion.library.book.CreateBookCommand
import it.tarczynski.onion.library.patron.CreatePatronCommand
import it.tarczynski.onion.library.patron.PatronCommandsClient
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class LibraryIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private BookCommandClient bookCommands

    @Autowired
    private PatronCommandsClient patronCommands

    @Autowired
    private LibraryCommandsClient libraryCommands

    def "should borrow a book and create a loan"() {
        given: 'a book'
            ResponseEntity<Map> bookResponse = bookCommands.execute(
                    new CreateBookCommand("The Title", new CreateBookCommand.Author(UUID.randomUUID().toString()))
            )

        and: 'an adult patron'
            ResponseEntity<Map> patronResponse = patronCommands.execute(
                    new CreatePatronCommand(18)
            )

        when: 'the book is borrowed the patron'
            ResponseEntity<Map> borrowCommandResponse = libraryCommands.execute(
                    new BorrowBookCommand(extractId(bookResponse), extractId(patronResponse))
            )

        then: 'a loan entry is created for the book and the patron'
            // @formatter:off
            ResponseAssertions.assertThat(borrowCommandResponse)
                    .isAccepted()
                    .hasLoanThat()
                        .hasId()
                        .hasBookId(extractId(bookResponse))
                        .hasPatronId(extractId(patronResponse))
            // @formatter:on
    }

    def "should reject borrow for under aged patron"() {
        given: 'a book'
            ResponseEntity<Map> bookResponse = bookCommands.execute(
                    new CreateBookCommand("The Title", new CreateBookCommand.Author(UUID.randomUUID().toString()))
            )

        and: 'a patron below adult age'
            ResponseEntity<Map> patronResponse = patronCommands.execute(
                    new CreatePatronCommand(17)
            )

        when: 'the book is borrowed'
            ResponseEntity<Map> borrowCommandResponse = libraryCommands.execute(
                    new BorrowBookCommand(extractId(bookResponse), extractId(patronResponse))
            )

        then: 'the loan is rejected'
            // @formatter:off
            ResponseAssertions.assertThat(borrowCommandResponse)
                    .hasStatus(UNPROCESSABLE_ENTITY)
                    .hasErrorThat()
                        .hasMessage("Patron does not meet minimum age requirements")
            // @formatter:on
    }
}
