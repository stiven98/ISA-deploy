package ftn.isa.team12.pharmacy.controller;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugSearcDTO;
import ftn.isa.team12.pharmacy.service.SearchDrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchDrugController {


    @Autowired
    private SearchDrugService searchDrugService;


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/drug")
    public ResponseEntity<List<DrugForOrderDTO>> findAll(@RequestBody DrugSearcDTO dto) {
        List<DrugForOrderDTO> list = searchDrugService.searchDrug(dto);
        if(list == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
