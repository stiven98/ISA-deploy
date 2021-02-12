package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {
    Authority findByRole(String role);

}
