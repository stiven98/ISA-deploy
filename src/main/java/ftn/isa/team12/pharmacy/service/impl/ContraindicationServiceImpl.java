package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.drugs.Contraindication;
import ftn.isa.team12.pharmacy.repository.ContraindicationRepository;
import ftn.isa.team12.pharmacy.service.ContraindicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = false)
public class ContraindicationServiceImpl implements ContraindicationService {

    @Autowired
    private ContraindicationRepository contraindicationRepository;

    public List<Contraindication> findAll() {
        return this.contraindicationRepository.findAll();
    }

    @Override
    public Contraindication saveAndFlush(Contraindication contraindicationRequest) {
        if (contraindicationRequest.getName().length() < 1) throw new IllegalArgumentException("Invalid name of contraindication!");
        return this.contraindicationRepository.saveAndFlush(contraindicationRequest);
    }

    @Override
    public Set<Contraindication> getByIds(List<String> contraindication) {
        Set<Contraindication> res = new HashSet<Contraindication>();
        for (String id: contraindication) {
            res.add((Contraindication) contraindicationRepository.findById(UUID.fromString(id)).get());
        }

        return res;

    }
}
