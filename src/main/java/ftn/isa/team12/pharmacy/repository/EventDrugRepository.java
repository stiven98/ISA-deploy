package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.event.EventDrug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventDrugRepository extends JpaRepository<EventDrug,UUID> {
}
