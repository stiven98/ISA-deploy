package ftn.isa.team12.pharmacy.service.impl;


import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.pharmacy.ExaminationPrice;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.ExaminationPriceDTO;
import ftn.isa.team12.pharmacy.repository.ExaminationPriceRepository;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.service.ExaminationPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ExaminationPriceSericeImpl  implements ExaminationPriceService{


    @Autowired
    ExaminationPriceRepository examinationPriceRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Override
    public List<ExaminationPrice> getAllByValideDate() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        return examinationPriceRepository.getAll(pharmacyAdministrator.getPharmacy(),new Date());
    }


    @Override
    public ExaminationPrice createExaminationPrice(ExaminationPriceDTO dto) {
        this.validationExaminationPrice(dto);
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());

        ExaminationPrice examinationPrice = new ExaminationPrice();
        examinationPrice.setDateOfValidity(new DateRange());
        examinationPrice.getDateOfValidity().setEndDate(dto.getEndDate());
        examinationPrice.getDateOfValidity().setStartDate(dto.getStartDate());
        examinationPrice.setExaminationType(dto.getExaminationType());
        examinationPrice.setPrice(dto.getPrice());
        examinationPrice.setPharmacy(pharmacy);

        examinationPrice = examinationPriceRepository.save(examinationPrice);

        return examinationPrice;
    }

    @Override
    public void validationExaminationPrice(ExaminationPriceDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());

        if(dto.getPrice() <= 0)
            throw new IllegalArgumentException("Bad input price");

        if(dto.getStartDate().after(dto.getEndDate()) || !dto.getStartDate().after(new Date()) || !dto.getEndDate().after(new Date()))
            throw new IllegalArgumentException("Bad input date");

        List<ExaminationPrice> examinationPrices =  examinationPriceRepository.getAllByAllByValidDate(pharmacy,dto.getStartDate());
        if(!examinationPrices.isEmpty()){
            throw new IllegalArgumentException("For this period already set price for examination");
        }
    }


    @Override
    public ExaminationPrice changeExaminationPrice(ExaminationPriceDTO dto) {
        ExaminationPrice examinationPrice = examinationPriceRepository.findByExaminationPriceId(dto.getExaminationPriceId());

        if(examinationPrice == null)
            throw new IllegalArgumentException("No examination price");

        if(dto.getPrice() < 0 || dto.getPrice() == examinationPrice.getPrice())
            throw new IllegalArgumentException("Bad input price");

        examinationPrice.setPrice(dto.getPrice());
        examinationPrice = examinationPriceRepository.save(examinationPrice);

        return examinationPrice;
    }


    @Override
    public List<ExaminationPriceDTO> getAllForChane() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        List<ExaminationPrice> examinationPrices = examinationPriceRepository.getAllForChange(pharmacy,new Date());
        List<ExaminationPriceDTO> dto = new ArrayList<>();
        for(ExaminationPrice ex: examinationPrices){
            dto.add(new ExaminationPriceDTO(ex));
        }



        return dto;
    }
}
