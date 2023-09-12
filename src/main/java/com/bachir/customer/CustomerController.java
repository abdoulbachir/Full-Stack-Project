package com.bachir.customer;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    //  Get all the customers
    @GetMapping("")
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    //Get specific customer based on id
    @GetMapping("{CustomerID}")
    public Customer getCustomer(@PathVariable("CustomerID") Integer customerID){
        return customerService.getCustomer(customerID);
    }

    //Add new customer
    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    //Delete customer by id
    @DeleteMapping("{CustomerID}")
    public void removedCustomer(@PathVariable("CustomerID") Integer customerID){
        customerService.deleteCustomerById(customerID);
    }

    //Update customer based on id
    @PutMapping("{CustomerID}")
    public void updateCustomer(@PathVariable("CustomerID") Integer customerID,
                               @RequestBody CustomerUpdateRequest updateRequest){
        customerService.updateCustomer(customerID, updateRequest);
    }
}
