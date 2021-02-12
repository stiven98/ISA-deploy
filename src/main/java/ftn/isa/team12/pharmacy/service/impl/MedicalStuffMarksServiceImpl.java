package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.marks.MedicalStuffMarks;
import ftn.isa.team12.pharmacy.repository.MedicalStuffMarksRepository;
import ftn.isa.team12.pharmacy.service.MedicalStuffMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class MedicalStuffMarksServiceImpl implements MedicalStuffMarksService {

    @Autowired
    private MedicalStuffMarksRepository medicalStuffMarksRepository;


    @Override
    public List<Double> findMedicalStuffMarksByMedicalStuff(UUID medicalStuffId) {
        return this.medicalStuffMarksRepository.findMedicalStuffMarksByMedicalStuff(medicalStuffId);
    }

    @Override
    public List<MedicalStuffMarks> findMedicalStuffMarksByPatientId(UUID patientId) {
        return this.medicalStuffMarksRepository.findMedicalStuffMarksByPatientId(patientId);
    }

    @Override
    public MedicalStuffMarks findMedicalStuffMarksByMedicalStuffMarksId(UUID medicalStuffMarksId) {
        return this.medicalStuffMarksRepository.findMedicalStuffMarksByMedicalStuffMarksId(medicalStuffMarksId);
    }

    @Override
    public MedicalStuffMarks save(MedicalStuffMarks medicalStuffMarks) {
        return this.medicalStuffMarksRepository.save(medicalStuffMarks);
    }
}
