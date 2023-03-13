package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import it.tarczynski.onion.library.shared.TimeFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static it.tarczynski.onion.library.TestUtil.randomUUIDString
import static it.tarczynski.onion.library.book.BookType.CIRCULATING
import static it.tarczynski.onion.library.book.CreateBookCommandBuilder.createBookCommand
import static it.tarczynski.onion.library.book.ResponseAssertions.assertThat
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

class BooksIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private BookCommandClient bookCommands

    @Autowired
    private BookQueryClient bookQueries

    def "should create a book"() {
        given: 'a command to create a book'
            CreateBookCommand command = createBookCommand()
                    .withTitle('The Title')
                    .build()

        when: 'the book is created'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'the response contains the details of the created book'
            // @formatter:off
            assertThat(response)
                    .isAccepted()
                    .containsBookThat()
                        .isCirculating()
                        .hasTitle('The Title')
                        .hasAuthor(command.author.id)
                        .hasCreationTime(TimeFixture.NOW)
            // @formatter:on

        and: 'it is possible to read it again'
            // @formatter:off
            assertThat(bookQueries.queryById(response.body.id as String))
                    .isOK()
                    .containsBookThat()
                        .isCirculating()
                        .hasTitle('The Title')
                        .hasAuthor(command.author.id)
                        .hasCreationTime(TimeFixture.NOW)
            // @formatter:on
    }

    def "should return unprocessable entity if any of the required fields is missing or malformed"() {
        given: 'a command with missing or malformed fields'
            CreateBookCommand command = createBookCommand()
                    .withType(type)
                    .withTitle(title)
                    .withAuthor(author)
                    .build()

        when: 'the command is executed against the API'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'an error is returned'
            assertThat(response)
                    .hasStatus(UNPROCESSABLE_ENTITY)

        where:
            type        | title       | author
            null        | null        | null
            CIRCULATING | 'The Title' | null
            CIRCULATING | 'The Title' | new CreateBookCommand.Author(null)
            CIRCULATING | null        | new CreateBookCommand.Author(randomUUIDString())
            CIRCULATING | ''          | new CreateBookCommand.Author(randomUUIDString())
            CIRCULATING | 'The Title' | new CreateBookCommand.Author('')
    }
}
