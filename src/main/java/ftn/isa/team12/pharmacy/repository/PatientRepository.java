package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.users.AccountCategory;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    @Query("SELECT patient from Patient patient WHERE patient.loginInfo.email = ?1")
    Patient findByEmail(String email);

    @Query("select patient.allergies from Patient patient where patient.loginInfo.email = ?1")
    List<Drug> findPatientAllergies(String email);

    @Query("select patient.allergies from Patient patient where patient.userId = ?1")
    List<Drug> findPatientAllergiesById(UUID id);

    @Query("select patient.category from Patient patient where patient.loginInfo.email = ?1")
    AccountCategory findAccountCategory(String email);

    @Query("select patient.penalties from Patient patient where patient.loginInfo.email = ?1")
    Integer findPenalty(String email);

    @Query("SELECT user from User user WHERE user.loginInfo.email = ?1")
    User findUserByEmail(String email);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="2000")})
    Optional<Patient> findById(UUID uuid);

}
