package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/city", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {


    @Autowired
    private CityService cityService;


    @GetMapping("/all")
    public ResponseEntity<List<City>> findAll() {
        return new ResponseEntity<List<City>>(this.cityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/byCountry")
    @ResponseBody
    public ResponseEntity<List<City>> findByCountry(@RequestParam String name){
        return new ResponseEntity<>(this.cityService.findByCountry(name), HttpStatus.OK);
    }





}
