package com.bachir.journey;

import com.bachir.customer.Customer;
import main.com.bachir.customer.CustomerRegistrationRequest;
import main.com.bachir.customer.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {

    private static final Random RANDOM = new Random();
    private static final String CUSTOMER_URI = "/api/v1/customers";

    @Autowired
    private WebTestClient webTestClient;


    @Test
    void canRegisterCustomer() {
        // create registration request;
        Faker faker = new Faker();
        Name FakerName = faker.name();

        String name = FakerName.fullName();
        String email = FakerName.lastName() +"_"+ UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1,99);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        //send a post
        webTestClient.post() //Create a new WebClient instance and configures it to perform a POST request.
                .uri(CUSTOMER_URI) //Set the request URI to /api/v1/customers.
                .accept(MediaType.APPLICATION_JSON) // Set the accepted media type to APPLICATION_JSON. This means that the test client will only accept responses in JSON format.
                .contentType(MediaType.APPLICATION_JSON) // Set the request content type to APPLICATION_JSON. This means that the test client will send the request body in JSON format.
                .body(Mono.just(request), CustomerRegistrationRequest.class) // Set the request body to the request object, which is a CustomerRegistrationRequest object. The Mono.just() method creates a Mono instance that emits the request object.
                .exchange() // Perform the request and returns a Flux instance that emits the response.

                // Assert that the response status code is 200 OK.
                .expectStatus()
                .isOk();

        //get all the customers

        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        //Make user that customer is present
        Customer expectedCustomer = new Customer(name, email, age);
        assertThat(allCustomers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(expectedCustomer);

        //get customer by id
        Long id = allCustomers.stream()
                        .filter(c->c.getEmail().equals(email))
                                .map(Customer::getId)
                                        .findFirst()
                                                .orElseThrow();

        expectedCustomer.setId(id);

        webTestClient.get()
                .uri(CUSTOMER_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {})
                .isEqualTo(expectedCustomer);
    }


    @Test
    void canDeleteCustomer() {
        // create registration request;
        Faker faker = new Faker();
        Name FakerName = faker.name();

        String name = FakerName.fullName();
        String email = FakerName.lastName() +"_"+ UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1,99);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        //send a post
        webTestClient.post() //Create a new WebClient instance and configures it to perform a POST request.
                .uri(CUSTOMER_URI) //Set the request URI to /api/v1/customers.
                .accept(MediaType.APPLICATION_JSON) // Set the accepted media type to APPLICATION_JSON. This means that the test client will only accept responses in JSON format.
                .contentType(MediaType.APPLICATION_JSON) // Set the request content type to APPLICATION_JSON. This means that the test client will send the request body in JSON format.
                .body(Mono.just(request), CustomerRegistrationRequest.class) // Set the request body to the request object, which is a CustomerRegistrationRequest object. The Mono.just() method creates a Mono instance that emits the request object.
                .exchange() // Perform the request and returns a Flux instance that emits the response.

                // Assert that the response status code is 200 OK.
                .expectStatus()
                .isOk();

        //get all the customers

        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();


        Long id = allCustomers.stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //delete customer
        webTestClient.delete()
                .uri(CUSTOMER_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id
        webTestClient.get()
                .uri(CUSTOMER_URI+"/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomer() {
        // create registration request;
        Faker faker = new Faker();
        Name FakerName = faker.name();

        String name = FakerName.fullName();
        String email = FakerName.lastName() +"_"+ UUID.randomUUID() + "@amigoscode.com";
        int age = RANDOM.nextInt(1,99);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(name, email, age);

        //send a post
        webTestClient.post() //Create a new WebClient instance and configures it to perform a POST request.
                .uri(CUSTOMER_URI) //Set the request URI to /api/v1/customers.
                .accept(MediaType.APPLICATION_JSON) // Set the accepted media type to APPLICATION_JSON. This means that the test client will only accept responses in JSON format.
                .contentType(MediaType.APPLICATION_JSON) // Set the request content type to APPLICATION_JSON. This means that the test client will send the request body in JSON format.
                .body(Mono.just(request), CustomerRegistrationRequest.class) // Set the request body to the request object, which is a CustomerRegistrationRequest object. The Mono.just() method creates a Mono instance that emits the request object.
                .exchange() // Perform the request and returns a Flux instance that emits the response.

                // Assert that the response status code is 200 OK.
                .expectStatus()
                .isOk();

        //get all the customers

        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {})
                .returnResult()
                .getResponseBody();

        Long id = allCustomers.stream()
                .filter(c->c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        //update customer
        String newName = "Abdoul Bachir";
        String newEmail =  FakerName.lastName() +"_"+ UUID.randomUUID() + "@amigoscode.com";
        int newAge = 56;
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(newName, newEmail, newAge);

        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateRequest),CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(id, newName, newEmail, newAge);

        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
