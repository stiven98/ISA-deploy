package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.common.Complaint;
import ftn.isa.team12.pharmacy.dto.AnswerDTO;
import ftn.isa.team12.pharmacy.dto.ComplaintDTO;
import ftn.isa.team12.pharmacy.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/complaint", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComplaintController {


    @Autowired
    private ComplaintService complaintService;

    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @PostMapping("/add")
    public ResponseEntity<Complaint> saveAndFlush(@RequestBody ComplaintDTO complaintRequest){

        Complaint complaint = this.complaintService.saveAndFlush(complaintRequest);

        return new ResponseEntity<>(complaint, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<Complaint>> findAll(){
        System.out.print("");
        return new ResponseEntity<>(this.complaintService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/sendAnswer")
    public ResponseEntity<HttpStatus> sendAnswer(@RequestBody AnswerDTO answerRequest) {
        System.out.print(answerRequest);

        this.complaintService.sendAnswer(answerRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
