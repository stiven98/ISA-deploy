package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.domain.drugs.DrugPrice;
import ftn.isa.team12.pharmacy.dto.ChangeDrugPriceDTO;
import ftn.isa.team12.pharmacy.dto.DrugPriceDTO;
import ftn.isa.team12.pharmacy.dto.PriceDTO;
import ftn.isa.team12.pharmacy.service.DrugPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/drugPrice", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugPriceController {

    @Autowired
    private DrugPriceService drugPriceService;

    @PostMapping ("/price")
    public Double findPrice(@RequestBody PriceDTO priceDTO) throws AccessDeniedException {
        return  drugPriceService.getPriceForDrug(priceDTO.getPharmacyId(),priceDTO.getDrugId());
    }


    @GetMapping("/all")
    public ResponseEntity<List<DrugPrice>> getAllDrugPrice(){
        return new ResponseEntity<>(drugPriceService.getAllDrugPrice(), HttpStatus.OK) ;
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping ("/createDrugPrice")
    public ResponseEntity<?> createDrugPRice(@RequestBody DrugPriceDTO dto){
        Map<String, String> result = new HashMap<>();
        DrugPrice drugPrice = drugPriceService.createDrugPrice(dto);
        if(drugPrice == null){
            result.put("result", "Can't create drug price");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully create drug price");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/allForChange")
    public ResponseEntity<List<DrugPriceDTO>> getAllDrugPriceForChange(){
        return new ResponseEntity<>(drugPriceService.finALlForChange(), HttpStatus.OK) ;
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/change")
    public ResponseEntity<?> changeDrugPrice(@RequestBody ChangeDrugPriceDTO dto){
        Map<String, String> result = new HashMap<>();
        DrugPrice drugPrice = drugPriceService.change(dto);
        if(drugPrice == null){
            result.put("result", "Can't change drug price");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("result","Successfully change drug price");
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
