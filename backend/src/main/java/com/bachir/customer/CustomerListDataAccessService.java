package com.bachir.customer;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * An implementation of the CustomerDao interface that uses an in-memory list
 * to store customer data. This class stands as a mock database for demonstration
 * or testing purposes.
 *
 * Note: This implementation is not suitable for production use, as it does not
 * persist data and will lose all information if the application restarts.
 *
 * @author "list" signifies that this is the list-based implementation of CustomerDao.
 */
@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    // A mock database table stored in memory as a List.
    private static final List<Customer> customers;

    static {
        // Initializing the 'database'.
        customers = new ArrayList<>();
        // Adding sample records.
        customers.add(new Customer(1L, "Alex", "alex@mail.com", 22));
        customers.add(new Customer(2L, "Jamila", "jamila@mail.com", 24));
    }

    /**
     * Retrieves all customers from the mock database.
     *
     * @return a List of customers.
     */
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    /**
     * Searches for a customer based on their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return an Optional containing the found customer, if any.
     */
    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    /**
     * Inserts a new customer into the 'database'.
     *
     * @param customer The customer object to add.
     */
    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Checks if a customer exists with the provided email.
     *
     * @param email The email to search for.
     * @return true if a customer exists, false otherwise.
     */
    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(customer -> customer.getEmail().equals(email));
    }

    /**
     * Checks if a customer exists with the provided ID.
     *
     * @param id The ID to search for.
     * @return true if a customer exists, false otherwise.
     */
    @Override
    public boolean existsPersonWithId(long id) {
        return customers.stream()
                .anyMatch(customer -> customer.getId().equals(id));
    }

    /**
     * Deletes a customer based on their ID.
     *
     * @param id The ID of the customer to delete.
     */
    @Override
    public void deleteCustomerById(long id) {
        customers.removeIf(customer -> customer.getId().equals(id));
    }

    /**
     * Updates a customer's information. This method assumes the customer record
     * already exists and has a unique ID that is used to find the specific record.
     *
     * Note: In a real-world scenario, we would search for the customer by ID and
     * update their specific fields. This stub functionality will just add a new
     * record for simplicity.
     *
     * @param customerUpdate The customer object with updated information.
     */
    @Override
    public void updateCustomer(Customer customerUpdate) {
        // In a real-world scenario, we should replace the existing item rather than adding.
        int index = customers.indexOf(customerUpdate); // This depends on the equals method of Customer.
        if(index >= 0) {
            customers.set(index, customerUpdate);
        } else {
            // Record not found, for the purpose of this mock, we're just adding as new record.
            customers.add(customerUpdate);
        }
    }
}
