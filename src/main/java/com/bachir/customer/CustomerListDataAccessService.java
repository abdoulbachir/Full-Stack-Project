package com.bachir.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Implement DAO

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

    //db lol
    private static List<Customer> customers;
    static {
        customers=new ArrayList<>();
        Customer alex=new Customer(1L,"Alex","alex@mail.com",22);
        customers.add(alex);
        Customer jamila=new Customer(2L,"Jamila","jamila@mail.com",24);
        customers.add(jamila);
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c-> c.getEmail().equals(email));
    }
    @Override
    public boolean existsPersonWithId(long id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleteCustomerById(long id) {
        customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .ifPresent(c -> customers.remove(c));
    }

    @Override
    public void updateCustomer(Customer customerUpdate) {
        customers.add(customerUpdate);
    }

}
