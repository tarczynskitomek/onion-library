package it.tarczynski.onion.library.reader

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class ReaderTestConfiguration {

    @Bean
    ReaderCommandsClient readerCommands(TestRestTemplate restTemplate) {
        new ReaderCommandsClient(restTemplate)
    }

    @Bean
    ReaderQueryClient readerQueries(TestRestTemplate restTemplate) {
        new ReaderQueryClient(restTemplate)
    }
}
