package it.tarczynski.onion.library.test.patron

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class Api {

    private final TestRestTemplate restTemplate

    Api(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    ResponseEntity<Map> create(TestPatron patron) {
        restTemplate.postForEntity("/library/patrons", patron, Map)
    }

    ResponseEntity<Map> readPatron(String id) {
        restTemplate.getForEntity("/library/patrons/$id", Map)
    }
}
