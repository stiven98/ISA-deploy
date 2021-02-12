package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import java.util.List;
import java.util.UUID;

public interface AuthorityService {
    List<Authority> findById(UUID id);
    List<Authority> findByRole(String role);
}
