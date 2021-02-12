package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.dto.GetDrugQuantityDTO;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugInPharmacyChangesDTO;
import ftn.isa.team12.pharmacy.service.DrugInPharmacyService;
import ftn.isa.team12.pharmacy.service.PharmacyAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/drugInPharmacy", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugInPharmacyController {

    @Autowired
    private DrugInPharmacyService drugInPharmacyService;

    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    @GetMapping("/id/{id}")
    public List<Drug> findDrugInPharmacyById(@PathVariable UUID id) throws AccessDeniedException{
        return  drugInPharmacyService.findDrugInPharmacyById(id);
    }

    @GetMapping("/pharmacies/{id}")
    public List<Pharmacy> findPharmaciesWithDrug(@PathVariable UUID id) throws AccessDeniedException{
        return  drugInPharmacyService.findPharmaciesWithDrug(id);
    }

   @PreAuthorize("hasAnyRole('ROLE_PATIENT')") // Dodati ostale role
    @PostMapping("/getQuantity")
    public ResponseEntity<?> getQuantity(@RequestBody GetDrugQuantityDTO drugQuantityDTO) {
      int quantity =   this.drugInPharmacyService.findDrugQuantity(drugQuantityDTO.getDrugId(), drugQuantityDTO.getPharmacyId());
        return new ResponseEntity<>(quantity, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/all/{email}")
    public ResponseEntity<List<DrugForOrderDTO>> findPharmaciesWithDrug(@PathVariable String email) throws AccessDeniedException{
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(email);
        return  new ResponseEntity<>(drugInPharmacyService.findAllDrugInPharmacyByid(phAdmin.getPharmacy().getId()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addDrugInPharmacy(@RequestBody DrugInPharmacyChangesDTO dto) {
        drugInPharmacyService.addDrugInPharmacy(dto);
        return new ResponseEntity<>("Succesfuly created", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/remove")
    public ResponseEntity<?> removeDrugInPharmacy(@RequestBody DrugInPharmacyChangesDTO dto) {
        drugInPharmacyService.removeDrugInPharmacy(dto);
        return new ResponseEntity<>("Succesfuly created", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> updateDrugInPharmacy(@RequestBody DrugInPharmacyChangesDTO dto) {
        drugInPharmacyService.updateDrugInPharmacy(dto);
        return new ResponseEntity<>("Succesfuly created", HttpStatus.OK);
    }
}
