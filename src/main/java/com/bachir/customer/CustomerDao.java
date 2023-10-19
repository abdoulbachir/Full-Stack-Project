package com.bachir.customer;

import java.util.List;
import java.util.Optional;

// Defines the DAO (Data Access Object) interface for performing operations related to 'Customer' entities.
// Implementations of this interface would typically interact with a database to store and retrieve customer information.
public interface CustomerDao {

    /**
     * Retrieves all customers from the data store.
     * @return a list of all customer records as 'Customer' objects.
     */
    List<Customer> selectAllCustomers();

    /**
     * Retrieves a customer by their unique identifier (ID).
     * @param id the unique identifier of the customer to retrieve.
     * @return an 'Optional' of 'Customer', which may contain a 'Customer' object if a customer with the given ID exists,
     * or be empty if no such customer exists in the data store.
     */
    Optional<Customer> selectCustomerById(Long id);

    /**
     * Inserts a new customer record into the data store.
     * @param customer the 'Customer' object representing the customer record to store.
     */
    void insertCustomer(Customer customer);

    /**
     * Checks whether a customer with the given email exists in the data store.
     * @param email the email address to check against existing customer records.
     * @return 'true' if a customer with the given email exists, 'false' otherwise.
     */
    boolean existsPersonWithEmail(String email);

    /**
     * Checks whether a customer with the given ID exists in the data store.
     * @param id the unique identifier to check against existing customer records.
     * @return 'true' if a customer with the given ID exists, 'false' otherwise.
     */
    boolean existsPersonWithId(long id);

    /**
     * Deletes a customer record from the data store using their unique identifier (ID).
     * @param id the unique identifier of the customer record to delete.
     */
    void deleteCustomerById(long id);

    /**
     * Updates an existing customer's information in the data store.
     * @param customer the 'Customer' object containing the updated information.
     */
    void updateCustomer(Customer customer);
}
