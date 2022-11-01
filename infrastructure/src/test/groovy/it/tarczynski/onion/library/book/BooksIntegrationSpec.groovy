package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.book.web.CreateBookCommand
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import it.tarczynski.onion.library.shared.TimeFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

class BooksIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private BookCommandClient bookCommands

    @Autowired
    private BookQueryClient bookQueries

    def "should create a book"() {
        given: 'a command to create a book'
            CreateBookCommand command = new CreateBookCommand('The Title', new CreateBookCommand.Author(UUID.randomUUID().toString()))

        when: 'the book is created'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'the response contains the details of the created book'
            ResponseAssertions.assertThat(response)
                    .isAccepted()
                    .isAwaitingApproval()
                    .hasTitle('The Title')
                    .hasAuthor(command.author.id)
                    .hasCreationTime(TimeFixture.NOW)

        and: 'it is possible to read it again'
            ResponseAssertions.assertThat(bookQueries.queryById(response.body.id as String))
                    .isOK()
                    .isAwaitingApproval()
                    .hasTitle('The Title')
                    .hasAuthor(command.author.id)
                    .hasCreationTime(TimeFixture.NOW)
    }

}
