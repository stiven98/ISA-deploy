package ftn.isa.team12.pharmacy.service;

import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.VacationRequest;
import ftn.isa.team12.pharmacy.dto.VacationRequestDTO;

public interface VacationRequestService {
    VacationRequest createNewRequest(MedicalStuff medicalStuff, VacationRequestDTO request);
}
