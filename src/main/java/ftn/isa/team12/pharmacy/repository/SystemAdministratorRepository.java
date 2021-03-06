package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.SystemAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SystemAdministratorRepository extends JpaRepository<SystemAdministrator, UUID> {
}
