package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.common.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
    Country findByName(String name);
}
