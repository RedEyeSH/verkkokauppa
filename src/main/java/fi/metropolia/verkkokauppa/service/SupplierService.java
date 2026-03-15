package fi.metropolia.verkkokauppa.service;

import fi.metropolia.verkkokauppa.entity.Supplier;
import fi.metropolia.verkkokauppa.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplier(Integer id) {
        return supplierRepository.findById(id).orElseThrow();
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Integer id, Supplier supplier) {
        Supplier existing = getSupplier(id);

        existing.setName(supplier.getName());
        existing.setContactName(supplier.getContactName());
        existing.setPhone(supplier.getPhone());
        existing.setEmail(supplier.getEmail());

        return supplierRepository.save(existing);
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }
}