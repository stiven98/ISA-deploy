package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.drugs.Ingredient;
import ftn.isa.team12.pharmacy.repository.IngredientRepository;
import ftn.isa.team12.pharmacy.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }

    public Ingredient saveAndFlush(Ingredient ingredientRequest) {
        return this.ingredientRepository.saveAndFlush(ingredientRequest);
    }

    @Override
    public Set<Ingredient> getByIds(List<String> contraindicationIds) {
        Set<Ingredient> res = new HashSet<>();
        for (String id :
                contraindicationIds) {
            res.add(this.ingredientRepository.findById(UUID.fromString(id)).get());
        }
        return res;
    }
}
