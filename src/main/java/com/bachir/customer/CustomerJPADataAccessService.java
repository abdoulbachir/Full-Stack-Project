package com.bachir.customer;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * A CustomerDao implementation using JPA for data access.
 *
 * It provides the mechanism for storage, retrieval, and search behavior
 * which emulates a collection of objects.
 *
 * @author "jpa" indicates that the JPA (Java Persistence API) is used for data access.
 */
@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {

    // The repository responsible for handling customer data.
    private final CustomerRepository customerRepository;

    /**
     * Constructor used for injecting the repository.
     *
     * @param customerRepository The CustomerRepository used for customer data access.
     */
    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieve all customers from the database.
     *
     * @return List of Customer objects.
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll(); // JPA repository method for fetching all records.
    }

    /**
     * Find a customer by ID.
     *
     * @param id A unique identifier for the customer.
     * @return An Optional object which may contain a Customer if they exist.
     */
    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customerRepository.findById(Math.toIntExact(id)); // JPA repository method for finding an entity by its ID.
    }

    /**
     * Insert a new customer into the database.
     *
     * @param customer The Customer object to be saved.
     */
    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer); // JPA repository method for saving an entity.
    }

    /**
     * Check if a customer exists with an exact email.
     *
     * @param email The email to check for.
     * @return A boolean indicating if a customer exists with the provided email.
     */
    @Override
    public boolean existsPersonWithEmail(String email) {
        return customerRepository.existsCustomerByEmail(email); // Custom repository method to check existence by email.
    }

    /**
     * Check if a customer exists with a specific ID.
     *
     * @param id The ID to check for.
     * @return A boolean indicating if a customer exists with the provided ID.
     */
    @Override
    public boolean existsPersonWithId(long id) {
        return customerRepository.existsCustomerById(id); // Custom repository method to check existence by ID.
    }

    /**
     * Delete a customer from the database using their ID.
     *
     * @param id The unique identifier for the customer to be deleted.
     */
    @Override
    public void deleteCustomerById(long id) {
        customerRepository.deleteById((int) id); // JPA repository method for deleting an entity by its ID.
    }

    /**
     * Update the information of an existing customer in the database.
     *
     * @param customerUpdate The Customer object containing updated data.
     */
    @Override
    public void updateCustomer(Customer customerUpdate) {
        // JPA repository method for saving an entity, works for both insert and update.
        // In this case, it performs an update if the row exists, based on the entity's ID.
        customerRepository.save(customerUpdate);
    }
}
