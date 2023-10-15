package com.bachir.customer;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

// Tells Spring to manage this bean.
@Component
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
