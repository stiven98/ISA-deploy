package ftn.isa.team12.pharmacy.controller;

import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
import ftn.isa.team12.pharmacy.dto.ExaminationPriceDTO;
import ftn.isa.team12.pharmacy.service.ExaminationPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/examinationPrice", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExaminationPriceController {


    @Autowired
    ExaminationPriceService examinationPriceService;


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ExaminationPrice>> getAllDrugPrice(){
        return new ResponseEntity<>(examinationPriceService.getAllByValideDate(), HttpStatus.OK) ;
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createExaminationPrice(@RequestBody ExaminationPriceDTO dto){
        Map<String, String> result = new HashMap<>();
        ExaminationPrice examinationPrice = examinationPriceService.createExaminationPrice(dto);
        if(examinationPrice == null){
            result.put("result", "Can't create examination price");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully create examination price");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/change")
    public ResponseEntity<?> changeExaminationPrice(@RequestBody ExaminationPriceDTO dto){
        Map<String, String> result = new HashMap<>();
        ExaminationPrice examinationPrice = examinationPriceService.changeExaminationPrice(dto);
        if(examinationPrice == null){
            result.put("result", "Can't create examination price");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully create examination price");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/allForChange")
    public ResponseEntity<List<ExaminationPriceDTO>> getAllExaminationPriceForChange(){
        return new ResponseEntity<>(examinationPriceService.getAllForChane(), HttpStatus.OK) ;
    }

}
