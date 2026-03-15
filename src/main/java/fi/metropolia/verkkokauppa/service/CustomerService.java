package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.entity.Customer;
import fi.metropolia.verkkokauppa.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existing = getCustomerById(id);

        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());

        return customerRepository.save(existing);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}