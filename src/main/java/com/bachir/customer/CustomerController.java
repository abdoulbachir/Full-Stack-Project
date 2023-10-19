package com.bachir.customer;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// The @RestController annotation marks this class as a controller where every method returns a domain object instead of a view.
// It is shorthand for including both @Controller and @ResponseBody.
@RestController
// The @RequestMapping annotation is used to map web requests to Spring Controller methods.
// Here, it defines the root URL for this controller, which is "api/v1/customers".
@RequestMapping("api/v1/customers")
public class CustomerController {

    // Dependency injection of the CustomerService to promote loose coupling and easier unit testing.
    private final CustomerService customerService;

    // Constructor-based dependency injection of the customer service.
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Handler for getting a list of all customers.
    // The @GetMapping annotation is a shortcut for @RequestMapping(method = GET).
    // This method handles GET requests for the URL defined by the class-level @RequestMapping plus "/".
    @GetMapping("")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();  // Delegates the processing to the service layer and returns the result as JSON.
    }

    // Handler for getting a single customer by their ID.
    // The @PathVariable annotation indicates that a method parameter should be bound to a URI template variable.
    @GetMapping("{CustomerID}")
    public Customer getCustomer(@PathVariable("CustomerID") Integer customerID) {
        return customerService.getCustomerById(customerID);  // Retrieves the customer with the specified ID.
    }

    // Handler for registering a new customer.
    // The @PostMapping annotation is a shortcut for @RequestMapping(method = POST).
    // The @RequestBody annotation indicates a method parameter should be bound to the body of the HTTP request.
    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request) {
        customerService.addCustomer(request);  // Delegates the processing of saving the customer to the service layer.
    }

    // Handler for deleting a specific customer by their ID.
    @DeleteMapping("{CustomerID}")
    public void removeCustomer(@PathVariable("CustomerID") Integer customerID) {
        customerService.deleteCustomerById(customerID);  // Delegates the deletion of the customer to the service layer.
    }

    // Handler for updating an existing customer.
    // The @PutMapping annotation is a shortcut for @RequestMapping(method = PUT).
    @PutMapping("{CustomerID}")
    public void updateCustomer(@PathVariable("CustomerID") Integer customerID,
                               @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerID, updateRequest);  // Delegates the updating of the customer to the service layer.
    }
}
