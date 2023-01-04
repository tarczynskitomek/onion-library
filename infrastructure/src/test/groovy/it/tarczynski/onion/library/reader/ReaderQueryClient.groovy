package it.tarczynski.onion.library.reader

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class ReaderQueryClient extends BaseTestClient {

    ReaderQueryClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> queryById(String id) {
        restTemplate.exchange(
                RequestEntity.get("/readers/$id")
                        .accept(MediaType.APPLICATION_JSON)
                        .build(),
                Map
        )
    }
}
