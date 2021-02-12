package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.DrugOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DrugOrderRepository extends JpaRepository<DrugOrder, UUID> {
    List<DrugOrder> findALlByPharmacyId(UUID pharmacyID);


    DrugOrder findByOrderId(UUID id);
}
