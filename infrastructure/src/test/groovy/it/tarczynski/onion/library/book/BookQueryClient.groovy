package it.tarczynski.onion.library.book

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

class BookQueryClient extends BaseTestClient {

    BookQueryClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> queryById(String id) {
        restTemplate.getForEntity("/books/$id", Map)
    }
}
