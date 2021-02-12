package ftn.isa.team12.pharmacy.controller;


import ftn.isa.team12.pharmacy.domain.common.Promotion;
import ftn.isa.team12.pharmacy.dto.DeleteEmployeeDTO;
import ftn.isa.team12.pharmacy.dto.PromotionDTO;
import ftn.isa.team12.pharmacy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/promotion", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromotionController {


    @Autowired
    PromotionService promotionService;

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createPromotion(@RequestBody PromotionDTO dto) {
        Map<String, String> result = new HashMap<>();
        if(promotionService.createPromotion(dto) != null) {
            result.put("result","Successfully create promotion");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        result.put("result","Can't create promotion");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


}
