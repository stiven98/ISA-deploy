package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.Supplier;
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

@RestController
@RequestMapping(value = "/api/supplier", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserService userService;


    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private EmailSender sender;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplierRequest) {

        User user = userService.findUserByEmail(supplierRequest.getLoginInfo().getEmail());

        if (user == null) {
            ResponseEntity.unprocessableEntity();

            Country country = this.countryService.saveAndFlush(supplierRequest.getLocation().getCity().getCountry());
            supplierRequest.getLocation().getCity().setCountry(country);

            City city = this.cityService.saveAndFlush(supplierRequest.getLocation().getCity());
            supplierRequest.getLocation().setCity(city);

            Location location = this.locationService.saveAndFlush(supplierRequest.getLocation());
            supplierRequest.setLocation(location);

            supplierRequest.setPassword(passwordEncoder.encode(supplierRequest.getPassword()));
            supplierRequest.setAuthorities(authorityService.findByRole("ROLE_SUPPLIER"));
            Supplier supplier = this.supplierService.saveAndFlush(supplierRequest);

            try {
                sender.sendVerificationEmail(supplier.getLoginInfo().getEmail(), supplier.getUserId().toString());
            } catch (Exception e) {
                return new ResponseEntity<>(supplierRequest, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(supplier, HttpStatus.CREATED);

        } else {
            throw new IllegalArgumentException("Email already exist!");
        }

    }


}
