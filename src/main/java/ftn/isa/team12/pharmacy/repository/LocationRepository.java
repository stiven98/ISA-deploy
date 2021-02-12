package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.common.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

    @Query("SELECT loc FROM Location loc WHERE loc.address.street = ?1 and loc.address.number = ?2 and  loc.city.id = ?3")
    Location findByLocationAndCity(String street, int number, UUID city);
}
