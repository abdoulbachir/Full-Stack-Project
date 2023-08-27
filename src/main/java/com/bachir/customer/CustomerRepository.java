package com.bachir.customer;

import org.springframework.data.jpa.repository.JpaRepository;

//This interface is responsible for all the CRUD operation and other complex theories
public interface CustomerRepository extends JpaRepository<Customer, Integer> { //Entity to act upon 'Customer' and data type of Customer 'id'
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Integer id);
}
