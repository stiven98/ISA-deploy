package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import ftn.isa.team12.pharmacy.repository.DrugMarksRepository;
import ftn.isa.team12.pharmacy.service.DrugMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class DrugMarksServiceImpl implements DrugMarksService {

    @Autowired
    private DrugMarksRepository drugMarksRepository;

    @Override
    public DrugMarks save(DrugMarks drugMarks) {
        return this.drugMarksRepository.save(drugMarks);
    }

    @Override
    public DrugMarks findByDrugMarksId(UUID drugMarksId) {
        return this.drugMarksRepository.findDrugMarksByDrugMarksId(drugMarksId);
    }

    @Override
    public List<Double> findDrugMarksByDrug(UUID drugId) {
        return this.drugMarksRepository.findDrugMarksByDrug(drugId);
    }


    @Override
    public List<DrugMarks> findDrugMarksByPatientId(UUID id) {
        return this.drugMarksRepository.findDrugMarksByPatientId(id);
    }
}
