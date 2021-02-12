package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DrugMarksRepository extends JpaRepository<DrugMarks, UUID> {
    @Query("select dm.mark from DrugMarks dm where dm.drug.drugId = ?1")
    List<Double> findDrugMarksByDrug(UUID drugId);

    @Query("select dm from DrugMarks  dm where dm.patient.userId = ?1")
    List<DrugMarks> findDrugMarksByPatientId(UUID id);

    DrugMarks findDrugMarksByDrugMarksId(UUID drugMarksId);
}
