package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipe;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipeItem;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.DrugMarkDTO;
import ftn.isa.team12.pharmacy.dto.DrugMarksChangeDTO;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/drugMarks", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugMarksController {
    @Autowired
    private DrugMarksService drugMarksService;

    @Autowired
    private DrugReservationService drugReservationService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private ERecipeService eRecipeService;
    @Autowired
    private PatientService patientService;


    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/createMark")
    public ResponseEntity<DrugMarks> createDrugMark(@RequestBody DrugMarkDTO dto) {
        if(dto.getMark() > 10) {
            dto.setMark(10);
        }
        Drug drug = this.drugService.findDrugByName(dto.getDrugName());
        Patient patient = this.patientService.findByEmail(dto.getPatientEmail());
        DrugMarks drugMarks = new DrugMarks();
        drugMarks.setPatient(patient);
        drugMarks.setDrug(drug);
        BigDecimal bd1 = new BigDecimal(dto.getMark()).setScale(2, RoundingMode.HALF_UP);
        double mark = bd1.doubleValue();
        drugMarks.setMark(mark);
        this.drugMarksService.save(drugMarks);

        List<Double> marks = this.drugMarksService.findDrugMarksByDrug(drug.getDrugId());
        marks.add(dto.getMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        drug.setAverageMark(averageMark);
        this.drugService.save(drug);

        return  new ResponseEntity<>(drugMarks, HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @PostMapping("/changeMark")
    public ResponseEntity<DrugMarks> changeMark(@RequestBody DrugMarksChangeDTO dto) {
        if(dto.getNewMark() > 10) {
            dto.setNewMark(10);
        }
        DrugMarks drugMarks = this.drugMarksService.findByDrugMarksId((dto.getDrugMarksId()));
        Drug drug = this.drugService.findDrugByName(drugMarks.getDrug().getName());
        Double mark = dto.getNewMark();
        BigDecimal bd1 = new BigDecimal(mark).setScale(2, RoundingMode.HALF_UP);
        mark = bd1.doubleValue();
        drugMarks.setMark(mark);
        this.drugMarksService.save(drugMarks);

        List<Double> marks = this.drugMarksService.findDrugMarksByDrug(drug.getDrugId());
        marks.add(dto.getNewMark());
        double averageMark = 0;
        for(double d : marks) {
            averageMark = averageMark + d;
        }
        averageMark = averageMark / marks.size();
        BigDecimal bd = new BigDecimal(averageMark).setScale(2, RoundingMode.HALF_UP);
        averageMark = bd.doubleValue();
        drug.setAverageMark(averageMark);
        this.drugService.save(drug);
        return  new ResponseEntity<>(drugMarks, HttpStatus.OK);

    }

    @GetMapping("/marksFor/{email}")
    public ResponseEntity<List<DrugMarks>> findMarksByPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<DrugMarks> drugMarks = this.drugMarksService.findDrugMarksByPatientId(patient.getUserId());
        return  new ResponseEntity<>(drugMarks, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/marksForPatient/{email}")
    public ResponseEntity<List<Drug>> findDrugsMarkedByPatient(@PathVariable String email) {
        Patient patient = this.patientService.findByEmail(email);
        List<DrugMarks> drugMarks = this.drugMarksService.findDrugMarksByPatientId(patient.getUserId());
        List<Drug> drugList = new ArrayList<>();
        for(DrugMarks dm : drugMarks) {
            drugList.add(dm.getDrug());
        }

        return  new ResponseEntity<>(drugList, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PATIENT')")
    @GetMapping("/drugsForPatient/{email}")
    public ResponseEntity<List<Drug>> findDrugsForPatient(@PathVariable String email) {
        List<Drug> drugs = this.drugReservationService.findDrugsPatientReserved(email);
        List<ERecipe> eRecipes = this.eRecipeService.findAllERecipesByPatient(email);
        Set<ERecipeItem> eRecipeItems = new HashSet<>();
        for(ERecipe eRecipe : eRecipes) {
            eRecipeItems.addAll(eRecipe.getERecipeItems());
        }
        List<Drug> drugsFromErecipe = new ArrayList<>();
        for(ERecipeItem item : eRecipeItems) {
            if(!drugsFromErecipe.contains(item.getDrug())) {
                drugsFromErecipe.add(item.getDrug());
            }
        }
        List<Drug> drugList = new ArrayList<>();
        for(Drug drug : drugs) {
            if(!drugList.contains(drug)) {
                drugList.add(drug);
            }
        }
        for(Drug drug : drugsFromErecipe) {
            if(!drugList.contains(drug)) {
                drugList.add(drug);
            }
        }
        return  new ResponseEntity<>(drugList, HttpStatus.OK);
    }
}
