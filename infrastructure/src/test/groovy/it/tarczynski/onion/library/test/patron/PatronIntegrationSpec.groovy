package it.tarczynski.onion.library.test.patron


import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static it.tarczynski.onion.library.test.patron.PatronResponseAssert.assertThat
import static it.tarczynski.onion.library.test.patron.TestPatron.aPatron

class PatronIntegrationSpec extends BaseIntegrationSpec {

    def 'should create a new regular patron'() {
        given: 'a new regular patron'
        TestPatron patron = aPatron()
                .withType('REGULAR')
                .withName('Joe')
                .withSurname('Doe')

        when: 'the patron is created'
        ResponseEntity<Map> response = api.create(patron)

        then: 'the patron is created successfully'
        // @formatter:off
        assertThat(response)
                .isNotNull()
                .hasStatus(HttpStatus.CREATED)

        and: 'it is possible to read the patron'
        assertThat(api.readPatron(response.body.id as String))
                .isNotNull()
                .hasStatus(HttpStatus.OK)
                .hasPatronThat()
                    .hasId()
                    .hasType('REGULAR')
                    .hasName('Joe')
                    .hasSurname('Doe')
        // formatter:on
    }
}
