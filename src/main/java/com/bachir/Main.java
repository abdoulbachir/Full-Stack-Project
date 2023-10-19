package com.bachir;

import com.bachir.customer.Customer;
import com.bachir.customer.CustomerRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Random;

/**
 * Main class that serves as the entry point of the Spring Boot application.
 * This class includes the setup required to initialize and run the application,
 * including the creation of some sample customer data to work with.
 */
@SpringBootApplication // The @SpringBootApplication annotation indicates that this class serves as the starting point for the Spring Boot Application.
public class Main {

    /**
     * Main method which serves as the entry point of the application.
     *
     * @param args command line arguments passed to the application. Not used in this application.
     */
    public static void main(String[] args) {
        /*
          The SpringApplication.run method launches the application.
          It sets up the default configuration, starts the Spring application context,
          and performs class path scanning, among other things.
          */
        SpringApplication.run(Main.class, args);
    }

    /**
     * The @Bean annotation makes the method return an object that should be registered as
     * a bean in the Spring application context. Here, a CommandLineRunner is used,
     * which indicates that the code within the method will run after all the beans are created and registered.
     *
     * @param customerRepository The repository object allowing interaction with the database.
     * @return CommandLineRunner This is expected to be a functional interface, with a single run method
     * that gets invoked with command line arguments as its parameters.
     */
    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> { // Utilizing a Java 8 lambda to keep the code more readable and concise.
            // Creating an instance of Faker, a library used to generate realistic and random data.
            Faker faker = new Faker();
            // Creating an instance of Random specifically for generating random integers.
            Random random = new Random();

            // Using the Faker instance to randomly generate a first name and last name.
            Name name = faker.name(); // 'name' is a subtype with methods specific to name data.
            String firstName = name.firstName(); // Retrieving a randomly generated first name.
            String lastName = name.lastName(); // Retrieving a randomly generated last name.

            // Creating a new Customer object with the generated names, forming a full name,
            // constructing an email, and generating a random age between 16 and 99.
            Customer customer = new Customer(
                    firstName+" "+lastName, // Concatenating first name and last name to form a full name.
                    firstName.toLowerCase()+"."+lastName.toLowerCase()+"@amigos.com", // Generating a likely email format.
                    random.nextInt(16, 99) // Randomly generating an age between 16 and 99.
            );

            // The 'save' method comes from the CustomerRepository (Spring Data JPA repository interface).
            // It persists the Customer object to the database, handling all the database interaction and transactions.
            customerRepository.save(customer);
        };
    }
}
