package ftn.isa.team12.pharmacy.controller;

import ftn.isa.team12.pharmacy.domain.drugs.DrugOrder;
import ftn.isa.team12.pharmacy.dto.DrugOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugOrderPhAdminDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesCreateDTO;
import ftn.isa.team12.pharmacy.service.DrugOrderService;
import ftn.isa.team12.pharmacy.service.OfferService;
import ftn.isa.team12.pharmacy.service.SupplierService;
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
@RequestMapping(value = "/api/drugOrder", produces = MediaType.APPLICATION_JSON_VALUE)
public class DrugOrderController {

    @Autowired
    private DrugOrderService drugOrderService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private OfferService offerService;


    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/createOrder")
    public ResponseEntity<DrugOrder> createDrugOrder(@RequestBody DrugOrderDTO drugOrder) {
        DrugOrder order = drugOrderService.createDrugOrder(drugOrder);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPPLIER')")
    @GetMapping("/all/{email}")
    public ResponseEntity<List<DrugOrder>> findAllForSupplier(@PathVariable String email) {

        return new ResponseEntity<>(this.drugOrderService.findAllForSupplier(email), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @GetMapping("/allByPharmacy")
    public ResponseEntity<List<DrugOrderPhAdminDTO>> findAllDermatologistInPharmacy() {
        return new ResponseEntity<>(drugOrderService.findAllByPharmacyID(), HttpStatus.OK);
    }



    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<?> deleteDrug(@RequestBody String id) {
        Map<String, String> result = new HashMap<>();
        result.put("result","Successfully delete drug order");
        if(drugOrderService.delete(id))
            return new ResponseEntity<>(result,HttpStatus.OK);

        result.put("result","Can't delete drug order");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }



    @PreAuthorize("hasAnyRole('ROLE_PH_ADMIN')")
    @PostMapping("/change")
    public ResponseEntity<?> changeDrugOrder(@RequestBody DrugOrderPhAdminDTO dto) {
        Map<String, String> result = new HashMap<>();
        result.put("result","Successfully change drug order");
        if(drugOrderService.changeDrugOrder(dto) != null)
            return new ResponseEntity<>(result,HttpStatus.OK);

        result.put("result","Can't change drug order");
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }



}
