package it.tarczynski.onion.library

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class LibraryCommandsClient extends BaseTestClient {

    LibraryCommandsClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> execute(BorrowBookCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/library/loans/commands/create/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }
}
