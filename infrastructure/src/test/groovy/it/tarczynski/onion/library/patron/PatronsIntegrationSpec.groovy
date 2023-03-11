package it.tarczynski.onion.library.patron

import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

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

    def "should return an error if regular patron name is missing"() {
        when: 'a command has an invalid patron name'
            ResponseEntity<Map> response = patronCommands.execute(new CreateRegularPatronCommand(name))

        then: 'an error is returned'
            ResponseAssertions.assertThat(response)
                    .hasStatus(UNPROCESSABLE_ENTITY)

        where:
            name << ['', null, "\t", "  ", "\n"]
    }

    def "should create a researcher patron"() {
        when: 'a create researcher patron command is executed'
            ResponseEntity<Map> response = patronCommands.execute(new CreateResearcherPatronCommand('Joe Doe', 'UMCS Lublin'))

        then:
            ResponseAssertions.assertThat(response)
                    .isAccepted()

        and:
            // @formatter:off
            ResponseAssertions.assertThat(patronQueries.queryById(response.body.id as String))
                    .isOK()
                    .hasPatronThat()
                        .hasName('Joe Doe')
                        .hasAffiliation('UMCS Lublin')
                        .isResearcher()
            // @formatter:on
    }

    def "should return an error if any of the researcher patron fields is missing"() {
        when:
            ResponseEntity<Map> response = patronCommands.execute(new CreateResearcherPatronCommand(name, affiliation))

        then:
            ResponseAssertions.assertThat(response)
                    .hasStatus(UNPROCESSABLE_ENTITY)

        where:
            name  | affiliation
            null  | null
            ''    | ''
            'Joe' | null
            null  | 'UMCS Lublin'
    }

}
