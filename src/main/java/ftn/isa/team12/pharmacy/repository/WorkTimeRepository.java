package ftn.isa.team12.pharmacy.repository;

import ftn.isa.team12.pharmacy.domain.common.WorkTime;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.MedicalStuff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface WorkTimeRepository extends JpaRepository<WorkTime,UUID> {

    List<WorkTime> findAllByEmployeeUserId(UUID id);
    List<WorkTime> findAllByEmployeeLoginInfoEmail(String email);
    WorkTime findByEmployeeAndPharmacyAndDate(MedicalStuff employee, Pharmacy pharmacy, Date date);

}
