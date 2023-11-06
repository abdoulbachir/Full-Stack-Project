package com.bachir.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * JDBC implementation of the CustomerDao repository. This class performs CRUD operations
 * for the 'Customer' entities in the database using JdbcTemplate.
 *
 * Annotated with @Repository, marking it as a Spring component for DAO-style data access operations.
 */
@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao {

    // JdbcTemplate is a central class in Spring's JDBC support and is used to execute SQL queries.
    private final JdbcTemplate jdbcTemplate;

    // An instance of a class that implements the RowMapper interface,
    // responsible for mapping rows of a ResultSet on a per-row basis.
    private final CustomerRowMapper customerRowMapper;

    /**
     * Constructs a new CustomerJDBCDataAccessService with the specified JdbcTemplate and CustomerRowMapper.
     *
     * @param jdbcTemplate The JdbcTemplate used for querying the database.
     * @param customerRowMapper The CustomerRowMapper used for mapping rows of a ResultSet to Customer objects.
     */
    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of customers, represented as Customer objects.
     */
    @Override
    public List<Customer> selectAllCustomers() {
        // SQL query to fetch all customers.
        final var sql = """
                SELECT id, name, email, age FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper); // Query the database and get the results mapped as Customer objects.
    }

    /**
     * Retrieves a customer by their ID from the database.
     *
     * @param id The unique identifier of the customer.
     * @return An Optional containing the found customer, if any.
     */
    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        // SQL query to fetch a customer by ID.
        final var sql = """
                SELECT id, name, email, age FROM customer WHERE id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst(); // Execute the query and return the result, if any, as an Optional.
    }

    /**
     * Inserts a new customer into the database.
     *
     * @param customer The Customer object containing information about the customer to be inserted.
     */
    @Override
    public void insertCustomer(Customer customer) {
        // SQL query to insert a new customer.
        final var sql = """
                INSERT INTO customer(name, email, age) VALUES ( ?, ?, ?)
                """;
        // Inserting a new customer record in the database and logging the operation's result.
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println("jdbcTemplate.update = " + result); // Logging the number of rows affected by the update.
    }

    /**
     * Checks if a customer with the specified email exists in the database.
     *
     * @param email The email to search for.
     * @return true if a customer with the given email exists, false otherwise.
     */
    @Override
    public boolean existsPersonWithEmail(String email) {
        // SQL query to count customers with the specified email.
        final var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;
        // Querying the database for the number of customers with the given email.
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0; // Return true if at least one match was found.
    }

    /**
     * Checks if a customer with the specified ID exists in the database.
     *
     * @param id The ID to search for.
     * @return true if a customer with the given ID exists, false otherwise.
     */
    @Override
    public boolean existsPersonWithId(long id) {
        // SQL query to count customers with the specified ID.
        final var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        // Querying the database for the number of customers with the given ID.
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0; // Return true if at least one match was found.
    }

    /**
     * Deletes a customer from the database using their ID.
     *
     * @param id The unique identifier of the customer to be deleted.
     */
    @Override
    public void deleteCustomerById(long id) {
        // Define the SQL query to delete the customer with the given ID.
        var sql = """
            DELETE
            FROM customer
            WHERE id = ?
            """;

        // Execute the update and log the result, indicating how many rows in the database were affected.
        int result = jdbcTemplate.update(sql, id);
        System.out.println("deleteCustomerById result = " + result); // Logging the number of rows affected by the update.
    }

    /**
     * Updates the information of an existing customer in the database.
     * The method checks for non-null values before attempting an update to the respective fields.
     *
     * @param customer The Customer object containing the updated data.
     */
    @Override
    public void updateCustomer(Customer customer) {
        // Update the customer's name if it has been changed.
        if (customer.getName() != null) {
            // SQL query to update the customer's name based on their ID.
            String sql = """
                UPDATE customer
                SET name = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getName(), customer.getId());
            System.out.println("update customer name result = " + result);
        }

        // Update the customer's age if it has been changed.
        if (customer.getAge() != null) {
            // SQL query to update the customer's age based on their ID.
            String sql = """
                UPDATE customer
                SET age = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getAge(), customer.getId());
            System.out.println("update customer age result = " + result);
        }

        // Update the customer's email if it has been changed.
        if (customer.getEmail() != null) {
            // SQL query to update the customer's email based on their ID.
            String sql = """
                UPDATE customer
                SET email = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getEmail(), customer.getId());
            System.out.println("update customer email result = " + result);
        }
    }
}
