package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.DrugPrice;
import ftn.isa.team12.pharmacy.dto.ChangeDrugPriceDTO;
import ftn.isa.team12.pharmacy.dto.DrugPriceDTO;

import java.util.List;
import java.util.UUID;

public interface DrugPriceService {

    double getPriceForDrug(UUID pharmacyId, UUID drugId);
    List<DrugPrice> getAllDrugPrice();

    DrugPrice createDrugPrice(DrugPriceDTO dto);

    void validationDrugPrice (DrugPriceDTO dto);

    List<DrugPriceDTO> finALlForChange();


    DrugPrice change(ChangeDrugPriceDTO dto);

}
