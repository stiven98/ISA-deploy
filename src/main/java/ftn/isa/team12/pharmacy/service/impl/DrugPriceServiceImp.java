package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.drugs.DrugPrice;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.ChangeDrugPriceDTO;
import ftn.isa.team12.pharmacy.dto.DrugPriceDTO;
import ftn.isa.team12.pharmacy.repository.DrugPriceRepository;
import ftn.isa.team12.pharmacy.repository.DrugRepository;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.service.DrugPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = false)
public class DrugPriceServiceImp implements DrugPriceService {

    @Autowired
    private DrugPriceRepository drugPriceRepository;

    @Autowired
    DrugRepository drugRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;


    @Override
    public double getPriceForDrug(UUID pharmacyId, UUID drugId) {
        return this.drugPriceRepository.getPriceForDrug(pharmacyId, drugId);
    }

    @Override
    public List<DrugPrice> getAllDrugPrice() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        List<DrugPrice> list = drugPriceRepository.getAll(pharmacyAdministrator.getPharmacy(),new Date());
        return list;
    }


    @Override
    public DrugPrice createDrugPrice(DrugPriceDTO dto) {
        this.validationDrugPrice(dto);
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Drug drug = drugRepository.findByDrugId(dto.getIdDrug());
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());

        DrugPrice drugPrice = new DrugPrice();
        drugPrice.setValidityPeriod(new DateRange());
        drugPrice.getValidityPeriod().setStartDate(dto.getStartDate());
        drugPrice.getValidityPeriod().setEndDate(dto.getEndDate());
        drugPrice.setPrice(dto.getPrice());
        drugPrice.setPharmacy(pharmacy);
        drugPrice.setDrug(drug);

        drugPrice = drugPriceRepository.save(drugPrice);

        return drugPrice;
    }

    @Override
    public void validationDrugPrice(DrugPriceDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Drug drug = drugRepository.findByDrugId(dto.getIdDrug());
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());

        if(dto.getPrice() <= 0)
            throw new IllegalArgumentException("Bad input price");

        if(dto.getStartDate().after(dto.getEndDate()) || !dto.getStartDate().after(new Date()) || !dto.getEndDate().after(new Date()))
            throw new IllegalArgumentException("Bad input date");

        if(drug == null || pharmacy == null)
            throw new IllegalArgumentException("Bad input");


        boolean flag =false;
        for(DrugInPharmacy d : pharmacy.getDrugs()) {
            if (d.getDrug().getDrugId() == drug.getDrugId()){
                flag = true;
                break;
            }
        }

        if(!flag)
            throw new IllegalArgumentException("No drug in pharmacy");

        List<DrugPrice> drugPriceList = drugPriceRepository.getAllByDrug(pharmacy , dto.getStartDate(),drug.getDrugId());
        if(!drugPriceList.isEmpty()){
            throw new IllegalArgumentException("For this period already set price for " + drug.getName());
        }
    }


    @Override
    public List<DrugPriceDTO> finALlForChange() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        List<DrugPrice> list = drugPriceRepository.getAllForChange(pharmacyAdministrator.getPharmacy(),new Date());
        List<DrugPriceDTO> dto = new ArrayList<>();
        for(DrugPrice d : list){
            dto.add(new DrugPriceDTO(d));
        }

        return dto;
    }


    @Override
    public DrugPrice change(ChangeDrugPriceDTO dto) {
        DrugPrice drugPrice = drugPriceRepository.findDrugPriceById(dto.getDrugPrice());
        if(drugPrice == null)
            throw new IllegalArgumentException("No drug price");

        if(dto.getPrice() < 0 || dto.getPrice() == drugPrice.getPrice())
            throw new IllegalArgumentException("Bad input price");

        drugPrice.setPrice(dto.getPrice());
        drugPrice = drugPriceRepository.save(drugPrice);
        return drugPrice;
    }
}
