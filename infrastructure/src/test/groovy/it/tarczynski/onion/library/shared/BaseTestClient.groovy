package it.tarczynski.onion.library.shared

import org.springframework.boot.test.web.client.TestRestTemplate

class BaseTestClient {

    protected final TestRestTemplate restTemplate

    BaseTestClient(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }
}
