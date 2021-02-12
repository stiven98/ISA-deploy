package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.dto.VacationDTO;
import ftn.isa.team12.pharmacy.service.VacationService;
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
@RequestMapping(value = "/api/vacation", produces = MediaType.APPLICATION_JSON_VALUE)
public class VacationController {

    @Autowired
    VacationService vacationService;



    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<VacationDTO>> getAllVacationRequestFromPharmacist(){
        return new ResponseEntity<>(vacationService.getAllFromPharmacyForPharmacist(),HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN', 'ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/accept")
    public ResponseEntity<?> acceptVacation(@RequestBody VacationDTO dto){
        Map<String, String> result = new HashMap<>();
        result.put("result","Approve vacation from employee with email: " + dto.getEmail());
        if(vacationService.approveVacation(dto) != null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        result.put("result", "Can't approve vacation from employee with email: " + dto.getEmail());
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN', 'ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/decline")
    public ResponseEntity<?> declineVacation(@RequestBody VacationDTO dto){
        Map<String, String> result = new HashMap<>();
        result.put("result","Decline vacation from employee with email: " + dto.getEmail());
        if(vacationService.declineVacation(dto) != null)
            return new ResponseEntity<>(result,HttpStatus.OK);
        result.put("result","Can't decline vacation from employee with email: " + dto.getEmail());
        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }



    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @GetMapping("/allDermatologist")
    public ResponseEntity<List<VacationDTO>> getAllVacationRequestFromDermatologist(){
        return new ResponseEntity<>(vacationService.getAllVacationRequestFromDermatologist(),HttpStatus.OK);
    }




}
