package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {
}
