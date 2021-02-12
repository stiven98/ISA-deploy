package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface PharmacyAdministratorRepository extends JpaRepository<PharmacyAdministrator, UUID> {

    @Query("select s from PharmacyAdministrator s where s.loginInfo.email= ?1")
    PharmacyAdministrator findAdminByEmail(String email);



    @Query("select s from PharmacyAdministrator s where s.pharmacy.id= ?1")
    PharmacyAdministrator findAdminByPharmacyId(UUID pharmacyId);




}
