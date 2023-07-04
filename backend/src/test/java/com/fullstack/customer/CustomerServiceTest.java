package com.fullstack.customer;

import com.fullstack.exception.DuplicateResourceException;
import com.fullstack.exception.ResourceNotFoundException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDA0 customerDA0;
    private CustomerService underTest;
    private final CustomerDTOMapper customerDTOMapper = new CustomerDTOMapper();

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDA0, customerDTOMapper, passwordEncoder);
    }


    @Test
    void getAllCustomers() {
        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDA0).selectAllCustomers();
    }


    @Test
    void canGetCustomer() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", "password", 19,
                Gender.MALE);
        when(customerDA0.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerDTO expected = customerDTOMapper.apply(customer);

        // When
        CustomerDTO actual = underTest.getCustomer(id);

        // Then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional() {
        // Given
        int id = 10;

        when(customerDA0.selectCustomerById(id))
                .thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }


    @Test
    void addCustomer() {
        // Given
        String email = "alex@gmail.com";
        when(customerDA0.existsCustomerWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",email, "password", 19, Gender.MALE
        );

        String passwordHash = "¢5554ml;f;lsd";
        when(passwordEncoder.encode(request.password())).thenReturn(passwordHash);

        // When
        underTest.addCustomer(request);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDA0).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getPassword()).isEqualTo(passwordHash);
    }


    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        // Given
        String email = "alex@gmail.com";
        when(customerDA0.existsCustomerWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",email, "password", 19, Gender.MALE
        );

        // When
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already taken");

        // Then
        verify(customerDA0, never()).insertCustomer(any());
    }


    @Test
    void deleteCustomerById() {
        // Given
        int id = 10;
        when(customerDA0.existsCustomerWithId(id)).thenReturn(true);

        // When
        underTest.deleteCustomerById(id);

        // Then
        verify(customerDA0).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        // Given
        int id = 10;
        when(customerDA0.existsCustomerWithId(id)).thenReturn(false);

        // When
        assertThatThrownBy(() -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));

        // Then
        verify(customerDA0, never()).deleteCustomerById(id);
    }


    @Test
    void canUpdateAllCustomerProperties() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", "password", 19,
                Gender.MALE);
        when(customerDA0.selectCustomerById(id))
                .thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail, 23
        );


        when(customerDA0.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // when
        underTest.updateCustomer(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDA0).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
    }
}