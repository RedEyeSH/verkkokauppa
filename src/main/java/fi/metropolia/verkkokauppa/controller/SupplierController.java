package fi.metropolia.verkkokauppa.controller;

import fi.metropolia.verkkokauppa.entity.Supplier;
import fi.metropolia.verkkokauppa.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @GetMapping
    public List<Supplier> getSuppliers() {
        return service.getSuppliers();
    }

    @PostMapping
    public Supplier createSupplier(@RequestBody Supplier supplier) {
        return service.createSupplier(supplier);
    }

    @DeleteMapping("/{id}")
    public void deleteSupplier(@PathVariable Integer id) {
        service.deleteSupplier(id);
    }
}