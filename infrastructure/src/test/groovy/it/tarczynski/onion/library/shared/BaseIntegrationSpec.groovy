package it.tarczynski.onion.library.shared

import com.fasterxml.jackson.databind.ObjectMapper
import it.tarczynski.onion.library.generated.tables.Books
import it.tarczynski.onion.library.generated.tables.Loans
import it.tarczynski.onion.library.generated.tables.Readers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BaseIntegrationSpec extends Specification {

    @Autowired
    protected TestRestTemplate restTemplate

    @Autowired
    protected JdbcTemplate jdbcTemplate

    @Autowired
    protected ObjectMapper objectMapper

    @Autowired
    protected TimeMachineFake timeMachine

    def setup() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                Loans.LOANS.name,
                Books.BOOKS.name,
                Readers.READERS.name,
        )
        timeMachine.reset()
    }

    String extractId(ResponseEntity<Map> response) {
        assert response.body.id != null
        return response.body.id as String
    }
}
