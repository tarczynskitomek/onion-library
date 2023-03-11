package it.tarczynski.onion.library.patron

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class PatronQueryClient extends BaseTestClient {

    PatronQueryClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> queryById(String id) {
        restTemplate.exchange(
                RequestEntity.get("/patrons/$id")
                        .accept(MediaType.APPLICATION_JSON)
                        .build(),
                Map
        )
    }
}
