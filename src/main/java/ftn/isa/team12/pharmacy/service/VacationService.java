package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Vacation;
import ftn.isa.team12.pharmacy.domain.users.VacationRequest;
import ftn.isa.team12.pharmacy.dto.VacationDTO;

import java.util.Date;
import java.util.List;

public interface VacationService {

    List<Vacation> checkVacationDay(Pharmacy pharmacy, Date date, MedicalStuff medicalStuff);
    List<VacationDTO> getAllFromPharmacyForPharmacist();

    Vacation approveVacation(VacationDTO dto);

    VacationRequest declineVacation(VacationDTO dto);

    List<VacationDTO> getAllVacationRequestFromDermatologist();

}
