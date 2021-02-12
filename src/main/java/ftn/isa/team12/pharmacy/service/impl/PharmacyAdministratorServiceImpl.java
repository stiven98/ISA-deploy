package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.repository.PharmacyAdministratorRepository;
import ftn.isa.team12.pharmacy.service.PharmacyAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Transactional(readOnly = false)
public class PharmacyAdministratorServiceImpl implements PharmacyAdministratorService, UserDetailsService {

    @Autowired
    private PharmacyAdministratorRepository pharmacyAdministratorRepository;


    @Override
    public List<PharmacyAdministrator> findAll() { return this.pharmacyAdministratorRepository.findAll(); }

    @Override
    public PharmacyAdministrator findAdminByEmail(String email) {
        return pharmacyAdministratorRepository.findAdminByEmail(email);
    }

    @Override
    public PharmacyAdministrator findAdminByPharmacyId(UUID pharmacy) {
        return pharmacyAdministratorRepository.findAdminByPharmacyId(pharmacy);
    }

    @Override
    public PharmacyAdministrator saveAndFlush(PharmacyAdministrator pharmacyAdministratorRequest) {

        return this.pharmacyAdministratorRepository.saveAndFlush(pharmacyAdministratorRequest);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        PharmacyAdministrator phAdmin = pharmacyAdministratorRepository.findAdminByEmail(email);
        if (phAdmin == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return phAdmin;
        }
    }
}
