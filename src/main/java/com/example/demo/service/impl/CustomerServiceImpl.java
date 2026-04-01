package com.example.demo.service.impl;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);

        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setCity(customer.getCity());

        return customerRepository.save(existing);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer existing = getCustomerById(id);
        customerRepository.delete(existing);
    }
}