package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.marks.MedicalStuffMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MedicalStuffMarksRepository extends JpaRepository<MedicalStuffMarks, UUID> {

    @Query("select mm.mark from MedicalStuffMarks  mm where mm.medicalStuff.userId = ?1")
    List<Double> findMedicalStuffMarksByMedicalStuff(UUID medicalStuffId);

    @Query("select mm from MedicalStuffMarks  mm where mm.patient.userId = ?1")
    List<MedicalStuffMarks> findMedicalStuffMarksByPatientId(UUID patientId);

    MedicalStuffMarks findMedicalStuffMarksByMedicalStuffMarksId(UUID medicalStuffMarksId);

}
