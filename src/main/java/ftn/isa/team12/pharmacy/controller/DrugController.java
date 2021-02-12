package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.*;
import ftn.isa.team12.pharmacy.domain.enums.IssuanceRegime;
import ftn.isa.team12.pharmacy.dto.DrugDTO;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.ExaminationDataRequestDTO;
import ftn.isa.team12.pharmacy.dto.NewDrugDTO;
import ftn.isa.team12.pharmacy.service.ContraindicationService;
import ftn.isa.team12.pharmacy.service.DrugService;
import ftn.isa.team12.pharmacy.service.IngredientService;
import ftn.isa.team12.pharmacy.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/api/drug", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugController {


    @Autowired
    private DrugService drugService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ContraindicationService contraindicationService;

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/allDrugs")
    public ResponseEntity<List<Drug>> findAllFullDrugs() {
        return new ResponseEntity<>(this.drugService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DrugDTO>> findAllDrug(){
        List<Drug> drugs = drugService.findAll();
        List<DrugDTO> dto = new ArrayList<DrugDTO>();
        for (Drug d: drugs) {
            System.out.print(d.getAverageMark());
            dto.add(new DrugDTO(d));
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getAllByPatientAndPharmacy")
    public ResponseEntity<?> findAllDrugsByPatientAndPharmacy(ExaminationDataRequestDTO dto){
        List<Drug> drugs = this.drugService.findAllByPharmacyAndPatient(dto);
        Map<String, String> result = new HashMap<>();
        if(drugs == null){
            result.put("result", "Patient with specified id doesn't exist!");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    @GetMapping("/drugForOrder")
    public ResponseEntity<List<DrugForOrderDTO>> getAll(){
        return new ResponseEntity<>(drugService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Drug> findByName(@PathVariable String name){
        Drug drug = drugService.findDrugByName(name);
        return new ResponseEntity<>(drug,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Drug> saveDrug(@RequestBody NewDrugDTO drugRequest) {
        System.out.println(drugRequest);

        Set<Contraindication> contraindications = contraindicationService.getByIds(drugRequest.getContraindication());
        Set<Ingredient> ingredients = ingredientService.getByIds(drugRequest.getIngredients());
        Set<Drug> substituteDrugs = drugService.getByIds(drugRequest.getSubstituteDrug());
        Manufacturer manufacturer = manufacturerService.findById(drugRequest.getManufacturer());

        Drug drug = new Drug();
        drug.setManufacturer(manufacturer);
        drug.setContraindications(contraindications);
        drug.setName(drugRequest.getName());
        drug.setCode(drugRequest.getCode());
        drug.setIngredients(ingredients);
        drug.setPoints(drugRequest.getPoints());
        drug.setNote(drugRequest.getNote());
        drug.setSubstituteDrugs(substituteDrugs);
        drug.setTypeOfDrug(drugRequest.getTypeOfDrug());
        drug.setFormOfDrug(drugRequest.getFormOfDrug());
        drug.setIssuanceRegime((drugRequest.getIssuanceRegime().equals("With recipe") ? IssuanceRegime.withRecipe : IssuanceRegime.withoutRecipe));
        drug = this.drugService.saveAndFlush(drug);

        System.out.println(drug);

        return new ResponseEntity<>(drug, HttpStatus.CREATED);
    }

    @PostMapping("/findByIds")
    public ResponseEntity<List<Drug>> findByIds(@RequestBody List<String> ids) {
        return new ResponseEntity<>(this.drugService.findByIds(ids), HttpStatus.OK);
    }




}
