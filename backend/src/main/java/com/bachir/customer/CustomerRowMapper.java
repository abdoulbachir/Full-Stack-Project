package com.bachir.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The CustomerRowMapper class implements the RowMapper interface, providing
 * a component that maps rows from a ResultSet to instances of the Customer class.
 * This class is typically used in JDBC template queries to convert SQL query results
 * into objects that can be used within the application.
 *
 * Annotated with @Component, this class is automatically detected by Spring
 * during component scanning. This allows Spring to handle the lifecycle of
 * instances of this class and inject them where needed.
 *
 * Methods:
 *
 * 1. mapRow(ResultSet rs, int rowNum):
 *    - This method is an override from the RowMapper interface.
 *    - It is called for each row in the ResultSet of a SQL query.
 *    - It creates and returns a Customer object by extracting the data
 *      from the ResultSet.
 *    - The rowNum argument indicates the number of the current row.
 *      It can be used for more complex mappings, if needed.
 *
 * Usage:
 *
 * The CustomerRowMapper is typically used in the query methods of a
 * JdbcTemplate. For example, when calling a queryForObject or query method
 * from JdbcTemplate, an instance of CustomerRowMapper can be passed as
 * an argument to map each row of the ResultSet to a Customer object.
 *
 * Example:
 *   jdbcTemplate.query("SELECT * FROM customer", new CustomerRowMapper());
 *
 * The mapRow method doesn't need to be called directly by the developer.
 * It is called internally by the JdbcTemplate for each row of the ResultSet.
 *
 * Handling SQLException:
 * SQLException can occur while interacting with the database. This is a
 * checked exception, so the method explicitly declares that it might be thrown.
 * The calling code should handle this exception, typically by logging it or
 * by translating it into a DataAccessException (in a Spring application).
 */
@Component // Tells Spring to manage this bean.
public class CustomerRowMapper implements RowMapper<Customer> {

    // This method is called for each row in the results of a SQL query.
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Return a new Customer object.
        return new Customer(
                // Get the value of the `id` column from the ResultSet.
                rs.getLong("id"),

                // Get the value of the `name` column from the ResultSet.
                rs.getString("name"),

                // Get the value of the `email` column from the ResultSet.
                rs.getString("email"),

                // Get the value of the `age` column from the ResultSet.
                rs.getInt("age")
        );
    }
}
