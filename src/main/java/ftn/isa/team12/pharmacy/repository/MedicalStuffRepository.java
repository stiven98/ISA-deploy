package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.UUID;

public interface MedicalStuffRepository extends JpaRepository<MedicalStuff, UUID> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="2000")})
    MedicalStuff findByUserId(UUID userId);
    MedicalStuff findByLoginInfoEmail(String email);
}
