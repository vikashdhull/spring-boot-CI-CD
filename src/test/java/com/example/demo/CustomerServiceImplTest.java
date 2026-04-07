package com.example.demo;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.impl.CustomerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ CREATE
    @Test
    void testCreateCustomer() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("Vikash")
                .email("vikash@test.com")
                .city("Delhi")
                .build();

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
        assertEquals("Vikash", result.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    // ✅ GET BY ID (SUCCESS)
    @Test
    void testGetCustomerById_Success() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("Vikash")
                .email("vikash@test.com")
                .city("Delhi")
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertNotNull(result);
        assertEquals("Vikash", result.getName());
    }

    // ✅ GET BY ID (NOT FOUND)
    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.getCustomerById(1L);
        });

        assertEquals("Customer not found", exception.getMessage());
    }

    // ✅ GET ALL
    @Test
    void testGetAllCustomers() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "A", "a@test.com", "Delhi"),
                new Customer(2L, "B", "b@test.com", "Mumbai")
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
    }

    // ✅ UPDATE
    @Test
    void testUpdateCustomer() {
        Customer existing = new Customer(1L, "Old", "old@test.com", "Delhi");

        Customer updated = new Customer(null, "New", "new@test.com", "Noida");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(any(Customer.class))).thenReturn(existing);

        Customer result = customerService.updateCustomer(1L, updated);

        assertEquals("New", result.getName());
        assertEquals("new@test.com", result.getEmail());
        assertEquals("Noida", result.getCity());
    }

    // ✅ DELETE
    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer(1L, "Test", "test@test.com", "Delhi");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepository, times(1)).delete(customer);
    }
}