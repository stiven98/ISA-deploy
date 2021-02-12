package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
}
