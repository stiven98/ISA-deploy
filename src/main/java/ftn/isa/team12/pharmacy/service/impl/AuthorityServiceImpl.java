package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import ftn.isa.team12.pharmacy.repository.AuthorityRepository;
import ftn.isa.team12.pharmacy.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@Transactional(readOnly = false)
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findById(UUID id) {
        Authority auth = this.authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByRole(String role) {
        Authority auth = this.authorityRepository.findByRole(role);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
