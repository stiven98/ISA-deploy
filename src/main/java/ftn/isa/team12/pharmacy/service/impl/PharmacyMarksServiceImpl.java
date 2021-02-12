package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.marks.PharmacyMarks;
import ftn.isa.team12.pharmacy.repository.PharmacyMarksRepository;
import ftn.isa.team12.pharmacy.service.PharmacyMarksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class PharmacyMarksServiceImpl implements PharmacyMarksService {
    @Autowired
    private PharmacyMarksRepository pharmacyMarksRepository;

    @Override
    public List<Double> findPharmacyMarksByPharmacy(UUID pharmacyId) {
        return this.pharmacyMarksRepository.findPharmacyMarksByPharmacy(pharmacyId);
    }

    @Override
    public PharmacyMarks save(PharmacyMarks pharmacyMarks) {
        return this.pharmacyMarksRepository.save(pharmacyMarks);
    }

    @Override
    public List<PharmacyMarks> findPharmacyMarksByPatientId(UUID id) {
        return this.pharmacyMarksRepository.findPharmacyMarksByPatientId(id);
    }

    @Override
    public PharmacyMarks findByPharmacyMarksId(UUID pharmacyMarksId) {
        return this.pharmacyMarksRepository.findPharmacyMarksByPharmacyMarksId(pharmacyMarksId);
    }
}
