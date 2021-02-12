package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/workTime", produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkTimeController {

    @Autowired
    private WorkTimeService workTimeService;


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/all/{email}")
    public ResponseEntity<List<Date>> getWorkDayDermatologist(@PathVariable String  email){
        return new ResponseEntity<>(workTimeService.getWorkDayForDermatologist(email),HttpStatus.OK);
    }

}
