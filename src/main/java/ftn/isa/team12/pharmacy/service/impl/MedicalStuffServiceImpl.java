package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.dto.PatientExaminationDTO;
import ftn.isa.team12.pharmacy.repository.DermatologistRepository;
import ftn.isa.team12.pharmacy.repository.MedicalStuffRepository;
import ftn.isa.team12.pharmacy.repository.PharmacistRepository;
import ftn.isa.team12.pharmacy.service.ExaminationService;
import ftn.isa.team12.pharmacy.service.MedicalStuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = false)
public class MedicalStuffServiceImpl implements MedicalStuffService {

    @Autowired
    private MedicalStuffRepository medicalStuffRepository;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    DermatologistRepository dermatologistRepository;

    @Autowired
    PharmacistRepository pharmacistRepository;

    @Override
    public MedicalStuff findById(UUID id) {
        return this.medicalStuffRepository.findByUserId(id);
    }

    @Override
    public MedicalStuff findByEmail(String email) {
        return this.medicalStuffRepository.findByLoginInfoEmail(email);
    }

    @Override
    public Set<PatientExaminationDTO> findPatientsByMedicalStuff(MedicalStuff medicalStuff) {
        List<Examination> examinations = examinationService.findAllByEmployee(medicalStuff);
        Set<PatientExaminationDTO> patients = new HashSet<>();
        if(examinations == null){
            return patients;
        }
        examinations.forEach(examination -> { if(examination.getPatient() != null) patients.add(new PatientExaminationDTO(examination.getPatient(), examination)); });
        return patients;
    }

    @Override
    public Set<Pharmacy> findMyPharmacies(String email) {
        MedicalStuff medicalStuff = medicalStuffRepository.findByLoginInfoEmail(email);
        Set<Pharmacy> pharmacies = new HashSet<>();
        boolean isDermatologist;
        boolean isPharmacist;
        Dermatologist dermatologist = this.dermatologistRepository.findDermatologistById(medicalStuff.getUserId());
        Pharmacist pharmacist = this.pharmacistRepository.findByUserId(medicalStuff.getUserId());
        isDermatologist = dermatologist != null;
        isPharmacist = pharmacist != null;
        if(isDermatologist){
            return dermatologist.getPharmacies();
        }
        if(isPharmacist){
            pharmacies.add(pharmacist.getPharmacy());
        }
        return pharmacies;
    }

    @Override
    public MedicalStuff saveAndFlush(MedicalStuff medicalStuff) {
        return medicalStuffRepository.saveAndFlush(medicalStuff);
    }
    @Override
    public MedicalStuff save(MedicalStuff medicalStuff) {
        return this.medicalStuffRepository.save(medicalStuff);
    }

    @Override
    public List<MedicalStuff> findAll() {
        return this.medicalStuffRepository.findAll();
    }
}
