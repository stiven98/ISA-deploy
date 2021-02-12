package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PharmacistRepository extends JpaRepository<Pharmacist, UUID> {

    Pharmacist findByLoginInfoEmail(String email);
    Pharmacist findByUserId(UUID id);

}
