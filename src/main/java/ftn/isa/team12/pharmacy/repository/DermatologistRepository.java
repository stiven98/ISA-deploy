package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.UUID;

public interface DermatologistRepository extends JpaRepository<Dermatologist, UUID> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="1000")})
    Dermatologist findByLoginInfoEmail(String email);

    @Query("select p from Dermatologist p where p.userId= ?1")
    Dermatologist findDermatologistById(UUID id);

}
