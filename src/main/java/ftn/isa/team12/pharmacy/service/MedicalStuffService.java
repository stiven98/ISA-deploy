package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.dto.PatientExaminationDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MedicalStuffService {

    MedicalStuff findById(UUID id);
    MedicalStuff findByEmail(String email);
    Set<PatientExaminationDTO> findPatientsByMedicalStuff(MedicalStuff medicalStuff);
    Set<Pharmacy> findMyPharmacies(String email);
    MedicalStuff saveAndFlush(MedicalStuff medicalStuff);
    MedicalStuff save(MedicalStuff medicalStuff);

    List<MedicalStuff> findAll();
}
