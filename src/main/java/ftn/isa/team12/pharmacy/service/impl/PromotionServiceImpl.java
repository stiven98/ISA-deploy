package ftn.isa.team12.pharmacy.service.impl;


import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.common.Promotion;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.PromotionDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.repository.PromotionRepository;
import ftn.isa.team12.pharmacy.service.PatientService;
import ftn.isa.team12.pharmacy.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PatientService patientService;

    @Autowired
    EmailSender emailSender;

    @Override
    public Promotion createPromotion(PromotionDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        List<Patient> patientList = patientService.findAll();

        if(dto.getStartDate().after(dto.getEndDate()) || !dto.getStartDate().after(new Date()) || !dto.getEndDate().after(new Date()))
            throw new IllegalArgumentException("Bad input date");

        Promotion promotion = new Promotion();
        promotion.setText(dto.getText());
        promotion.setDateRange(new DateRange());
        promotion.getDateRange().setEndDate(dto.getEndDate());
        promotion.getDateRange().setStartDate(dto.getStartDate());
        promotion.setPharmacy(pharmacy);

        promotion = promotionRepository.save(promotion);

        if(promotion != null){
            for(Patient patient: patientList){
                if (patient.getSubscribedPharmacies().contains(pharmacy)) {
                    try{
                        emailSender.sendEmailNewPromotion(patient.getLoginInfo().getEmail(),promotion);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }

        return promotion;
    }
}
