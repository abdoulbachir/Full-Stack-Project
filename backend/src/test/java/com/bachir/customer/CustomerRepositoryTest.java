package com.bachir.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;

import com.bachir.AbstractTestcontainers;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest //Use this will reduce the number of dependencies initiated. Just loading anything that is needed for our test to run
// @SpringBootTest -> Never use this for unit testing because it will spin up the entire application.  We're focusing only in DAO testing.
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//Disable the embedded db. But now it's using our main docker db.
class CustomerRepositoryTest extends AbstractTestcontainers { //Add the extends to that it will use our testcontainers db

    @Autowired
    private CustomerRepository underTest;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount()); //Just for reference to see the number of beans.
    }

    @Test
    void existsCustomerByEmail() {
        //Given
        String email = FAKER.internet().emailAddress() + "_" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.save(customer);
        long id = underTest.findAll()
                .stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = underTest.existsCustomerByEmail(email);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerById() {
        //Given
        String email = FAKER.internet().emailAddress() + "_" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.save(customer);
        long id = underTest.findAll()
                .stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //When
        boolean actual = underTest.existsCustomerById(id);

        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailFailsWhenEmailNotPresent() {
        //Given
        String email = FAKER.internet().emailAddress() + "_" + UUID.randomUUID();

        //When
        boolean actual = underTest.existsCustomerByEmail(email);

        //Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerByEmailFailsWhenIdNotPresent() {
        //Given
        long id =87987;

        //When
        boolean actual = underTest.existsCustomerById(id);

        //Then
        assertThat(actual).isFalse();
    }
}