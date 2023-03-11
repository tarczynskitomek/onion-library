package it.tarczynski.onion.library.patron

import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

class PatronsIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    private PatronCommandsClient patronCommands

    @Autowired
    private PatronQueryClient patronQueries

    def "should create a regular patron"() {
        when: 'a create command is executed'
            ResponseEntity<Map> response = patronCommands.execute(new CreateRegularPatronCommand('Joe Doe'))

        then: 'the command is accepted'
            ResponseAssertions.assertThat(response)
                    .isAccepted()

        and: 'it is possible to read created patron'
            // @formatter:off
            ResponseAssertions.assertThat(patronQueries.queryById(response.body.id as String))
                    .isOK()
                    .hasPatronThat()
                        .hasName('Joe Doe')
                        .isRegular()
            // @formatter:on
    }
}
