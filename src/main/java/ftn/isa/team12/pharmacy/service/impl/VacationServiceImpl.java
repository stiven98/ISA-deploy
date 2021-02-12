package ftn.isa.team12.pharmacy.service.impl;


import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.enums.VacationRequestStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.*;
import ftn.isa.team12.pharmacy.dto.VacationDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.*;
import ftn.isa.team12.pharmacy.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class VacationServiceImpl implements VacationService {

    @Autowired
    VacationRepository vacationRepository;

    @Autowired
    PharmacistRepository pharmacistRepository;

    @Autowired
    VacationRequestRepository vacationRequestRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    DermatologistRepository dermatologistRepository;

    @Autowired
    EmailSender emailSender;


    @Override
    @Transactional(readOnly = false)
    public List<Vacation> checkVacationDay(Pharmacy pharmacy, Date date, MedicalStuff medicalStuff) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        return vacationRepository.getAll(pharmacyAdministrator.getPharmacy(), date, medicalStuff);
    }

    @Override
    public List<VacationDTO> getAllFromPharmacyForPharmacist() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        List<Pharmacist> pharmacists = pharmacistRepository.findAll();
        List<VacationRequest> vacationRequests = vacationRequestRepository.getAllFromPharmacy(pharmacy, new Date());
        List<VacationDTO> dto = new ArrayList<>();
        for (VacationRequest v: vacationRequests){
            for(Pharmacist p: pharmacists){
                if(p.getUserId() == v.getEmployee().getUserId())
                    dto.add(new VacationDTO(v));
            }
        }
        return dto;
    }


    @Override
    public Vacation approveVacation(VacationDTO dto) {
        VacationRequest vacationRequest = vacationRequestRepository.findByVacationId(dto.getVacationId());

        if(vacationRequest == null)
            throw new IllegalArgumentException("No vacation request with code:" + dto.getVacationId());
        if(vacationRequest.getStatus() == VacationRequestStatus.accepted)
            throw new IllegalArgumentException("Already accepted request with code:" + dto.getVacationId());
        if(vacationRequest.getStatus() == VacationRequestStatus.rejected)
            throw new IllegalArgumentException("Already rejected request with code:" + dto.getVacationId());

        vacationRequest.setStatus(VacationRequestStatus.accepted);
        vacationRequest.setNote(dto.getNote());
        vacationRequest = vacationRequestRepository.save(vacationRequest);

        Vacation vacation = new Vacation();
        if(vacationRequest != null){
            vacation.setEmployee(vacationRequest.getEmployee());
            vacation.setPharmacy(vacationRequest.getPharmacy());
            vacation.setDateRange(new DateRange());
            vacation.getDateRange().setStartDate(dto.getStartDate());
            vacation.getDateRange().setEndDate(dto.getEndDate());
            vacation = vacationRepository.save(vacation);
        }
        try{
            emailSender.sendEmailEmployee(vacationRequest.getVacationId(),vacationRequest.getEmployee().getLoginInfo().getEmail(),vacationRequest.getNote(),"accepted");
        }catch (Exception e){
            System.out.println(e);
        }






        return vacation;
    }


    @Override
    public VacationRequest declineVacation(VacationDTO dto) {
        VacationRequest vacationRequest = vacationRequestRepository.findByVacationId(dto.getVacationId());

        if(vacationRequest == null)
            throw new IllegalArgumentException("No vacation request with code:" + dto.getVacationId());
        if(vacationRequest.getStatus() == VacationRequestStatus.accepted)
            throw new IllegalArgumentException("Already accepted request with code:" + dto.getVacationId());
        if(vacationRequest.getStatus() == VacationRequestStatus.rejected)
            throw new IllegalArgumentException("Already rejected request with code:" + dto.getVacationId());

        vacationRequest.setStatus(VacationRequestStatus.rejected);
        vacationRequest.setNote(dto.getNote());
        vacationRequest = vacationRequestRepository.save(vacationRequest);

        try{
            emailSender.sendEmailEmployee(vacationRequest.getVacationId(),vacationRequest.getEmployee().getLoginInfo().getEmail(),vacationRequest.getNote(),"rejected");
        }catch (Exception e){
            System.out.println(e);
        }




        return vacationRequest;
    }


    @Override
    public List<VacationDTO> getAllVacationRequestFromDermatologist() {
        List<Dermatologist> pharmacists = dermatologistRepository.findAll();
        List<VacationRequest> vacationRequests = vacationRequestRepository.getAllFromDermatologist(new Date());
        List<VacationDTO> dto = new ArrayList<>();
        for (VacationRequest v: vacationRequests){
            for(Dermatologist p: pharmacists){
                if(p.getUserId() == v.getEmployee().getUserId())
                    dto.add(new VacationDTO(v));
            }
        }
        return dto;
    }
}