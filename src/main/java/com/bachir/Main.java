package com.bachir;

import com.bachir.customer.Customer;
import com.bachir.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;;import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){

        return args -> {
            Customer alex = new Customer(1,"Alex","alex@mail.com",22);
            Customer jamila = new Customer(2,"Jamila","jamila@mail.com",24);

            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
    }
}
