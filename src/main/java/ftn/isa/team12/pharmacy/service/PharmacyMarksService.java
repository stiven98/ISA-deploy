package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.marks.PharmacyMarks;
import java.util.List;
import java.util.UUID;

public interface PharmacyMarksService {

    List<Double> findPharmacyMarksByPharmacy(UUID pharmacyId);

    PharmacyMarks save(PharmacyMarks pharmacyMarks);

    List<PharmacyMarks> findPharmacyMarksByPatientId(UUID id);

    PharmacyMarks findByPharmacyMarksId(UUID pharmacyMarksId);
}
