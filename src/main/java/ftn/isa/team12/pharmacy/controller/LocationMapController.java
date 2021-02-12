package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.common.LocationMap;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.LocationMapDTO;
import ftn.isa.team12.pharmacy.repository.LocationMapRepository;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/locationMap", produces = MediaType.APPLICATION_JSON_VALUE)
public class LocationMapController {

    @Autowired
    LocationMapRepository locationMapRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/change")
    public ResponseEntity<?> saveLocationMap(@RequestBody LocationMapDTO dto){
        Map<String, String> result = new HashMap<>();
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        LocationMap locationMap = locationMapRepository.findByPharmacyId(pharmacy.getId());
        System.out.println(pharmacy.getName());

        if(locationMap == null){
            locationMap = new LocationMap();
            locationMap.setPharmacy(pharmacy);
            locationMap.setGeographicalWidth(dto.getGeographicalWidth());
            locationMap.setGeographicalLength(dto.getGeographicalLength());
            locationMap = locationMapRepository.save(locationMap);
        }else {
            locationMap.setGeographicalLength(dto.getGeographicalLength());
            locationMap.setGeographicalWidth(dto.getGeographicalWidth());
            locationMap = locationMapRepository.save(locationMap);
        }

        if(locationMap == null){
            result.put("result","Something wrong");
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }


        result.put("result","Successfully change location");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<LocationMapDTO> getLocationMap(@PathVariable String name){
        System.out.println(name);
        Pharmacy pharmacy = pharmacyRepository.findPharmacyByName(name);
        LocationMap locationMap = locationMapRepository.findByPharmacyId(pharmacy.getId());
        LocationMapDTO dto = new LocationMapDTO(pharmacy,locationMap);

        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


}
