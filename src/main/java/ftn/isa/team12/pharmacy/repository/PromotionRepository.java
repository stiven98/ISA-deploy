package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.common.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PromotionRepository extends JpaRepository<Promotion, UUID> {



}
