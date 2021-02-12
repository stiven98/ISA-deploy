package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugReservation;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.dto.DrugReservationDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DrugReservationService {

    DrugReservation createDrugReservation(DrugReservationDTO drugReservationDTO) throws Exception;

    List<DrugReservation> findDrugReservationByPatient(String patientEmail);

    DrugReservation cancelReservation(UUID id);

    List<Pharmacy> findPharmaciesWherePatientReservedDrugs(String patientEmail);

    List<Drug> findDrugsPatientReserved(String patientEmail);

    DrugReservation findDrugReservationByIdAndPharmacyId(UUID id, UUID pharmacyId);

    DrugReservation issueDrug(UUID id);

    List<DrugReservation> findAllBefore(Date today);
}
