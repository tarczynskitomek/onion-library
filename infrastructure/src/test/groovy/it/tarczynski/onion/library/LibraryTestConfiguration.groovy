package it.tarczynski.onion.library

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class LibraryTestConfiguration {

    @Bean
    LibraryCommandsClient libraryCommands(TestRestTemplate restTemplate) {
        new LibraryCommandsClient(restTemplate)
    }
}
