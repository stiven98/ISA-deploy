package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.users.SystemAdministrator;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/systemAdministrator", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemAdministratorController {

    @Autowired
    private SystemAdministratorService systemAdministratorService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailSender sender;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PreAuthorize("hasAnyRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<SystemAdministrator> saveSystemAdministrator(@RequestBody SystemAdministrator systemAdministratorRequest){
        User user = userService.findUserByEmail(systemAdministratorRequest.getLoginInfo().getEmail());

        if (user == null) {

            ResponseEntity.unprocessableEntity();

            Country country = this.countryService.saveAndFlush(systemAdministratorRequest.getLocation().getCity().getCountry());
            systemAdministratorRequest.getLocation().getCity().setCountry(country);

            City city = this.cityService.saveAndFlush(systemAdministratorRequest.getLocation().getCity());
            systemAdministratorRequest.getLocation().setCity(city);

            Location location = this.locationService.saveAndFlush(systemAdministratorRequest.getLocation());
            systemAdministratorRequest.setLocation(location);

            systemAdministratorRequest.setAuthorities(authorityService.findByRole("ROLE_SYSTEM_ADMINISTRATOR"));
            systemAdministratorRequest.getAccountInfo().setFirstLogin(true);
            systemAdministratorRequest.getAccountInfo().setActive(false);
            systemAdministratorRequest.setPassword(passwordEncoder.encode(systemAdministratorRequest.getPassword()));
            SystemAdministrator systemAdministrator = this.systemAdministratorService.saveAndFlush(systemAdministratorRequest);

            try {
                sender.sendVerificationEmail(systemAdministrator.getLoginInfo().getEmail(), systemAdministrator.getUserId().toString());
            } catch (Exception e) {
                return new ResponseEntity<>(systemAdministratorRequest, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(systemAdministrator, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("Email already exist!");
        }
    }
}
