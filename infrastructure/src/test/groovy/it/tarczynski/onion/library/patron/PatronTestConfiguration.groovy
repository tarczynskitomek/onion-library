package it.tarczynski.onion.library.patron

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class PatronTestConfiguration {

    @Bean
    PatronCommandsClient patronCommands(TestRestTemplate restTemplate) {
        new PatronCommandsClient(restTemplate)
    }

    @Bean
    PatronQueryClient patronQueries(TestRestTemplate restTemplate) {
        new PatronQueryClient(restTemplate)
    }
}
