package it.tarczynski.onion.library

import it.tarczynski.onion.library.book.BookCommandClient
import it.tarczynski.onion.library.patron.CreateRegularPatronCommand
import it.tarczynski.onion.library.patron.PatronCommandsClient
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static it.tarczynski.onion.library.book.CreateBookCommandBuilder.createBookCommand

class LibraryIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private BookCommandClient bookCommands

    @Autowired
    private PatronCommandsClient patronCommands

    @Autowired
    private LibraryCommandsClient libraryCommands

    def "should put a book on hold for a regular patron"() {
        given: 'a book'
            ResponseEntity<Map> bookResponse = bookCommands.execute(createBookCommand().build())

        and: 'an a patron'
            ResponseEntity<Map> patronResponse = patronCommands.execute(new CreateRegularPatronCommand('Joe Doe'))

        when: 'the book is put on hold for the patron'
            ResponseEntity<Map> holdCommandResponse = libraryCommands.execute(
                    new HoldBookCommand(extractId(bookResponse), extractId(patronResponse))
            )

        then: 'a hold entry is created for the book and the patron'
            // @formatter:off
            ResponseAssertions.assertThat(holdCommandResponse)
                    .isAccepted()
                    .hasHoldThat()
                        .hasId()
                        .hasBookId(extractId(bookResponse))
                        .hasPatronId(extractId(patronResponse))
            // @formatter:on
    }

}
