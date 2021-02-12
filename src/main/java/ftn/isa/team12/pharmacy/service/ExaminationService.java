package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.dto.*;
import ftn.isa.team12.pharmacy.domain.users.Patient;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ExaminationService {
    List<Examination> findAll();
    List<Examination> findAllByEmployee(MedicalStuff employee);
    List<Examination> findAllByPatient(Patient patient);
    List<Examination> findAllByEmployeeAndPharmacy(MedicalStuff employee, Pharmacy pharmacy);

    void checkExamination(ExaminationCreateDTO dto);

    Examination addExaminationForDermatologist(ExaminationCreateDTO dto);
    BusyDateDTO busyTime(String email, Date date);

    Examination findById(UUID id);
    Examination findCurrentById(UUID id);
    Examination scheduleNewMedStuff(ExaminationScheduleMedStuffDTO dto);
    List<Pharmacy> findAllPharmaciesWherePatientHadExamination(UUID patientId);
    List<MedicalStuff> findAllMedicalStuffThatTreatedPatient(UUID patientId);
    List<Examination> findPharmaciesWithFreeTerm(Date date, LocalTime time);
    List<MedicalStuff> findAvailableByPharmacy(String pharmacyName);
    Examination findByEmployeePharmacyTimeDate(UUID userId, String pharmacyName, Date date, LocalTime time);
    Examination save(Examination examination);
    List<Examination> findPharmacistConsultationsForPatient(UUID patientId);
    List<Examination> findDermatologistExaminationsForPatient(UUID patientId);

    List<Examination> findFreeTermsForDermatologistsByPhamracy(String pharmacyName);



    List<Examination> findAllFreeByEmployeeAndPharmacy(MedicalStuff medicalStuff, Pharmacy pharmacy);

    Examination scheduleExistingMedStuff(ExaminationScheduleExistingMedStuffDTO dto);

    Examination submitExaminationData(ExaminationSubmissionDTO dto);

    List<Examination> findAllByEmployeeAndPatient(MedicalStuff medicalStuff, Patient patient);
}
