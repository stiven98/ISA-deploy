package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import ftn.isa.team12.pharmacy.domain.users.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface VacationRepository extends JpaRepository<Vacation, UUID> {

    @Query("select d from Vacation d where d.pharmacy= ?1 and d.dateRange.startDate < ?2 and d.dateRange.endDate > ?2 and d.employee = ?3")
    List<Vacation> getAll(Pharmacy pharmacy, Date date, MedicalStuff medicalStuff);

    @Query("select d from Vacation d where d.pharmacy= ?1 and d.dateRange.startDate > ?2")
    List<Vacation> getAllFromPharmacy(Pharmacy pharmacy , Date date);



}
