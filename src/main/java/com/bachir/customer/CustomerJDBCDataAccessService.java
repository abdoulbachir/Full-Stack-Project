package com.bachir.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDao{

    // A Spring class that provides a simplified way to execute JDBC queries.
    private final JdbcTemplate jdbcTemplate;

    // A Spring RowMapper implementation for the Customer class. This class is used to map the results of SQL queries to Customer objects.
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        // Define the SQL query.
        var sql = """
                SELECT id, name, email, age FROM customer
                """;

        // Execute the SQL query and return the results as a list of Customer objects.
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        // Define the SQL query.
        var sql = """
                SELECT id, name, email, age FROM customer WHERE id = ?
                """;

        // Execute the SQL query and return the results as an optional Customer object.
        return jdbcTemplate.query(sql, customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        // Define the SQL query.
        var sql= """
                INSERT INTO customer(name, email, age) VALUES ( ?, ?, ?)
                """;

        // Execute the SQL query to insert the new customer into the database.
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());

        // Print the result of the update operation.
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        // Define the SQL query.
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;

        // Execute the SQL query and return the count of customers with the given email address.
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);

        // Return true if there is at least one customer with the given email address.
        return count != null && count>0;
    }

    @Override
    public boolean existsPersonWithId(long id) {
        // Define the SQL query.
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;

        // Execute the SQL query and return the count of customers with the given ID.
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);

        // Return true if there is at
        return count != null && count>0;
    }
    @Override
    public void deleteCustomerById(long id) {
        // Define the SQL query to delete the customer with the given ID.
        var sql = """
            DELETE
            FROM customer
            WHERE id = ?
            """;

        // Execute the SQL query.
        int result = jdbcTemplate.update(sql, id);

        // Print the result of the update operation.
        System.out.println("deleteCustomerById result = " + result);
    }

    @Override
    public void updateCustomer(Customer customer) {
        // Check if the customer's name has been changed.
        if (customer.getName() != null) {
            // Define the SQL query to update the customer's name.
            String sql = """
                UPDATE customer
                SET name = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getName(), customer.getId());

            // Print the result of the update operation.
            System.out.println("update customer name result = " + result);
        }

        // Check if the customer's age has been changed.
        if (customer.getAge() != null) {
            // Define the SQL query to update the customer's age.
            String sql = """
                UPDATE customer
                SET age = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getAge(), customer.getId());

            // Print the result of the update operation.
            System.out.println("update customer age result = " + result);
        }

        // Check if the customer's email has been changed.
        if (customer.getEmail() != null) {
            // Define the SQL query to update the customer's email.
            String sql = """
                UPDATE customer
                SET email = ?
                WHERE id = ?;
                """;

            // Execute the SQL query.
            int result = jdbcTemplate.update(sql, customer.getEmail(), customer.getId());

            // Print the result of the update operation.
            System.out.println("update customer email result = " + result);
        }
    }
}
