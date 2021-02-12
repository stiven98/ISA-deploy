package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugInPharmacyChangesDTO;
import java.util.List;
import java.util.UUID;

public interface DrugInPharmacyService {

    List<Drug> findDrugInPharmacyById(UUID id);
    void addDrugInPharmacy(DrugInPharmacyChangesDTO drugInPharmacyChangesDTO);
    List<Pharmacy> findPharmaciesWithDrug(UUID id);
    Integer findDrugQuantity(UUID drugId, UUID pharmacyId);
    List<DrugForOrderDTO> findAllDrugInPharmacyByid(UUID id);
    void updateDrugInPharmacy(DrugInPharmacyChangesDTO dto);
    void removeDrugInPharmacy(DrugInPharmacyChangesDTO drugInPharmacyChangesDTO);
    void save(DrugInPharmacy drugInPharmacy);
}
