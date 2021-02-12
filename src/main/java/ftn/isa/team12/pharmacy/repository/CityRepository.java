package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.common.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface CityRepository extends JpaRepository<City, UUID> {

    City findByName(String name);

    @Query("SELECT c FROM City c WHERE c.country.countryId=?2 and c.name=?1")
    City findByNameAndCountryId(String name, UUID id);

    @Query("SELECT c FROM City c WHERE c.country.name = ?1")
    List<City> findByCountry(String name);

}
