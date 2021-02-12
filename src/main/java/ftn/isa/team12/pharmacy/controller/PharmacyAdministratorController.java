package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/phadmin", produces = MediaType.APPLICATION_JSON_VALUE)
public class PharmacyAdministratorController {


    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private EmailSender sender;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    public ResponseEntity<List<PharmacyAdministrator>> findAll() {
        List<PharmacyAdministrator> phAdmin = pharmacyAdministratorService.findAll();

        return new ResponseEntity<List<PharmacyAdministrator>>(phAdmin,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PharmacyAdministrator> findAdminByEmail(@PathVariable String email) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(email);

        return new ResponseEntity<PharmacyAdministrator>(phAdmin,HttpStatus.OK);
    }

    @GetMapping("/pharmacyId/{pharmacyId}")
    public ResponseEntity<PharmacyAdministrator> findAdminByPharmacyId(@PathVariable UUID pharmacyId) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByPharmacyId(pharmacyId);

        return new ResponseEntity<PharmacyAdministrator>(phAdmin,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add/{id}")
    public ResponseEntity<PharmacyAdministrator> savePhAdmin(@RequestBody PharmacyAdministrator pharmacyAdministratorRequest,
                                                             @PathVariable String id){
        User existsPharmacyAdministrator = this.userService.findUserByEmail(pharmacyAdministratorRequest.getUsername());

        if (existsPharmacyAdministrator == null) {
            ResponseEntity.unprocessableEntity();

            Country country = this.countryService.saveAndFlush(pharmacyAdministratorRequest.getLocation().getCity().getCountry());
            pharmacyAdministratorRequest.getLocation().getCity().setCountry(country);

            City city = this.cityService.saveAndFlush(pharmacyAdministratorRequest.getLocation().getCity());
            pharmacyAdministratorRequest.getLocation().setCity(city);

            Location location = this.locationService.saveAndFlush(pharmacyAdministratorRequest.getLocation());
            pharmacyAdministratorRequest.setLocation(location);


            pharmacyAdministratorRequest.setAuthorities(authorityService.findByRole("ROLE_PH_ADMIN"));
            pharmacyAdministratorRequest.getAccountInfo().setActive(false);
            pharmacyAdministratorRequest.getAccountInfo().setFirstLogin(true);
            pharmacyAdministratorRequest.setPharmacy(pharmacyService.findPharmacyById(UUID.fromString(id)));
            pharmacyAdministratorRequest.setPassword(passwordEncoder.encode(pharmacyAdministratorRequest.getPassword()));
            PharmacyAdministrator pharmacyAdministrator = this.pharmacyAdministratorService.saveAndFlush(pharmacyAdministratorRequest);

            try {
                sender.sendVerificationEmail(pharmacyAdministrator.getLoginInfo().getEmail(), pharmacyAdministrator.getUserId().toString());
            } catch (Exception e) {
                return new ResponseEntity<>(pharmacyAdministratorRequest, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(pharmacyAdministrator, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("Email already exist!");
        }
    }

}
