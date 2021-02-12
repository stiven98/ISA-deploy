package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.drugs.DrugPrice;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DrugPriceRepository extends JpaRepository<DrugPrice, UUID> {

    @Query("select d.price from DrugPrice d where d.pharmacy.id= ?1 and d.drug.drugId= ?2")
    double getPriceForDrug(UUID pharmacyId, UUID drugId);

    @Query("select d from DrugPrice d where d.pharmacy= ?1 and d.validityPeriod.startDate < ?2 and d.validityPeriod.endDate > ?2")
    List<DrugPrice> getAll(Pharmacy pharmacy, Date date);

    @Query("select d from DrugPrice d where d.pharmacy= ?1 and d.validityPeriod.startDate <= ?2  and d.validityPeriod.endDate >= ?2 and d.drug.drugId = ?3")
    List<DrugPrice> getAllByDrug(Pharmacy pharmacy,Date  date,UUID drug);

    @Query("select d from DrugPrice d where d.pharmacy= ?1 and d.validityPeriod.startDate <= ?2 and d.validityPeriod.endDate >= ?2 or d.validityPeriod.startDate >= ?2")
    List<DrugPrice> getAllForChange(Pharmacy pharmacy, Date date);



 DrugPrice findDrugPriceById(UUID id);

}
