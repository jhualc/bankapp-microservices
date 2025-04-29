package com.microservices.bankapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.bankapp.entity.Customer;
import com.microservices.bankapp.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Juan Pérez");
        customer.setGender("M");
        customer.setAge(30);
        customer.setIdentification("1234567890");
        customer.setAddress("Calle Falsa 123");
        customer.setPhone("3001234567");
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Mockito.when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan Pérez"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Mockito.when(customerService.getCustomerById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    @Test
    void testCreateCustomer() throws Exception {
        Mockito.when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Mockito.when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(customer);

        mockMvc.perform(put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan Pérez"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/customers/1"))
                .andExpect(status().isNoContent());
    }
}
