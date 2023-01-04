package it.tarczynski.onion.library.reader

import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

class ReadersIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private ReaderCommandsClient readerCommands

    @Autowired
    private ReaderQueryClient readerQueries

    def "should create a reader with the given age"() {
        when: 'a create command is executed'
            ResponseEntity<Map> response = readerCommands.exectue(new CreateReaderCommand(18))

        then: 'the command is accepted'
            ResponseAssertions.assertThat(response)
                    .isAccepted()

        and: 'it is possible to read created reader'
            // @formatter:off
            ResponseAssertions.assertThat(readerQueries.queryById(response.body.id as String))
                    .isOK()
                    .hasReaderThat()
                        .hasAge(18)
            // @formatter:on
    }
}
