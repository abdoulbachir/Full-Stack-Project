package com.bachir.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;

    @Mock //Added to mock CustomerRepository
    private CustomerRepository customerRepository;

    //Used to close the resource after each test
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this); // This returns an instance of AutoCloseable
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    //This is so after each test we have a new mock to work with
    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // When
        underTest.selectAllCustomers();

        // Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        long id=2;

        // When
        underTest.selectCustomerById(id);

        // Then
        verify(customerRepository).findById((int) id);
    }

    @Test
    void insertCustomer() {
        // Given
        Customer customer = new Customer("bachir","bachir@gmail.com",16);

        // When
        underTest.insertCustomer(customer);

        // Then
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = "email.com";

        // When
        underTest.existsPersonWithEmail(email);

        // Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        // Given
        long id=2;

        // When
        underTest.existsPersonWithId(id);

        // Then
        verify(customerRepository).existsCustomerById(id);
    }

    @Test
    void deleteCustomerById() {
        // Given
        long id=2;

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerRepository).deleteById((int) id);
    }

    @Test
    void updateCustomer() {
        // Given
        Customer customer = new Customer("bachir","bachir@gmail.com",16);

        // When
        underTest.updateCustomer(customer);

        // Then
        verify(customerRepository).save(customer);
    }
}