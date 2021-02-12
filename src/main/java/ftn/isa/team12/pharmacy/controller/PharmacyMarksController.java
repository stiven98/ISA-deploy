package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.marks.PharmacyMarks;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.PharmacyMarkChangeDTO;
import ftn.isa.team12.pharmacy.dto.PharmacyMarkDTO;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/pharmacyMarks", produces = MediaType.APPLICATION_JSON_VALUE )
public class PharmacyMarksController {

    @Autowired
    private PharmacyMarksService pharmacyMarksService;

    @Autowired
    private DrugReservationService drugReservationService;

    @Autowired
    private ERecipeService eRecipeService;

    @Autowired
    private ExaminationService examinationService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PatientService patientService;


    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/pharmaciesForPatient/{email}")
    public ResponseEntity<List<Pharmacy>> getPharmaciesForPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<Pharmacy> pharmacies = this.drugReservationService.findPharmaciesWherePatientReservedDrugs(email);
        List<Pharmacy> pharmacies1 = this.eRecipeService.findPharmaciesWherePatientHasERecipe(email);
        List<Pharmacy> pharmacies2 = this.examinationService.findAllPharmaciesWherePatientHadExamination(patient.getUserId());
        List<Pharmacy> pharmacyList = new ArrayList<>();

        for(Pharmacy pharmacy : pharmacies) {
            if(!pharmacyList.contains(pharmacy)) {
                pharmacyList.add(pharmacy);
            }
        }
        for(Pharmacy pharmacy : pharmacies1) {
            if(!pharmacyList.contains(pharmacy)){
                pharmacyList.add(pharmacy);
            }
        }
        for(Pharmacy pharmacy : pharmacies2) {
            if(!pharmacyList.contains(pharmacy)){
                pharmacyList.add(pharmacy);
            }
        }

        return  new ResponseEntity<>(pharmacyList, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/marksForPatient/{email}")
    public ResponseEntity<List<Pharmacy>> findPharmaciesByPatient(@PathVariable String email) {
       Patient patient = this.patientService.findByEmail(email);
       List<PharmacyMarks> pharmacyMarks = this.pharmacyMarksService.findPharmacyMarksByPatientId(patient.getUserId());
      List<Pharmacy> pharmacyList = new ArrayList<>();
      for(PharmacyMarks pm : pharmacyMarks) {
          pharmacyList.add(pm.getPharmacy());
      }

        return  new ResponseEntity<>(pharmacyList, HttpStatus.OK);
    }

    @GetMapping("/marksFor/{email}")
    public ResponseEntity<List<PharmacyMarks>> findMarksByPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<PharmacyMarks> pharmacyMarks = this.pharmacyMarksService.findPharmacyMarksByPatientId(patient.getUserId());
        return  new ResponseEntity<>(pharmacyMarks, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/createMark")
    public ResponseEntity<PharmacyMarks> createPharmacyMark(@RequestBody PharmacyMarkDTO dto) {
        if(dto.getMark() > 10 ) {
            dto.setMark(10);
        }
        Pharmacy pharmacy = this.pharmacyService.findPharmacyByName(dto.getPharmacyName());
        Patient patient = this.patientService.findByEmail(dto.getPatientEmail());
        PharmacyMarks pharmacyMarks = new PharmacyMarks();
        pharmacyMarks.setPatient(patient);
        pharmacyMarks.setPharmacy(pharmacy);
        BigDecimal bd1 = new BigDecimal(dto.getMark()).setScale(2, RoundingMode.HALF_UP);
        double mark = bd1.doubleValue();
        pharmacyMarks.setMark(mark);
        this.pharmacyMarksService.save(pharmacyMarks);

        List<Double> marks = this.pharmacyMarksService.findPharmacyMarksByPharmacy(pharmacy.getId());
        marks.add(dto.getMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        pharmacy.setAverageMark(averageMark);
        this.pharmacyService.save(pharmacy);

        return  new ResponseEntity<>(pharmacyMarks, HttpStatus.OK);

    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/changeMark")
    public ResponseEntity<PharmacyMarks> changeMark(@RequestBody PharmacyMarkChangeDTO dto) {
        if(dto.getNewMark() > 10 ) {
            dto.setNewMark(10);
        }
        PharmacyMarks pharmacyMarks = this.pharmacyMarksService.findByPharmacyMarksId((dto.getPharmacyMarksId()));
        Pharmacy pharmacy = this.pharmacyService.findPharmacyByName(pharmacyMarks.getPharmacy().getName());
        Double mark = dto.getNewMark();
        BigDecimal bd1 = new BigDecimal(mark).setScale(2, RoundingMode.HALF_UP);
        mark = bd1.doubleValue();
        pharmacyMarks.setMark(mark);
        this.pharmacyMarksService.save(pharmacyMarks);

        List<Double> marks = this.pharmacyMarksService.findPharmacyMarksByPharmacy(pharmacy.getId());
        marks.add(dto.getNewMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        pharmacy.setAverageMark(averageMark);
        this.pharmacyService.save(pharmacy);
        return  new ResponseEntity<>(pharmacyMarks, HttpStatus.OK);

    }

}
