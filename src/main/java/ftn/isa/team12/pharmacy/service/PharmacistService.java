package ftn.isa.team12.pharmacy.service;
import ftn.isa.team12.pharmacy.domain.common.WorkTime;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.dto.DeleteEmployeeDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesCreateDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesDTO;
import ftn.isa.team12.pharmacy.dto.EmployeesSearchDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PharmacistService {

    Pharmacist findByEmail(String email);
    List<Pharmacist> findAll();
    List<EmployeesDTO> findAllPharmacist();
    List<EmployeesDTO> findAllByPhADmin(String email);
    List<EmployeesDTO> searchPharmacist(EmployeesSearchDTO searchDTO);
    List<Pharmacist> serachByPatient(EmployeesSearchDTO searchDTO);
    List<Pharmacist> serachByPhAdmin(EmployeesSearchDTO searchDTO);
    List<Pharmacist> search(EmployeesSearchDTO searchDTO , List<Pharmacist> dermatologists);
    Pharmacist createPharmacist(EmployeesCreateDTO dto);
    Set<WorkTime> createWorkTime(EmployeesCreateDTO dto, Pharmacy pharmacy, Pharmacist pharmacist);
    boolean validation(EmployeesCreateDTO dto);

    boolean deletePharmacist(DeleteEmployeeDTO dto);
    boolean checkForDeletePharmacist(DeleteEmployeeDTO dto);

    Pharmacist findById(UUID id);

    Pharmacist save(Pharmacist p);
}
