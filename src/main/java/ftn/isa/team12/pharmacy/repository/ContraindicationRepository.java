package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.drugs.Contraindication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ContraindicationRepository  extends JpaRepository<Contraindication, UUID> {
}
