package com.bachir.customer;

import org.springframework.data.jpa.repository.JpaRepository;

//This interface is responsible for all the CRUD operation and other complex theories
public interface CustomerRepository extends JpaRepository<Customer, Integer> { //Entity to act upon 'Customer' and data type of Customer 'id'
    /**These two are auto-generate, so no need to be tested.
     * BUt always test when there are custom complex query I created.
     */
    boolean existsCustomerByEmail(String email);
    boolean existsCustomerById(Long id);
}
