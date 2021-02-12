package ftn.isa.team12.pharmacy.controller;

import ftn.isa.team12.pharmacy.domain.drugs.Manufacturer;
import ftn.isa.team12.pharmacy.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/manufacturer", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/all")
    public ResponseEntity<List<Manufacturer>> findAll() {
        return new ResponseEntity<>(this.manufacturerService.findAll(), HttpStatus.OK);
    }
}
