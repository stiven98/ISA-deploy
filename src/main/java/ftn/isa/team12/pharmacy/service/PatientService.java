package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.users.AccountCategory;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.User;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<Patient> findAll();
    Patient saveAndFlush(Patient patient);
    Patient findByEmail(String email);
    List<Drug> findPatientAllergies(String email);
    void addAllergy(Patient patient);
    AccountCategory findAccountCategory(String email);
    Integer findPenalty(String email);
    User findUserByEmail(String email);
    Patient updateStatus(UUID id);
    Patient save(Patient patient);
    Patient givePenalty(UUID id);
    List<Drug> findAllergiesById(UUID id);

    Patient findById(UUID patientId);
}
