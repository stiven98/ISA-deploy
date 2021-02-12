package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.*;
import ftn.isa.team12.pharmacy.dto.PatientDTO;
import ftn.isa.team12.pharmacy.dto.PatientExaminationDTO;
import ftn.isa.team12.pharmacy.service.*;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.users.AccountCategory;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.dto.AddAllergyDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.service.CityService;
import ftn.isa.team12.pharmacy.service.CountryService;
import ftn.isa.team12.pharmacy.service.LocationService;
import ftn.isa.team12.pharmacy.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.util.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserService userService;

    @Autowired
    private MedicalStuffService medicalStuffService;

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
    private PharmacyService pharmacyService;

    @PreAuthorize("hasRole('ROLE_PH_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<PatientDTO>> findAll() {
        List<Patient> patients = patientService.findAll();
        List<PatientDTO> dto = new ArrayList<>();
        for (Patient p : patients) {
            dto.add(new PatientDTO(p));
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST','ROLE_PHARMACIST')")
    @GetMapping("/getPatientsByMedicalStuffId")
    public ResponseEntity<?> findPatientsByMedicalStuff() {
        Map<String, String> result = new HashMap<>();
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String email = currentUser.getName();
        User user = userService.findUserByEmail(email);
        if (user == null) {
            result.put("result", "Please log in first!");
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        MedicalStuff medicalStuff = medicalStuffService.findById(user.getUserId());
        Set<PatientExaminationDTO> patients = medicalStuffService.findPatientsByMedicalStuff(medicalStuff);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<Patient> savePatient(@RequestBody Patient patientRequest,
                                               HttpServletResponse response) {
        User user = userService.findUserByEmail(patientRequest.getLoginInfo().getEmail());
        if (user == null) {
            Patient patient = this.patientService.saveAndFlush(patientRequest);
            return new ResponseEntity<>(patient, HttpStatus.CREATED);
        } else {
            throw new IllegalArgumentException("Email already exist!");
        }
    }


    @GetMapping("/allergies/{email}")
    public ResponseEntity<List<Drug>> findPatientAllergies(@PathVariable String email) {
        List<Drug> allergies = patientService.findPatientAllergies(email);
        return new ResponseEntity<List<Drug>>(allergies, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')") // Dodati ostale role
    @PostMapping("/addAllergy")
    public ResponseEntity<?> addAllergy(@RequestBody AddAllergyDTO addAllergy) {
        Patient patient = patientService.findByEmail(addAllergy.getEmail());
        Drug allergy = drugService.findDrugByName(addAllergy.getDrugName());
        patient.getAllergies().add(allergy);
        patientService.addAllergy(patient);
        return new ResponseEntity<>("Successfully added allergy", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/accountCategory/{email}")
    public ResponseEntity<AccountCategory> findAccountCategory(@PathVariable String email) {
        AccountCategory category = patientService.findAccountCategory(email);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/penalty/{email}")
    public ResponseEntity<Integer> findPenalty(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if(dayOfMonth == 1) {
            patient.setPenalties(0);
            this.patientService.save(patient);
        }
        Integer penalty = patientService.findPenalty(email);
        return new ResponseEntity<>(penalty, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/subscribedPharmacies")
    public ResponseEntity<Set<Pharmacy>> findSubscribedPharmacy(@RequestBody String email) {
        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with email doesn't exist!");
        }
        return new ResponseEntity<>(patient.getSubscribedPharmacies(), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/unsubscribePharmacy/{email}/{id}")
    public ResponseEntity<Patient> unsubscribePharmacy(@PathVariable String email, @PathVariable String id) {
        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with email doesn't exist!");
        }

        Pharmacy pharmacy = pharmacyService.findPharmacyById(UUID.fromString(id));
        if (pharmacy == null) {
            throw new IllegalArgumentException("Pharmacy doesn't exists!");
        }

        patient.getSubscribedPharmacies().remove(pharmacy);
        patient = patientService.save(patient);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/isSubscribed/{email}/{id}")
    public ResponseEntity<Boolean> isSubscribed(@PathVariable String email, @PathVariable String id) {
        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with email doesn't exist!");
        }

        Pharmacy pharmacy = pharmacyService.findPharmacyById(UUID.fromString(id));
        if (pharmacy == null) {
            throw new IllegalArgumentException("Pharmacy doesn't exists!");
        }

        patient.getSubscribedPharmacies().contains(pharmacy);
        return new ResponseEntity<>(patient.getSubscribedPharmacies().contains(pharmacy), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/subscribe/{email}/{id}")
    public ResponseEntity<Patient> subscribePharmacy(@PathVariable String email, @PathVariable String id) {
        Patient patient = patientService.findByEmail(email);
        if (patient == null) {
            throw new IllegalArgumentException("Patient with email doesn't exist!");
        }

        Pharmacy pharmacy = pharmacyService.findPharmacyById(UUID.fromString(id));
        if (pharmacy == null) {
            throw new IllegalArgumentException("Pharmacy doesn't exists!");
        }

        patient.getSubscribedPharmacies().add(pharmacy);
        patient = patientService.save(patient);

        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

}
