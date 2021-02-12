package ftn.isa.team12.pharmacy.controller;

import ftn.isa.team12.pharmacy.domain.drugs.Ingredient;
import ftn.isa.team12.pharmacy.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/ingredient", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @GetMapping("/all")
    public ResponseEntity<List<Ingredient>> findAll() {
        return new ResponseEntity<>(this.ingredientService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
    @PostMapping("/add")
    public ResponseEntity<Ingredient> saveAndFlush(@RequestBody Ingredient ingredientRequest) {
        return new ResponseEntity<>(this.ingredientService.saveAndFlush(ingredientRequest), HttpStatus.CREATED);
    }



}
