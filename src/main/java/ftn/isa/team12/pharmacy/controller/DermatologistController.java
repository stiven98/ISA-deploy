package ftn.isa.team12.pharmacy.controller;

import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.dto.DeleteEmployeeDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesCreateDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesSearchDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/dermatologist", produces = MediaType.APPLICATION_JSON_VALUE)
public class DermatologistController {

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DermatologistService dermatologistService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private EmailSender sender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Dermatologist> saveDermatologist(@RequestBody Dermatologist dermatologistRequest) {

        User user = userService.findUserByEmail(dermatologistRequest.getUsername());

        if (user == null) {

            ResponseEntity.unprocessableEntity();

            Country country = this.countryService.saveAndFlush(dermatologistRequest.getLocation().getCity().getCountry());
            dermatologistRequest.getLocation().getCity().setCountry(country);

            City city = this.cityService.saveAndFlush(dermatologistRequest.getLocation().getCity());
            dermatologistRequest.getLocation().setCity(city);

            Location location = this.locationService.saveAndFlush(dermatologistRequest.getLocation());
            dermatologistRequest.setLocation(location);

            dermatologistRequest.setAuthorities(authorityService.findByRole("ROLE_DERMATOLOGIST"));
            dermatologistRequest.getAccountInfo().setActive(false);
            dermatologistRequest.getAccountInfo().setFirstLogin(true);
            dermatologistRequest.setPassword(passwordEncoder.encode(dermatologistRequest.getPassword()));
            Dermatologist dermatologist = this.dermatologistService.saveAndFlush(dermatologistRequest);

            try {
                sender.sendVerificationEmail(dermatologist.getLoginInfo().getEmail(), dermatologist.getUserId().toString());
            } catch (Exception e) {
                return new ResponseEntity<>(dermatologistRequest, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<Dermatologist>(dermatologist, HttpStatus.CREATED);
        } else {

            throw new IllegalArgumentException("Email already exist!");

        }


    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeesDTO>> findAll() {
        return new ResponseEntity<>(dermatologistService.findAllDermatologist(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/allFromOtherPharmacy")
    public ResponseEntity<List<EmployeesDTO>> findAllFromOtherPharmacy() {
        return new ResponseEntity<>(dermatologistService.findAllFromOtherPharmacy(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/all/{email}")
    public ResponseEntity<List<EmployeesDTO>> findAllDermatologistInPharmacyByAdmin(@PathVariable String email) {
        return new ResponseEntity<>(dermatologistService.findAllByPhAdmin(email), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PH_ADMIN')")
    @PostMapping("/searchDermatologist")
    public ResponseEntity<List<EmployeesDTO>> searchDermatologist(@RequestBody EmployeesSearchDTO dto) {
        return new ResponseEntity<>(dermatologistService.searchDermatologist(dto), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteDermatologist(@RequestBody DeleteEmployeeDTO dto) {
        Map<String, String> result = new HashMap<>();
        if(dermatologistService.deleteDermatologist(dto)) {
            result.put("result","Successfully delete dermatologist with email: " + dto.getEmployeeEmail());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("result","Can't delete dermatologist with email: " + dto.getEmployeeEmail());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/addInPharmacy")
    public ResponseEntity<?> addDermatologistInPharmacy(@RequestBody EmployeesCreateDTO dto){
        Map<String, String> result = new HashMap<>();
        if(dermatologistService.addDermatologist(dto) == null){
            result.put("result","Can't add dermatologist with email: " + dto.getEmailPhAdmin());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully add dermatologist with email: " + dto.getEmailPhAdmin());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
