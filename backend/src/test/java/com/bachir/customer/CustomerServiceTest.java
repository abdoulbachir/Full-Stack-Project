package com.bachir.customer;

import com.bachir.exception.DuplicateResourceException;
import com.bachir.exception.RequestValidationException;
import com.bachir.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //You can just do this instead of doing 1, 2 and 3.
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    //Used to close the resource after each test
    // 1. private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        //2. autoCloseable = MockitoAnnotations.openMocks(this);   // This returns an instance of AutoCloseable
        underTest = new CustomerService(customerDao);
    }

    //This is so after each test we have a new mock to work with
/** 3.  @AfterEach
*    void tearDown() {
*    }
 */

    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void canGetCustomer() {
        // Given
        long id = 10;
        Customer customer = new Customer(id, "Alex", "alex@mail.com",19);

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.getCustomerById(10);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowExceptionWhenCantGetCustomer() {
        // Given
        long id = 10;
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(()->underTest.getCustomerById((int) id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        // Given
        String email = "alex@mail.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email, 19);

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class); //Only for objects we construct in methods ourselves.
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void willThrowWhenEmailExistWhileAddingCustomer() {
        // Given
        String email = "alex@mail.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email, 19);

        // When
        assertThatThrownBy(()->underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        // Then
        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        // Given
        long id = 10;
        when(customerDao.existsPersonWithId(id)).thenReturn(true);

        // When
        underTest.deleteCustomerById((int) id);

        // Then
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // Given
        long id = 10;
        when(customerDao.existsPersonWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(()->underTest.deleteCustomerById((int) id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found".formatted(id));
        // Then
        verify(customerDao, never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomersProperties() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        String newEmail = "Alexandro@mail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", newEmail,23);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomer(id ,updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void canUpdateOnyCustomerName() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("Alexandro", null,null);

        // When
        underTest.updateCustomer(id ,updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }

    @Test
    void canUpdateOnyCustomerEmail() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        String newEmail = "Alexandro@mail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail,null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);
        // When
        underTest.updateCustomer(id ,updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
    }


    @Test
    void canUpdateOnyCustomerAge() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, null,22);

        // When
        underTest.updateCustomer(id ,updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWithExistingEmail() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        String newEmail = "Alexandro@mail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(null, newEmail,null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);
        // When
        assertThatThrownBy(()->underTest.updateCustomer(id ,updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        int id = 10;
        Customer customer = new Customer("Alex","alex@gmail.com",19);
        when(customerDao.selectCustomerById((long) id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(customer.getName(),
                customer.getEmail(),customer.getAge());

        // When
        assertThatThrownBy(()->underTest.updateCustomer(id ,updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}