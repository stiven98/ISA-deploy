package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.users.Supplier;

public interface SupplierService {

    Supplier saveAndFlush(Supplier supplierRequest);
    Supplier findByEmail(String email);
}
