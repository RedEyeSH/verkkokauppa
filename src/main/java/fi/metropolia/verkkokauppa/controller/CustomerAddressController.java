package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.entity.CustomerAddress;
import fi.metropolia.verkkokauppa.service.CustomerAddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerAddressController {
    private final CustomerAddressService service;

    public CustomerAddressController(CustomerAddressService service) {
        this.service = service;
    }

    @GetMapping("/customers/{id}/addresses")
    public List<CustomerAddress> getCustomerAddresses(@PathVariable Integer id) {
        return service.getCustomerAddresses(id);
    }

    @PostMapping("/customers/{id}/addresses")
    public CustomerAddress createAddress(@PathVariable Integer id,
                                         @RequestBody CustomerAddress address) {
        return service.createAddress(id, address);
    }

    @PutMapping("/customer-addresses/{id}")
    public CustomerAddress updateAddress(@PathVariable Integer id,
                                         @RequestBody CustomerAddress address) {
        return service.updateAddress(id, address);
    }

    @DeleteMapping("/customer-addresses/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        service.deleteAddress(id);
    }
}