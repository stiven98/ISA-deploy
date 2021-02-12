package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.repository.LoyaltyProgramRepository;
import ftn.isa.team12.pharmacy.service.LoyaltyProgramService;
import ftn.isa.team12.pharmacy.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = false)
public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

    @Autowired
    private LoyaltyProgramRepository loyaltyProgramRepository;

    @Autowired
    private PatientService patientService;

    @Override
    public LoyaltyProgram saveAndFlush(LoyaltyProgram loyaltyProgramRequest) {


        LoyaltyProgram loyaltyProgram = this.loyaltyProgramRepository.findById(loyaltyProgramRequest.getId()).get();
        loyaltyProgram.setPointsPerCounseling(loyaltyProgramRequest.getPointsPerCounseling());
        loyaltyProgram.setPointsPerExamination(loyaltyProgramRequest.getPointsPerExamination());
        loyaltyProgram.setDiscountForGold(loyaltyProgramRequest.getDiscountForGold());
        loyaltyProgram.setDiscountForRegular(loyaltyProgramRequest.getDiscountForRegular());
        loyaltyProgram.setDiscountForSilver(loyaltyProgramRequest.getDiscountForSilver());
        loyaltyProgram.setMinGold(loyaltyProgramRequest.getMinGold());
        loyaltyProgram.setMinRegular(loyaltyProgramRequest.getMinRegular());
        loyaltyProgram.setMinSilver(loyaltyProgramRequest.getMinSilver());
        loyaltyProgram = this.loyaltyProgramRepository.save(loyaltyProgram);

        List<Patient> patients = patientService.findAll();
        for (Patient patient: patients) {
            patient.getCategory().setCategory(loyaltyProgram.getCategory(patient.getCategory().getPoints()));
            patientService.save(patient);
        }

        return loyaltyProgram;
    }

    @Override
    @Transactional(readOnly = false)
    public LoyaltyProgram getLoyaltyProgram() {
        return this.loyaltyProgramRepository.findAll().get(0);
    }
}
