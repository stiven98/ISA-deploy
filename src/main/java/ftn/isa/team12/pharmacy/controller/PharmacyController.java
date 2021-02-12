package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;

import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.PharmacyChangeDTO;
import ftn.isa.team12.pharmacy.dto.PharmacySearchDTO;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.service.CityService;
import ftn.isa.team12.pharmacy.service.CountryService;
import ftn.isa.team12.pharmacy.service.LocationService;
import ftn.isa.team12.pharmacy.service.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/pharmacy",  produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyController {

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;


    @GetMapping("/all")
    public ResponseEntity<List<Pharmacy>> findAll() {
        double price;
        double exam;
        List<Pharmacy> pharmacies = pharmacyService.findAll();
        for (Pharmacy p : pharmacies) {
            if (!p.getExaminationPriceList().isEmpty()) {
                for (ExaminationPrice ep : p.getExaminationPriceList()) {
                    if (ep.getExaminationType().equals(ExaminationType.pharmacistConsultations) && (new Date().before(ep.getDateOfValidity().getEndDate()))) {
                        price = ep.getPrice();
                        p.setConsulationPrice(price);
                    } else if (ep.getExaminationType().equals(ExaminationType.dermatologistExamination) && (new Date().before(ep.getDateOfValidity().getEndDate()))) {
                        exam = ep.getPrice();
                        p.setExaminationPrice(exam);
                    }
                }
            } else {
                p.setExaminationPrice(0.0);
                p.setConsulationPrice(0.0);
            }
            this.pharmacyService.save(p);
        }
        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Pharmacy> findPharmacyById(@PathVariable UUID id){
        Pharmacy pharmacy = pharmacyService.findPharmacyById(id);
        return new ResponseEntity<Pharmacy>(pharmacy,HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Pharmacy> findPharmacyByName(@PathVariable String name){
        Pharmacy pharmacy = pharmacyService.findPharmacyByName(name);
        return new ResponseEntity<Pharmacy>(pharmacy,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Pharmacy> savePharmacy(@RequestBody Pharmacy pharmacyRequest) {

        Pharmacy existsPharmacy = pharmacyService.findPharmacyByName(pharmacyRequest.getName());

        if(existsPharmacy == null ) {
            ResponseEntity.unprocessableEntity();
            Country country = this.countryService.saveAndFlush(pharmacyRequest.getLocation().getCity().getCountry());
            pharmacyRequest.getLocation().getCity().setCountry(country);

            City city = this.cityService.saveAndFlush(pharmacyRequest.getLocation().getCity());
            pharmacyRequest.getLocation().setCity(city);

            Location location = this.locationService.saveAndFlush(pharmacyRequest.getLocation());
            pharmacyRequest.setLocation(location);

            Pharmacy pharmacy = this.pharmacyService.saveAndFlush(pharmacyRequest);

            return new ResponseEntity<>(pharmacy, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("Pharmacy with name already exist!");
        }

    }
    @PostMapping("/search")
    public ResponseEntity<List<Pharmacy>> searchPharmacies(@RequestBody PharmacySearchDTO dto) {
        List<Pharmacy> pharmacies = this.pharmacyService.findAll();
        List<Pharmacy> searched =  this.pharmacyService.searchPharmacies(pharmacies, dto);
        return new ResponseEntity<>(searched, HttpStatus.OK);
    }




    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/getPharmacy")
    public ResponseEntity<?> getchangePharmacy() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        PharmacyChangeDTO p = new PharmacyChangeDTO(pharmacy);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/change")
    public ResponseEntity<?> changePharmacy(@RequestBody PharmacyChangeDTO dto) {
        Map<String, String> result = new HashMap<>();
        if(pharmacyService.change(dto) == null){
            result.put("result","Can't change pharmacy");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully change pharmacy");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


}
