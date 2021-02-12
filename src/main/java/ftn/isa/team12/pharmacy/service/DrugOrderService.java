package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.DrugOrder;
import ftn.isa.team12.pharmacy.dto.DrugOrderDTO;
import ftn.isa.team12.pharmacy.dto.DrugOrderPhAdminDTO;

import java.util.List;
import java.util.UUID;

public interface DrugOrderService {
    DrugOrder createDrugOrder(DrugOrderDTO drugOrder);

    List<DrugOrder> findAll();
    DrugOrder findById(UUID id);
    List<DrugOrder> findAllForSupplier(String email);

    List<DrugOrderPhAdminDTO> findAllByPharmacyID();

    boolean delete(String id);

    DrugOrder changeDrugOrder(DrugOrderPhAdminDTO dto);

}
