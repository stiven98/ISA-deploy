package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.drugs.Contraindication;
import java.util.List;
import java.util.Set;

public interface ContraindicationService {

    List<Contraindication> findAll();

    Contraindication saveAndFlush(Contraindication contraindicationRequest);

    Set<Contraindication> getByIds(List<String> contraindication);
}
