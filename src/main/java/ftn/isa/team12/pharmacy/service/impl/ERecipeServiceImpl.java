package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipe;
import ftn.isa.team12.pharmacy.domain.drugs.ERecipeItem;
import ftn.isa.team12.pharmacy.domain.enums.ERecipeStatus;
import ftn.isa.team12.pharmacy.domain.enums.ExaminationType;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.ERecipeFromQrCodeDTO;
import ftn.isa.team12.pharmacy.dto.QrCodeItem;
import ftn.isa.team12.pharmacy.repository.DrugInPharmacyRepository;
import ftn.isa.team12.pharmacy.repository.ERecipeRepository;
import ftn.isa.team12.pharmacy.repository.PatientRepository;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = false)
public class ERecipeServiceImpl implements ERecipeService {

    @Autowired
    private ERecipeRepository eRecipeRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private DrugInPharmacyRepository drugInPharmacyRepository;

    @Override
    public List<ERecipe> findAllERecipesByPatient(String email) {
        Patient patient = this.patientRepository.findByEmail(email);
        return eRecipeRepository.findAllERecipesByPatient(patient.getUserId());
    }

    @Override
    public List<Pharmacy> findPharmaciesWherePatientHasERecipe(String patientEmail) {
        Patient patient = this.patientRepository.findByEmail(patientEmail);

        return this.eRecipeRepository.findPharmaciesWherePatientHasERecipe(patient.getUserId());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ERecipe addERecipe(ERecipeFromQrCodeDTO eRecipeFromQrCodeDTO) {
        Patient patient = this.patientService.findByEmail(eRecipeFromQrCodeDTO.getEmail());
        LoyaltyProgram lp = this.loyaltyProgramService.getLoyaltyProgram();


        Pharmacy pharmacy = pharmacyService.findPharmacyById(UUID.fromString(eRecipeFromQrCodeDTO.getPharmacyId()));
        Set<ERecipeItem> items = new HashSet<>();

        for(QrCodeItem item : eRecipeFromQrCodeDTO.getQrCodeItems()) {
            Drug drug = drugService.findDrugByName(item.getName());
            DrugInPharmacy drugInPharmacy = drugInPharmacyRepository.findDrugInPharmacy(drug.getDrugId(), pharmacy.getId());
            drugInPharmacy.setQuantity(drugInPharmacy.getQuantity() - item.getQuantity());
            drugInPharmacyRepository.save(drugInPharmacy);
            ERecipeItem eRecipeItem = new ERecipeItem();
            eRecipeItem.setDrug(drug);
            eRecipeItem.setQuantity(item.getQuantity());
            items.add(eRecipeItem);
            patient.getCategory().setPoints(patient.getCategory().getPoints() + drug.getPoints());
        }
        patient.getCategory().setPoints(patient.getCategory().getPoints());
        patient.getCategory().setCategory(lp.getCategory(patient.getCategory().getPoints()));
        this.patientService.save(patient);

        ERecipe eRecipe = new ERecipe();
        Date date = new Date();
        double correctionFactorD = 1000.0 * Math.random();
        int correctionFactor = (int) correctionFactorD;
        eRecipe.setCode("e" + date.hashCode() + "-" + correctionFactor);
        eRecipe.setDuration(0);
        eRecipe.setERecipeStatus(ERecipeStatus.newErecipe);
        eRecipe.setPatient(patient);
        eRecipe.setPharmacy(pharmacy);
        eRecipe.setDateOfIssuing(new Date());
        eRecipe = eRecipeRepository.save(eRecipe);
        for(ERecipeItem it : items){
            it.setERecipe(eRecipe);
        }
        eRecipe.setERecipeItems(items);
        return eRecipeRepository.save(eRecipe);
    }
}
