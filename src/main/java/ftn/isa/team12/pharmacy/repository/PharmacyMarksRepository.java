package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.marks.PharmacyMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PharmacyMarksRepository extends JpaRepository<PharmacyMarks, UUID> {

    @Query("select pm.mark from PharmacyMarks pm where pm.pharmacy.id = ?1")
    List<Double> findPharmacyMarksByPharmacy(UUID pharmacyId);

    @Query("select pm from PharmacyMarks pm where pm.patient.userId = ?1")
    List<PharmacyMarks> findPharmacyMarksByPatientId(UUID id);


    PharmacyMarks findPharmacyMarksByPharmacyMarksId(UUID pharmacyMarksId);
}
