package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.common.LocationMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationMapRepository extends JpaRepository<LocationMap, UUID> {
    LocationMap findByPharmacyId(UUID id);
}
