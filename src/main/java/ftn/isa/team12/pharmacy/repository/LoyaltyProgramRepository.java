package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, UUID> {
}
