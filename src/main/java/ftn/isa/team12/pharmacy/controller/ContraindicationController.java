package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.Contraindication;
import ftn.isa.team12.pharmacy.service.ContraindicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/contraindication", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContraindicationController {


    @Autowired
    private ContraindicationService contraindicationService;

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<Contraindication>> findAll() {
        return new ResponseEntity<>(this.contraindicationService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Contraindication> saveAndFlush(@RequestBody Contraindication contraindicationRequest) {
        return new ResponseEntity<>(this.contraindicationService.saveAndFlush(contraindicationRequest), HttpStatus.CREATED);
    }



}
