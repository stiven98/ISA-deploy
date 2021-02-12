
package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.DrugReservation;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.dto.DrugReservationDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.service.DrugReservationService;
import ftn.isa.team12.pharmacy.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/drugReservation", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugReservationController {
    @Autowired
    private DrugReservationService drugReservationService;

    @Autowired
    private PharmacistService pharmacistService;

    @Autowired
    private EmailSender sender;

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/createReservation")
    public ResponseEntity<DrugReservation> createDrugReservation(@RequestBody DrugReservationDTO drugReservationDTO) throws Exception {
        DrugReservation drugReservation = drugReservationService.createDrugReservation(drugReservationDTO);
        return  new ResponseEntity<>(drugReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PHARMACIST')")
    @PostMapping("/issueDrug/{id}")
    public ResponseEntity<?> issueDrug(@PathVariable UUID id) {
        DrugReservation drugReservation = drugReservationService.issueDrug(id);
        if(drugReservation != null){
            sender.sendDrugPickingUpFeedback(drugReservation.getPatient().getUsername(), drugReservation.getDrug_reservation_id());
        }
        return  new ResponseEntity<>(drugReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PHARMACIST')")
    @GetMapping("/findReservation/{id}")
    public ResponseEntity<DrugReservation> findDrugReservation(@PathVariable UUID id, Principal user) {
        Pharmacist pharmacist = pharmacistService.findByEmail(user.getName());
        Pharmacy pharmacy = pharmacist.getPharmacy();
        DrugReservation drugReservation = drugReservationService.findDrugReservationByIdAndPharmacyId(id, pharmacy.getId());
        return  new ResponseEntity<>(drugReservation, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/getPatientReservations/{email}")
    public ResponseEntity<List<DrugReservation>> findDrugReservationByPatient(@PathVariable String email) {
        List<DrugReservation> drugReservations = this.drugReservationService.findDrugReservationByPatient(email);
        return new ResponseEntity<>(drugReservations, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/cancel/{id}")
    public ResponseEntity<DrugReservation> cancelReservation(@PathVariable UUID id) {
        DrugReservation drugRes = this.drugReservationService.cancelReservation(id);
        return  new ResponseEntity<>(drugRes, HttpStatus.OK);
    }

}
