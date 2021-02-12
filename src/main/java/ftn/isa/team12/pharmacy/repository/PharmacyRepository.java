package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;
public interface PharmacyRepository extends JpaRepository<Pharmacy, UUID> {

    @Query("select p from Pharmacy p where p.id= ?1")
    Pharmacy findPharmacyById(UUID pharmacyId);

    @Query("select s from Pharmacy s where s.name= ?1")
    Pharmacy findPharmacyByName(String name);

}
