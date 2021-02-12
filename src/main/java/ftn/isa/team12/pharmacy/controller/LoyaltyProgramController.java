package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import ftn.isa.team12.pharmacy.service.LoyaltyProgramService;
import ftn.isa.team12.pharmacy.service.impl.LoyaltyProgramServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/loyaltyProgram", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoyaltyProgramController {

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @GetMapping("/get")
    public ResponseEntity<LoyaltyProgram> getLoyaltyProgram() {
        return new ResponseEntity<>(this.loyaltyProgramService.getLoyaltyProgram(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/update")
    public ResponseEntity<LoyaltyProgram> updateLoyaltyProgram(@RequestBody LoyaltyProgram loyaltyProgramRequest) {
        return new ResponseEntity<>(this.loyaltyProgramService.saveAndFlush(loyaltyProgramRequest), HttpStatus.OK);
    }




}
