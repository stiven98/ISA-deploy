package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.marks.DrugMarks;
import java.util.List;
import java.util.UUID;

public interface DrugMarksService {

    DrugMarks save(DrugMarks drugMarks);
    DrugMarks findByDrugMarksId(UUID drugMarksId);

    List<Double> findDrugMarksByDrug(UUID drugId);
    List<DrugMarks> findDrugMarksByPatientId(UUID id);

}
