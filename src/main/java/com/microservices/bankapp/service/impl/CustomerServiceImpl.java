package com.microservices.bankapp.service.impl;

import com.microservices.bankapp.entity.Customer;
import com.microservices.bankapp.repository.CustomerRepository;
import com.microservices.bankapp.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setGender(updatedCustomer.getGender());
        existingCustomer.setAge(updatedCustomer.getAge());
        existingCustomer.setIdentification(updatedCustomer.getIdentification());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setPhone(updatedCustomer.getPhone());
        existingCustomer.setStatus(updatedCustomer.getStatus());
        return customerRepository.save(existingCustomer);
    }



    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
