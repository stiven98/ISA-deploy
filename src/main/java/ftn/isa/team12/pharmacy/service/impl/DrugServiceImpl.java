package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.DrugForOrderDTO;
import ftn.isa.team12.pharmacy.dto.ExaminationDataRequestDTO;
import ftn.isa.team12.pharmacy.repository.DrugInPharmacyRepository;
import ftn.isa.team12.pharmacy.repository.DrugRepository;
import ftn.isa.team12.pharmacy.repository.PatientRepository;
import ftn.isa.team12.pharmacy.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = false)
public class DrugServiceImpl implements DrugService {

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DrugInPharmacyRepository drugInPharmacyRepository;
    
    @Override
    public List<Drug> findAll() {
        return drugRepository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public Drug findDrugByName(String drugName) {
        return drugRepository.findDrugByName(drugName);
    }

    @Override
    public Set<Drug> getByIds(List<String> substituteDrugIds) {
        Set<Drug> substituteDrugs = new HashSet<Drug>();
        for (String id : substituteDrugIds) {
            substituteDrugs.add(this.drugRepository.findById(UUID.fromString(id)).get());
        }
        return substituteDrugs;
    }

    @Override
    public Drug saveAndFlush(Drug drug) {
        // Add validation
        return this.drugRepository.saveAndFlush(drug);
    }

    @Override
    public List<Drug> findAllByPharmacyAndPatient(ExaminationDataRequestDTO dto) {
        List<Drug> drugs = new ArrayList<>();
        Patient patient = patientRepository.getOne(dto.getPatientId());
        if (patient == null){
            return null;
        }
        List<DrugInPharmacy> drugsInPharmacy = drugInPharmacyRepository.findDrugInPharmacyByPharmacyId(dto.getPharmacyId());
        List<Drug> available = new ArrayList<>();
        drugsInPharmacy.forEach(drugInPharmacy -> available.add(drugInPharmacy.getDrug()));
        Set<Drug> allergies = patient.getAllergies();
        for(Drug drug : available){
            boolean containedInAllergies = false;
            for(Drug allergy : allergies){
                if(drug.getDrugId() == allergy.getDrugId()) {
                    containedInAllergies = true;
                    break;
                }
            }
            if(!containedInAllergies){
                drugs.add(drug);
            }
        }
        return drugs;
    }

    @Override
    public List<Drug> findByIds(List<String> ids) {
        List<Drug> drugs = new ArrayList<>();
        for (String id :
                ids) {
            drugs.add(this.drugRepository.findById(UUID.fromString(id)).get());
        }
        return drugs;
    }

    @Override
    @Transactional(readOnly = false)
    public Drug findById(UUID id) {
        return drugRepository.findById(id).orElseGet(null);
    }

    @Override
    public Drug save(Drug drug) {
        return this.drugRepository.save(drug);
    }


    @Override
    public List<DrugForOrderDTO> getAll() {
        List<DrugForOrderDTO> dto = new ArrayList<>();
        for (Drug drug: drugRepository.findAll()) {
            dto.add(new DrugForOrderDTO(drug));
        }
        return dto;
    }
}
