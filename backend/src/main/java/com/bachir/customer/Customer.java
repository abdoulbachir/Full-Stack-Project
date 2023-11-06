package com.bachir.customer;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents a customer entity class in the system.
 * This class is a JPA entity with a corresponding table in the database.
 */
@Entity  // This tells JPA that this class's instances should be persisted in the database.
@Table(
        name = "customer",  // Specifies the name of the table in the database.
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_unique_email",  // Name of the unique constraint.
                        columnNames = "email"  // Column in the table that is constrained to unique values.
                )
        }
)
public class Customer {
    // These are the columns in the customer database table.
    @Id  // Marks this field as the primary key.
    @SequenceGenerator(
            name = "customer_id_seq",  // Name of the sequence generator.
            sequenceName = "customer_id_seq",  // Name of the sequence.
            allocationSize = 1  // Specifies the amount by which the sequence is incremented each time.
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,  // Specifies the strategy to use for primary key generation.
            generator = "customer_id_seq"  // The name of the sequence generator to use. This parameter is only required if the strategy parameter is set to SEQUENCE.
    )
    private Long id;  // The unique ID of the customer.

    @Column(nullable = false)  // Column configuration, stating that this field can never be null in the database.
    private String name;  // The full name of the customer.

    @Column(nullable = false)  // Column configuration, stating that this field can never be null in the database.
    private String email;  // The email address of the customer (must be unique).

    @Column(nullable = false)  // Column configuration, stating that this field can never be null in the database.
    private Integer age;  // The age of the customer.

    // Default constructor required by JPA.
    public Customer() {
    }

    // Constructor that accepts all field values. This is used to create new instances of the Customer class.
    public Customer(Long id, String name, String email, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Constructor without the ID field, which is typically auto-generated when persisting using JPA.
    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Below are the standard getters and setters used for accessing and mutating the fields of instances.
    // These methods adhere to the Java Bean naming convention, making them compatible with various libraries.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    // Overridden equals method that checks equality based on field values.
    // This is important for data consistency and correct behavior of collections containing Customer instances.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // If the same object, return true.
        if (o == null || getClass() != o.getClass()) return false;  // If null or different class, objects are not equal.
        Customer customer = (Customer) o;  // Cast the other object to Customer for comparison.
        // Check equality for all the fields.
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age);
    }

    // Overridden hashCode method, providing a unique hash for each customer based on its field values.
    // This is used in hash-based collections and is important to override whenever the equals method is overridden.
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age);  // Generates a hash based on the values of the fields.
    }
}
