package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.marks.MedicalStuffMarks;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.MedicalStuffMarkChangeDTO;
import ftn.isa.team12.pharmacy.dto.MedicalStuffMarkDTO;
import ftn.isa.team12.pharmacy.service.ExaminationService;
import ftn.isa.team12.pharmacy.service.MedicalStuffMarksService;
import ftn.isa.team12.pharmacy.service.MedicalStuffService;
import ftn.isa.team12.pharmacy.service.PatientService;
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
@RequestMapping(value = "api/medicalStuffMarks", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalStuffMarksController {

    @Autowired
    private MedicalStuffMarksService medicalStuffMarksService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private ExaminationService examinationService;
    @Autowired
    private MedicalStuffService medicalStuffService;

    @GetMapping("/medicalStuffToMark/{email}")
    public ResponseEntity<List<MedicalStuff>> getMedicalStuffToMark(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<MedicalStuff> medicalStuffs = this.examinationService.findAllMedicalStuffThatTreatedPatient(patient.getUserId());
        List<MedicalStuff> medicalStuffList = new ArrayList<>();
        for(MedicalStuff ms : medicalStuffs) {
            if(!medicalStuffList.contains(ms)) {
                medicalStuffList.add(ms);
            }
        }
        return new ResponseEntity<>(medicalStuffList, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/createMark")
    public ResponseEntity<MedicalStuffMarks> createMedicalStuffMark(@RequestBody MedicalStuffMarkDTO dto) {
        if(dto.getMark() > 10) {
            dto.setMark(10);
        }
        MedicalStuff medicalStuff = this.medicalStuffService.findByEmail(dto.getMedicalStuffEmail());
        Patient patient = this.patientService.findByEmail(dto.getPatientEmail());
        MedicalStuffMarks medicalStuffMarks = new MedicalStuffMarks();
        medicalStuffMarks.setPatient(patient);
        medicalStuffMarks.setMedicalStuff(medicalStuff);
        BigDecimal bd1 = new BigDecimal(dto.getMark()).setScale(2, RoundingMode.HALF_UP);
        double mark = bd1.doubleValue();
        medicalStuffMarks.setMark(mark);
        this.medicalStuffMarksService.save(medicalStuffMarks);

        List<Double> marks = this.medicalStuffMarksService.findMedicalStuffMarksByMedicalStuff(medicalStuff.getUserId());
        marks.add(dto.getMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        medicalStuff.setAverageMark(averageMark);
        this.medicalStuffService.save(medicalStuff);

        return  new ResponseEntity<>(medicalStuffMarks, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/changeMark")
    public ResponseEntity<MedicalStuffMarks> changeMark(@RequestBody MedicalStuffMarkChangeDTO dto) {
        if(dto.getNewMark() > 10 ) {
            dto.setNewMark(10);
        }
        MedicalStuffMarks medMarks = this.medicalStuffMarksService.findMedicalStuffMarksByMedicalStuffMarksId((dto.getMedicalStuffMarksId()));
        MedicalStuff ms = this.medicalStuffService.findById(medMarks.getMedicalStuff().getUserId());
        Double mark = dto.getNewMark();
        BigDecimal bd1 = new BigDecimal(mark).setScale(2, RoundingMode.HALF_UP);
        mark = bd1.doubleValue();
        medMarks.setMark(mark);
        this.medicalStuffMarksService.save(medMarks);

        List<Double> marks = this.medicalStuffMarksService.findMedicalStuffMarksByMedicalStuff(ms.getUserId());
        marks.add(dto.getNewMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        ms.setAverageMark(averageMark);
        this.medicalStuffService.save(ms);
        return  new ResponseEntity<>(medMarks, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/marksForPatient/{email}")
    public ResponseEntity<List<MedicalStuff>> findMedicalStuffMarkedByPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<MedicalStuffMarks> medicalStuffMarks = this.medicalStuffMarksService.findMedicalStuffMarksByPatientId(patient.getUserId());
        List<MedicalStuff> medicalStuffs = new ArrayList<>();
        for(MedicalStuffMarks mm : medicalStuffMarks) {
            medicalStuffs.add(mm.getMedicalStuff());
        }

        return  new ResponseEntity<>(medicalStuffs, HttpStatus.OK);
    }
    @GetMapping("/marksFor/{email}")
    public ResponseEntity<List<MedicalStuffMarks>> findMarksByPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<MedicalStuffMarks> ms = this.medicalStuffMarksService.findMedicalStuffMarksByPatientId(patient.getUserId());
        return  new ResponseEntity<>(ms, HttpStatus.OK);
    }


}
