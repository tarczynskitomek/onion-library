package it.tarczynski.onion.library.patron

import it.tarczynski.onion.library.shared.BaseTestClient
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

class PatronCommandsClient extends BaseTestClient {

    PatronCommandsClient(TestRestTemplate restTemplate) {
        super(restTemplate)
    }

    ResponseEntity<Map> execute(CreateRegularPatronCommand command) {
        UUID commandId = generateId()
        restTemplate.exchange(
                RequestEntity.put("/patrons/commands/create-regular/$commandId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(command),
                Map
        )
    }
}
