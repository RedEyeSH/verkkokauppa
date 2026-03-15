package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.entity.Customer;
import fi.metropolia.verkkokauppa.entity.CustomerAddress;
import fi.metropolia.verkkokauppa.repository.CustomerAddressRepository;
import fi.metropolia.verkkokauppa.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAddressService {
    private final CustomerAddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public CustomerAddressService(CustomerAddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    public List<CustomerAddress> getCustomerAddresses(Integer customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    public CustomerAddress createAddress(Integer customerId, CustomerAddress address) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();

        address.setCustomer(customer);

        return addressRepository.save(address);
    }

    public CustomerAddress updateAddress(Integer id, CustomerAddress updated) {
        CustomerAddress existing = addressRepository.findById(id).orElseThrow();

        existing.setStreetAddress(updated.getStreetAddress());
        existing.setPostalCode(updated.getPostalCode());
        existing.setCity(updated.getCity());
        existing.setCountry(updated.getCountry());

        return addressRepository.save(existing);
    }

    public void deleteAddress(Integer id) {
        addressRepository.deleteById(id);
    }
}