package it.tarczynski.onion.library.test.patron

import groovy.transform.CompileStatic
import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy
import it.tarczynski.onion.library.shared.BaseIntegrationSpec
import org.assertj.core.api.WithAssertions
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

@CompileStatic
@Builder(builderStrategy = SimpleStrategy, prefix = 'with')
class TestPatron {

    String type = 'REGULAR'
    String name = 'Joe'
    String surname = 'Doe'
    
    static TestPatron aPatron() {
        new TestPatron()
    }
}

class PatronResponseAssert implements WithAssertions {
    
    private final ResponseEntity<Map> response

    static PatronResponseAssert assertThat(ResponseEntity<Map> response) {
        new PatronResponseAssert(response)
    }
    
    private PatronResponseAssert(ResponseEntity<Map> response) {
        this.response = response
    }
    
    PatronResponseAssert isNotNull() {
        assert response != null
        this
    }
    
    PatronResponseAssert hasStatus(HttpStatus status) {
        assert response.statusCode == status
        this
    }

    PatronResponseAssert hasId() {
        assert response.body.id != null
        this
    }

    PatronResponseAssert hasType(String type) {
        assert response.body.type == type
        this
    }

    PatronResponseAssert hasName(String name) {
        assert response.body.name == name
        this
    }

    PatronResponseAssert hasSurname(String surname) {
        assert response.body.surname == surname
        this
    }
    
    PatronResponseAssert hasPatronThat() {
        this
    }
}