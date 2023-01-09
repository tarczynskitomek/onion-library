package it.tarczynski.onion.library.book


import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import it.tarczynski.onion.library.shared.TimeFixture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import java.time.temporal.ChronoUnit

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
            // @formatter:off
            ResponseAssertions.assertThat(response)
                    .isAccepted()
                    .containsBookThat()
                        .isAwaitingApproval()
                        .hasTitle('The Title')
                        .hasAuthor(command.author.id)
                        .hasCreationTime(TimeFixture.NOW)
            // @formatter:on

        and: 'it is possible to read it again'
            // @formatter:off
            ResponseAssertions.assertThat(bookQueries.queryById(response.body.id as String))
                    .isOK()
                    .containsBookThat()
                        .isAwaitingApproval()
                        .hasTitle('The Title')
                        .hasAuthor(command.author.id)
                        .hasCreationTime(TimeFixture.NOW)
            // @formatter:on
    }

    def "should approve a book"() {
        given: 'an existing book'
            String id = createNewBook()

        and: 'some time has passed'
            timeMachine.advanceBy(1, ChronoUnit.MINUTES)

        and: 'a command to approve the book'
            ApproveBookCommand command = new ApproveBookCommand(id)

        when: 'the command is executed'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'the response contains the details of the approved book'
            // @formatter:off
            ResponseAssertions.assertThat(response)
                    .isAccepted()
                    .containsBookThat()
                        .isApproved()
                        .hasCreationTime(TimeFixture.NOW)
                        .hasApprovalDate(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on

        and: 'it is possible to read the book again'
            // @formatter:off
            ResponseAssertions.assertThat(bookQueries.queryById(id))
                    .isOK()
                    .containsBookThat()
                        .isApproved()
                        .hasCreationTime(TimeFixture.NOW)
                        .hasApprovalDate(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on
    }

    def "should reject a book"() {
        given: 'a book'
            String id = createNewBook()

        and: 'some time has passed'
            timeMachine.advanceBy(1, ChronoUnit.MINUTES)

        and: 'a command to reject the book'
            RejectBookCommand command = new RejectBookCommand(id)

        when: 'the command is executed'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'the response contains the details of rejected book'
            // @formatter:off
            ResponseAssertions.assertThat(response)
                    .isAccepted()
                    .containsBookThat()
                        .isRejected()
                        .hasCreationTime(TimeFixture.NOW)
                        .hasRejectionTime(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on

        and: 'it is possible to read it again'
            // @formatter:off
            ResponseAssertions.assertThat(bookQueries.queryById(id))
                    .isOK()
                    .containsBookThat()
                        .isRejected()
                        .hasCreationTime(TimeFixture.NOW)
                        .hasRejectionTime(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on
    }

    def "should archive approved book"() {
        given: 'a book'
            String id = createNewBook()

        and: 'the book is approved'
            bookCommands.execute(new ApproveBookCommand(id))

        and: 'some time has passed'
            timeMachine.advanceBy(1, ChronoUnit.MINUTES)

        and: 'a command to archive the book'
            ArchiveBookCommand command = new ArchiveBookCommand(id)

        when: 'the command is executed'
            ResponseEntity<Map> response = bookCommands.execute(command)

        then: 'the response contains the details of rejected book'
            // @formatter:off
            ResponseAssertions.assertThat(response)
                    .isAccepted()
                    .containsBookThat()
                    .isArchived()
                    .hasCreationTime(TimeFixture.NOW)
                    .hasArchivisationTime(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on

        and: 'it is possible to read it again'
            // @formatter:off
            ResponseAssertions.assertThat(bookQueries.queryById(id))
                    .isOK()
                    .containsBookThat()
                        .isArchived()
                        .hasCreationTime(TimeFixture.NOW)
                        .hasArchivisationTime(TimeFixture.NOW.plusSeconds(60))
            // @formatter:on
    }

    private String createNewBook() {
        CreateBookCommand command = new CreateBookCommand('The Title', new CreateBookCommand.Author(UUID.randomUUID().toString()))
        ResponseEntity<Map> response = bookCommands.execute(command)
        ResponseAssertions.assertThat(response).isAccepted()
        response.body.id as String
    }

}
