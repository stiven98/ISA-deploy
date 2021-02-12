package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.marks.MedicalStuffMarks;

import java.util.List;
import java.util.UUID;

public interface MedicalStuffMarksService {

    List<Double> findMedicalStuffMarksByMedicalStuff(UUID medicalStuffId);
    List<MedicalStuffMarks> findMedicalStuffMarksByPatientId(UUID patientId);
    MedicalStuffMarks findMedicalStuffMarksByMedicalStuffMarksId(UUID medicalStuffMarksId);
    MedicalStuffMarks save(MedicalStuffMarks medicalStuffMarks);
}
