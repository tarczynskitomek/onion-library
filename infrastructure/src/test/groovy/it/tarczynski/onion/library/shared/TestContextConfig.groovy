package it.tarczynski.onion.library.shared

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class TestContextConfig {

    @Bean
    @Primary
    TimeMachine timeMachine() {
        new TimeMachineFake()
    }
}
