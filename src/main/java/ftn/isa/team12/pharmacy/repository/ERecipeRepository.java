package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipe;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface ERecipeRepository  extends JpaRepository<ERecipe, UUID> {
    @Query("select erecipe from ERecipe erecipe  where erecipe.patient.userId = ?1")
    List<ERecipe> findAllERecipesByPatient(UUID patientId);

    @Query("select erecipe.pharmacy from ERecipe erecipe where erecipe.patient.userId=?1")
    List<Pharmacy> findPharmaciesWherePatientHasERecipe(UUID patientId);


}
