package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.enums.VacationRequestStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.VacationRequest;
import ftn.isa.team12.pharmacy.dto.VacationRequestDTO;
import ftn.isa.team12.pharmacy.repository.VacationRequestRepository;
import ftn.isa.team12.pharmacy.service.PharmacyService;
import ftn.isa.team12.pharmacy.service.VacationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class VacationRequestServiceImpl implements VacationRequestService {

    @Autowired
    VacationRequestRepository vacationRequestRepository;

    @Autowired
    PharmacyService pharmacyService;

    @Override
    public VacationRequest createNewRequest(MedicalStuff medicalStuff, VacationRequestDTO request) {
        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setDateRange(request.getDateRange());
        vacationRequest.setEmployee(medicalStuff);
        Pharmacy p = pharmacyService.findPharmacyById(request.getPharmacy());
        vacationRequest.setPharmacy(p);
        vacationRequest.setStatus(VacationRequestStatus.created);
        return this.vacationRequestRepository.save(vacationRequest);
    }
}
