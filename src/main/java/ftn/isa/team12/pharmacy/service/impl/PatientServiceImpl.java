package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.City;
import ftn.isa.team12.pharmacy.domain.common.Country;
import ftn.isa.team12.pharmacy.domain.common.Location;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.enums.UserCategory;
import ftn.isa.team12.pharmacy.domain.users.AccountCategory;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.User;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.PatientRepository;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private EmailSender sender;


    @Override
    public List<Patient> findAll() {
        return this.patientRepository.findAll();
    }

    @Override
    public Patient updateStatus(UUID id) {
        Patient patient =  this.patientRepository.getOne(id);
        patient.getAccountInfo().setActive(true);
        return this.patientRepository.save(patient);
    }

    @Override
    @Transactional(readOnly = false)
    public Patient save(Patient patient) {
        return this.patientRepository.save(patient);
    }


    @Override
    public Patient saveAndFlush(Patient patientRequest) {
        ResponseEntity.unprocessableEntity();

        Country country = this.countryService.saveAndFlush(patientRequest.getLocation().getCity().getCountry());
        patientRequest.getLocation().getCity().setCountry(country);

        City city = this.cityService.saveAndFlush(patientRequest.getLocation().getCity());
        patientRequest.getLocation().setCity(city);

        Location location = this.locationService.saveAndFlush(patientRequest.getLocation());
        patientRequest.setLocation(location);

        patientRequest.setAuthorities(authorityService.findByRole("ROLE_PATIENT"));

        patientRequest.getAccountInfo().setActive(false);
        patientRequest.getAccountInfo().setFirstLogin(true);
        patientRequest.setPenalties(0);
        patientRequest.getLoginInfo().setPassword(passwordEncoder.encode(patientRequest.getPassword()));
        patientRequest.setCategory(new AccountCategory());
        patientRequest.getCategory().setCategory(UserCategory.no_category);
        patientRequest.getCategory().setPoints(0);

        Patient patient = this.patientRepository.saveAndFlush(patientRequest);
        
        try {
            sender.sendVerificationEmail(patient.getLoginInfo().getEmail(), patient.getUserId().toString());
        } catch (Exception e) {
            System.out.print(e);
        }

        return patient;
    }


    @Override
    public Patient givePenalty(UUID id) {
        Patient patient = this.patientRepository.getOne(id);
        if(patient == null){
            return null;
        }
        int penalties = patient.getPenalties();
        patient.setPenalties(++penalties);
        return this.patientRepository.save(patient);
    }

    @Override
    public List<Drug> findAllergiesById(UUID id) {
        return this.patientRepository.findPatientAllergiesById(id);
    }

    @Override
    public Patient findById(UUID patientId) {
        return this.patientRepository.findById(patientId).get();
    }



    @Override
    @Transactional(readOnly = false)
    public Patient findByEmail(String email) {
        return this.patientRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = false)
    public User findUserByEmail(String email) {
        return this.patientRepository.findUserByEmail(email);
    }

    @Override
    public List<Drug> findPatientAllergies(String email) {
        return this.patientRepository.findPatientAllergies(email);
    }

    @Override
    public void addAllergy(Patient patient) {
        this.patientRepository.save(patient);
    }

    @Override
    public AccountCategory findAccountCategory(String email) {
        return this.patientRepository.findAccountCategory(email);
    }

    @Override
    public Integer findPenalty(String email) {
        return this.patientRepository.findPenalty(email);
    }


}
