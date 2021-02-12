package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugSearcDTO;
import java.util.List;
import java.util.Set;

public interface SearchDrugService {

    List<DrugForOrderDTO> searchDrug(DrugSearcDTO dto);
    Set<DrugInPharmacy> searchDrugByName(String name,Set<DrugInPharmacy> dip);
    Set<DrugInPharmacy> searchDrugByCode(String code);
    Set<DrugInPharmacy> searchDrugByTypeOfDrug(String typeOfDrug);
    Set<DrugInPharmacy> searchDrugByFormOfDrug(String formOfDrug);
    Set<DrugInPharmacy> searchDrugByIssuanceRegime(String issuanceRegime);
    Set<DrugInPharmacy> searchDrugByManufactureName(String manufactureName);
    Set<DrugInPharmacy> searchDrugByQuantity(int quantity);
    List<DrugForOrderDTO> prepareForSend();




}
