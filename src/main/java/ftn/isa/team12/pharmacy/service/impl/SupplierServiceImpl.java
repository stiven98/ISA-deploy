package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.users.Supplier;
import ftn.isa.team12.pharmacy.repository.SupplierRepository;
import ftn.isa.team12.pharmacy.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier saveAndFlush(Supplier supplierRequest) {
        supplierRequest.getAccountInfo().setFirstLogin(true);
        supplierRequest.getAccountInfo().setActive(false);
        return this.supplierRepository.saveAndFlush(supplierRequest);
    }

    @Override
    public Supplier findByEmail(String email) {
        return this.supplierRepository.findByEmail(email);
    }
}
