package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.Ingredient;
import java.util.List;
import java.util.Set;

public interface IngredientService {

    List<Ingredient> findAll();
    Ingredient saveAndFlush(Ingredient ingredientRequest);

    Set<Ingredient> getByIds(List<String> contraindication);
}
