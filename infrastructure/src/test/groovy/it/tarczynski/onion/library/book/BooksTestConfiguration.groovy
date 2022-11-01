package it.tarczynski.onion.library.book


import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class BooksTestConfiguration {

    @Bean
    BookCommandClient bookCommands(TestRestTemplate restTemplate) {
        new BookCommandClient(restTemplate)
    }

    @Bean
    BookQueryClient bookQuery(TestRestTemplate restTemplate) {
        new BookQueryClient(restTemplate)
    }
}
