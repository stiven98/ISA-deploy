package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, UUID> {

    @Query("select d from VacationRequest d where d.pharmacy= ?1 and d.dateRange.startDate > ?2")
    List<VacationRequest> getAllFromPharmacy(Pharmacy pharmacy, Date date);

    @Query("select d from VacationRequest d where d.dateRange.startDate > ?1")
    List<VacationRequest> getAllFromDermatologist(Date date);

    VacationRequest findByVacationId(UUID id);
}
