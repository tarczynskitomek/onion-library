package it.tarczynski.onion.library.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

@Configuration
class LocalPostgresConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(LocalPostgresConfiguration.class);

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.3")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        LOG.info("Starting local postgresql container");
        postgreSQLContainer.start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
    }
}
