package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.Complaint;
import ftn.isa.team12.pharmacy.domain.enums.StatusOfComplaint;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.AnswerDTO;
import ftn.isa.team12.pharmacy.dto.ComplaintDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.ComplaintRepository;
import ftn.isa.team12.pharmacy.service.ComplaintService;
import ftn.isa.team12.pharmacy.service.MedicalStuffService;
import ftn.isa.team12.pharmacy.service.PatientService;
import ftn.isa.team12.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private MedicalStuffService medicalStuffService;

    @Autowired
    private EmailSender sender;

    public Complaint saveAndFlush(ComplaintDTO complaintRequest) {
        Complaint complaint = new Complaint();

        Patient patient = this.patientService.findByEmail(complaintRequest.getEmailPatient());

        if (patient == null) {
            throw new IllegalArgumentException("Wrong email!");
        }
        complaint.setPatient(patient);
        complaint.setStatusOfComplaint(StatusOfComplaint.no_answered);
        complaint.setContent(complaintRequest.getContent());

        if (complaintRequest.getForWho().equals("Dermatologist and pharmacist")) {
            MedicalStuff medicalStuff = medicalStuffService.findById(UUID.fromString(complaintRequest.getMedicalStaffId()));
            if (medicalStuff == null) {
                throw new IllegalArgumentException("Wrong id of medical stuff!");
            }
            complaint.setMedicalStuff(medicalStuff);

        } else if( complaintRequest.getForWho().equals("Pharmacy")) {
            Pharmacy pharmacy = pharmacyService.findPharmacyById(UUID.fromString(complaintRequest.getPharmacyId()));
            if (pharmacy == null) {
                throw new IllegalArgumentException("Wrong id of pharmacy!");

            }
            complaint.setPharmacy(pharmacy);


        } else {
            throw new IllegalArgumentException("Error!");
        }

        complaint = complaintRepository.saveAndFlush(complaint);
        return complaint;
    }

    @Override
    public List<Complaint> findAll() {
        return this.complaintRepository.findAll();
    }

    @Override
    public void sendAnswer(AnswerDTO answerRequest) {
        Complaint complaint = this.complaintRepository.findById(UUID.fromString(answerRequest.getComplaintId())).get();
        if (complaint == null) {
            throw new IllegalArgumentException("Wrong id of complaint!");
        }
        sender.sendAnswerOnComplaint(answerRequest);
        System.out.print("Email has been sent!");
        complaint.setStatusOfComplaint(StatusOfComplaint.answered);
        complaintRepository.saveAndFlush(complaint);
    }
}
