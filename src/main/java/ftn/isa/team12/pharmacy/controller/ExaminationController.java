package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationStatus;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.ExaminationDrugQuantityDTO;
import ftn.isa.team12.pharmacy.dto.ExaminationScheduleMedStuffDTO;
import ftn.isa.team12.pharmacy.dto.ScheduleExaminationDTO;
import ftn.isa.team12.pharmacy.dto.*;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.ExaminationRepository;
import ftn.isa.team12.pharmacy.repository.WorkTimeRepository;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.*;


@RestController
@RequestMapping(value = "/api/examination", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExaminationController {

    @Autowired
    ExaminationService examinationService;

    @Autowired
    MedicalStuffService medicalStuffService;

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    WorkTimeRepository workTimeRepository;

    @Autowired
    ExaminationRepository examinationRepository;


    @Autowired
    PatientService patientService;

    @Autowired
    PharmacistService pharmacistService;

    @Autowired
    DrugInPharmacyService drugInPharmacyService;

    @Autowired
    LoyaltyProgramService loyaltyProgramService;

    @Autowired
    DrugService drugService;

    @Autowired
    EmailSender sender;


    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/allByEmployee")
    public ResponseEntity<?> findAllByEmployee(Principal user) {
        Map<String, String> result = new HashMap<>();
        MedicalStuff medicalStuff = medicalStuffService.findByEmail(user.getName());
        List<Examination> examinations = examinationService.findAllByEmployee(medicalStuff);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @PostMapping("/scheduleNewMedStuff")
    public ResponseEntity<?> scheduleNewMedStuff(@RequestBody ExaminationScheduleMedStuffDTO dto){
        Map<String, String> res = new HashMap<>();
        Examination examination = examinationService.scheduleNewMedStuff(dto);
        if(examination == null){
            res.put("result", "You can't schedule examination in desired time!");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        res.put("result", "Examination successfully scheduled!");
        Patient patient = examination.getPatient();
        Pharmacy pharmacy = examination.getPharmacy();
        sender.sendSchedulingInfo(patient.getUsername(), examination.getDateOfExamination().toString(), examination.getTimeOfExamination().toString(), pharmacy.getName());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @PostMapping("/scheduleExistingMedStuff")
    public ResponseEntity<?> scheduleExistingMedStuff(@RequestBody ExaminationScheduleExistingMedStuffDTO dto){
        Map<String, String> res = new HashMap<>();
        Examination examination = examinationService.scheduleExistingMedStuff(dto);
        if(examination == null){
            res.put("result", "You can't schedule examination in desired time!");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        res.put("result", "Examination successfully scheduled!");
        Patient patient = examination.getPatient();
        Pharmacy pharmacy = examination.getPharmacy();
        sender.sendSchedulingInfo(patient.getUsername(), examination.getDateOfExamination().toString(), examination.getTimeOfExamination().toString(), pharmacy.getName());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @PostMapping("/submitExamination")
    public ResponseEntity<?> scheduleExistingMedStuff(@RequestBody ExaminationSubmissionDTO dto){
        Map<String, String> res = new HashMap<>();
        Examination examination = examinationService.submitExaminationData(dto);
        if(examination == null){
            res.put("result", "You can't submit your data!");
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        res.put("result", "Examination data successfully submitted!");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/getCurrentExamination/{id}")
    public ResponseEntity<?> getCurrentExamination(@PathVariable UUID id, Principal user) {
        Map<String, String> result = new HashMap<>();
        MedicalStuff medicalStuff = medicalStuffService.findByEmail(user.getName());
        Examination examination = examinationService.findCurrentById(id);

        if(examination == null){
            result.put("result", "The examination you are trying to access is passed or isn't started yet!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if(examination.getEmployee().getUserId() != medicalStuff.getUserId()){
            result.put("result", "You are trying to access to someone else's examination!");
            return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(examination, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @PostMapping("/givePenalty/{id}") //POST instead PUT because CORS policy doesn't allow PUT from some reason
    public ResponseEntity<?> givePenalty(@PathVariable UUID id, PenaltyReq req) {
        Map<String, String> result = new HashMap<>();
        Patient patient = patientService.givePenalty(id);
        if(patient == null){
            result.put("result", "The user with specified ID doesn't exist!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result", "Successfully given penalty!");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/drugAvailability")
    public ResponseEntity<?> getDrugAvailability(ExaminationDrugQuantityDTO drugQuantityDTO) {
        Map<String, Integer> result = new HashMap<>();
        Map<String, List<Drug>> resultAlternative = new HashMap<>();
        Drug drug = drugService.findById(drugQuantityDTO.getDrugId());
        String drugName = drug.getName();
        Pharmacy pharmacy = pharmacyService.findPharmacyById(drugQuantityDTO.getPharmacyId());
        Set<PharmacyAdministrator> admins = pharmacy.getPhAdmins();
        int quantity = this.drugInPharmacyService.findDrugQuantity(drugQuantityDTO.getDrugId(), drugQuantityDTO.getPharmacyId());
        if(quantity <= 0){
            for(PharmacyAdministrator phAdmin : admins){
                sender.sendDrugQuantityNotificationToPhAdmin(phAdmin.getUsername(), drugName);
            }
            List<Drug> retVal = new ArrayList<>();
            Set<Drug> substitute = drug.getSubstituteDrugs();
            List<Drug> allergies = patientService.findAllergiesById(drugQuantityDTO.getPatientId());
            for(Drug subDrug : substitute){
                boolean isInAllergies = false;
                for(Drug allergy : allergies){
                    if(subDrug.getDrugId() == allergy.getDrugId()){
                        isInAllergies = true;
                        break;
                    }
                }
                if(!isInAllergies){
                    Integer quant = this.drugInPharmacyService.findDrugQuantity(subDrug.getDrugId(), drugQuantityDTO.getPharmacyId());
                    if(quant != null){
                        retVal.add(subDrug);
                    }
                }
            }
            resultAlternative.put("result", retVal);
            return new ResponseEntity<>(resultAlternative, HttpStatus.OK);
        }

        result.put("result", quantity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/allByEmployeeAndPharmacy/{id}")
    public ResponseEntity<?> findAllByEmployeeAndPharmacy(@PathVariable UUID id, Principal user) {
        Map<String, String> result = new HashMap<>();
        MedicalStuff medicalStuff = medicalStuffService.findByEmail(user.getName());
        Pharmacy pharmacy = pharmacyService.findPharmacyById(id);
        if(pharmacy == null){
            result.put("result", "Wrong pharmacy id!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        List<Examination> examinations = examinationService.findAllByEmployeeAndPharmacy(medicalStuff, pharmacy);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/allByEmployeeAndPatient/{id}")
    public ResponseEntity<?> findAllByEmployeeAndPatient(@PathVariable UUID id, Principal user) {
        Map<String, String> result = new HashMap<>();
        MedicalStuff medicalStuff = medicalStuffService.findByEmail(user.getName());
        Patient patient = patientService.findById(id);
        if(patient == null){
            result.put("result", "Wrong pharmacy id!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        List<Examination> examinations = examinationService.findAllByEmployeeAndPatient(medicalStuff, patient);
        List<ExaminationRetValDTO> dtos = new ArrayList<>();
        examinations.forEach(examination -> dtos.add(new ExaminationRetValDTO(examination)));

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_DERMATOLOGIST', 'ROLE_PHARMACIST')")
    @GetMapping("/allFreeByEmployeeAndPharmacy/{id}")
    public ResponseEntity<?> findAllFreeByEmployeeAndPharmacy(@PathVariable UUID id, Principal user) {
        Map<String, String> result = new HashMap<>();
        MedicalStuff medicalStuff = medicalStuffService.findByEmail(user.getName());
        Pharmacy pharmacy = pharmacyService.findPharmacyById(id);
        if(pharmacy == null){
            result.put("result", "Wrong pharmacy id!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        List<Examination> examinations = examinationService.findAllFreeByEmployeeAndPharmacy(medicalStuff, pharmacy);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/createExamination")
    public ResponseEntity<?> createExaminationForDermatologist(@RequestBody ExaminationCreateDTO dto){
        Map<String, String> result = new HashMap<>();
        Examination examination = examinationService.addExaminationForDermatologist(dto);
        if(examination == null) {
            result.put("result", "Can't create examination");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully created examination");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/busyTime")
    public ResponseEntity<BusyDateDTO> busyTime(@RequestBody TimeDTO dto){
        return new ResponseEntity<>(examinationService.busyTime(dto.getEmail(),dto.getDate()),HttpStatus.OK);
    }


    static class PenaltyReq{
        private int penalty;
    };
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/pharmaciesWithFreeTerms/")
    public ResponseEntity<List<Pharmacy>> findPharmaciesWithFreeTerms(@RequestBody FreeTermDTO dto)  {
        List<Examination> examinations = this.examinationService.findPharmaciesWithFreeTerm(dto.getDate(),dto.getTime());
        List<Pharmacy> pharmacies = new ArrayList<>();
        List<Pharmacist> pharmacists = this.pharmacistService.findAll();
        for(Examination ex : examinations) {
            if(pharmacists.contains(ex.getEmployee())) {
               if(!pharmacies.contains(ex.getPharmacy())) {
                   pharmacies.add(ex.getPharmacy());
               }
            }
        }

        return new ResponseEntity<>(pharmacies, HttpStatus.OK);
    }
    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/scheduleNew/")
    public ResponseEntity<Examination> scheduleExamination(@RequestBody ScheduleExaminationDTO dto)  {
        Patient patient = this.patientService.findByEmail(dto.getPatientEmail());
        LoyaltyProgram lp = this.loyaltyProgramService.getLoyaltyProgram();
        int discount = lp.getDiscountByCategory(patient.getCategory().getCategory());
        List<Examination> examinations = this.examinationService.findAllByPatient(patient);
        Examination examination = this.examinationService.findByEmployeePharmacyTimeDate(dto.getUserId(), dto.getPharmacyName(), dto.getDate(), dto.getTime());
        MedicalStuff pharmacist = medicalStuffService.findById(examination.getEmployee().getUserId());
        for(Examination ex : examinations) {
            if(ex.getDateOfExamination().equals(examination.getDateOfExamination()) && ex.getExaminationType() == ExaminationType.pharmacistConsultations){
                throw new IllegalArgumentException("You cant schedule more than 1 consultations for same day");
            }
        }
        if(patient.getPenalties() > 2) {
            throw new IllegalArgumentException("You have 3 or more penalties and you cant schedule consultations");
        }
        examination.setPatient(patient);
        examination.setExaminationType(ExaminationType.pharmacistConsultations);
        examination.setExaminationStatus(ExaminationStatus.scheduled);
        double priceOfExamination = examination.getExaminationPrice().getPrice();
        double newPrice =  (1.0 * discount / 100) * priceOfExamination;
        BigDecimal bd1 = new BigDecimal(newPrice).setScale(2, RoundingMode.HALF_UP);
        double nr = bd1.doubleValue();
        examination.setDiscount(nr);
        this.examinationService.save(examination);
        medicalStuffService.save(pharmacist);
        try {
            sender.sendPharmacistConsultationsMail(examination.getExaminationId(),dto.getPatientEmail(),dto.getPharmacyName(),examination.getDateOfExamination().toString(),
                   pharmacist.getAccountInfo().getName(), examination.getEmployee().getAccountInfo().getLastName(), examination.getTimeOfExamination().toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(examination, HttpStatus.OK);
    }

    @Transactional(readOnly = false)
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/newExamination/")
    public ResponseEntity<Examination> scheduleExamination(@RequestBody DermatologistExamScheduleDTO dto)  {
        Patient patient = this.patientService.findByEmail(dto.getPatientEmail());
        LoyaltyProgram lp = this.loyaltyProgramService.getLoyaltyProgram();
        int discount = lp.getDiscountByCategory(patient.getCategory().getCategory());
        List<Examination> examinations = this.examinationService.findAllByPatient(patient);
        Examination examination = this.examinationService.findById(dto.getExaminationId());
        for(Examination ex : examinations) {
            if(ex.getDateOfExamination().equals(examination.getDateOfExamination()) && ex.getExaminationType() == ExaminationType.dermatologistExamination){
                throw new IllegalArgumentException("You cant schedule more than 1 examination for same day");
            }
        }
        if(patient.getPenalties() > 2) {
            throw new IllegalArgumentException("You have 3 or more penalties and you cant schedule examination");
        }
        examination.setPatient(patient);
        examination.setExaminationType(ExaminationType.dermatologistExamination);
        examination.setExaminationStatus(ExaminationStatus.scheduled);
        double priceOfExamination = examination.getExaminationPrice().getPrice();
        double newPrice =  (1.0 * discount / 100) * priceOfExamination;
        BigDecimal bd1 = new BigDecimal(newPrice).setScale(2, RoundingMode.HALF_UP);
        double nr = bd1.doubleValue();
        examination.setDiscount(nr);
        this.examinationService.save(examination);
        try {
            sender.sendDermatologistExaminationMail(examination);
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(examination, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/findAvailablePharmacists/{pharmacyName}")
    public ResponseEntity<List<Pharmacist>> findAvailablePharmacists(@PathVariable String pharmacyName)  {
        List<MedicalStuff> medicalStuffs = this.examinationService.findAvailableByPharmacy(pharmacyName);
        List<Pharmacist> pharmacists = new ArrayList<>();
        List<Pharmacist> phamacists1 = this.pharmacistService.findAll();
        for (MedicalStuff ms : medicalStuffs) {
            if(phamacists1.contains(ms)) {
                pharmacists.add((Pharmacist) ms);
            }
        }

        return new ResponseEntity<>(pharmacists, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/getPatientConsulatitons/{patientEmail}")
    public ResponseEntity<List<Examination>> findPatientConsulatitons(@PathVariable String patientEmail)  {
        Patient patient = this.patientService.findByEmail(patientEmail);
        List<Examination> consultations = this.examinationService.findPharmacistConsultationsForPatient(patient.getUserId());
        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/getPatientExaminations/{patientEmail}")
    public ResponseEntity<List<Examination>> findPatientExaminations(@PathVariable String patientEmail)  {
        Patient patient = this.patientService.findByEmail(patientEmail);
        List<Examination> examinations = this.examinationService.findDermatologistExaminationsForPatient(patient.getUserId());
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/getAvailableDermByPharmacy/{pharmacyName}")
    public ResponseEntity<List<Examination>> getAvailableDermatologistByPharmacy(@PathVariable String pharmacyName)  {
        List<Examination> examinations = this.examinationService.findFreeTermsForDermatologistsByPhamracy(pharmacyName);
        return new ResponseEntity<>(examinations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/cancelConsultations/{id}")
    public ResponseEntity<Examination> cancelConsultations(@PathVariable UUID id) {
        Calendar calendar = Calendar.getInstance();
        Examination ex = this.examinationService.findById(id);
        Date deadlineForCancel = ex.getDateOfExamination();
        calendar.setTime(deadlineForCancel);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date dayBeforeDeadline = calendar.getTime();
        if(new Date().before(dayBeforeDeadline)) {
            ex.setPatient(null);
            ex.setExaminationStatus(ExaminationStatus.cancelled);
            ex.setDiscount(0);
            this.examinationService.save(ex);
        }
        else {
            throw new IllegalArgumentException("You cant cancel consultations in 24h before consultations");
        }
        return  new ResponseEntity<>(ex, HttpStatus.OK);
    }
}
