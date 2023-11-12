package com.bachir;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
//Used to run integration tests against Docker containers. Can be used with any integration testing framework, such as JUnit, TestNG, or Cucumber.
// @SpringBootTest -> Never use this for unit testing because it will spin up the entire application.  We're focusing only in DAO testing.
public abstract class AbstractTestcontainers {

    //Before all test methods in a class, apply migration with flyway
    @BeforeAll
    static void beforeAll() {
        // Create a new Flyway object and configure it to use the PostgreSQL database container.
        Flyway flyway = Flyway.configure().dataSource(
                postgreSQLContainer.getJdbcUrl(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getPassword()
        ).load();
        // Execute all pending database migrations
        flyway.migrate();
    }

    //Initialize the Postgres container
    @Container //Used to mark a field as a container
    protected static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("bachir-dao-unit-test")
            .withUsername("main/bachir")
            .withPassword("password");

    //Map our spring data source to our test container = connection our application to the testcontainer we have running
    @DynamicPropertySource
    private static void registerDataSourceProperty(DynamicPropertyRegistry registry){
        registry.add(
                "spring.datasource.url",
                postgreSQLContainer::getJdbcUrl //Same as   () -> postgreSQLContainer.getJdbcUrl()
        );

        registry.add(
                "spring.datasource.username",
                postgreSQLContainer::getUsername // Same as   () -> postgreSQLContainer.getUsername()
        );

        registry.add(
                "spring.datasource.password",
                postgreSQLContainer::getPassword // Same as   () -> postgreSQLContainer.getPassword()
        );
    }
    //This is a field to build us the data source
    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

    // Used to get JdbcTemplate that can be used by Test classes
    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    //To be able to generate fake data in Test classes
    protected static Faker FAKER = new Faker();
}
