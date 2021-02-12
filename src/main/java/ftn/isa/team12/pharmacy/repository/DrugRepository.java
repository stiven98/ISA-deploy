package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DrugRepository extends JpaRepository<Drug, UUID> {

    @Query("select drug from Drug drug where drug.name = ?1")
    Drug findDrugByName(String drugName);

    Drug findByDrugId(UUID drugId);

}
