package com.bachir.customer;


import com.bachir.exception.DuplicateResourceException;
import com.bachir.exception.RequestValidationException;
import com.bachir.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    //Returns a
    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }
     public Customer getCustomer(Integer id){
        return customerDao.selectCustomerById(id)
                .orElseThrow(()->new ResourceNotFoundException("Customer with id [%s] not found".formatted(id)));
    }

    //Add a customer
    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //Check if email exists, if yes then throw exception
        if (customerDao.existsPersonWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("Email already taken");
        }

        //If no, then add
        Customer customer =  new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id){
        //Check if customer with that id exist, if no then throw exception
        if (!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(id));
        }
        //If yes, then remove
        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest updateRequest) {
        //
        Customer customer = getCustomer(id);

        boolean changes = false;

        if (updateRequest.name() !=null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.email() !=null && !updateRequest.email().equals(customer.getEmail())){

            //Check if the new email already exist, if yes throw DuplicateResourceException
            if (customerDao.existsPersonWithEmail(updateRequest.email())){
                throw new DuplicateResourceException("Email already taken");
            }
            //If not, then update
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.age() !=null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }

        customerDao.updateCustomer(customer);
    }
}
