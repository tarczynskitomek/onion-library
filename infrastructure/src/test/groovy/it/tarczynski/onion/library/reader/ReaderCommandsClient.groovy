package it.tarczynski.onion.library.reader

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class ReaderCommandsClient extends BaseTestClient {

    ReaderCommandsClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> exectue(CreateReaderCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/readers/commands/create/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }
}
