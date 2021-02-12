package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.dto.DeleteEmployeeDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesCreateDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesSearchDTO;
import ftn.isa.team12.pharmacy.service.PharmacistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/pharmacist", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacistController {

    @Autowired
    private PharmacistService pharmacistService;

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')") // Dodati ostale role
    @GetMapping("/all")
    public ResponseEntity<List<EmployeesDTO>> findAll() {
        return new ResponseEntity<>(pharmacistService.findAllPharmacist(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')") // Dodati ostale role
    @GetMapping("/all/{email}")
    public ResponseEntity<List<EmployeesDTO>> findAllDermatologistInPharmacyByAdmin(@PathVariable String email) {
        return new ResponseEntity<>(pharmacistService.findAllByPhADmin(email), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PH_ADMIN')") // Add other role
    @PostMapping("/searchPharmacist")
    public ResponseEntity<List<EmployeesDTO>> searchDermatologist(@RequestBody EmployeesSearchDTO dto) {
        return new ResponseEntity<>(pharmacistService.searchPharmacist(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Pharmacist> createPharmacist(@RequestBody EmployeesCreateDTO dto) {
        Pharmacist pharmacist = pharmacistService.createPharmacist(dto);
        if(pharmacist!= null)
            return new ResponseEntity<>(pharmacist, HttpStatus.OK);
        return new ResponseEntity<>(pharmacist, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> deletePharmacy(@RequestBody DeleteEmployeeDTO dto) {
        Map<String, String> result = new HashMap<>();
        if(pharmacistService.deletePharmacist(dto)) {
            result.put("result","Successfully delete pharmacist with email: " + dto.getEmployeeEmail());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("result","Can't delete pharmacist with email: " + dto.getEmployeeEmail());
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);

    }




}
