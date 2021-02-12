package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.WorkTime;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.*;
import ftn.isa.team12.pharmacy.repository.DermatologistRepository;
import ftn.isa.team12.pharmacy.repository.ExaminationRepository;
import ftn.isa.team12.pharmacy.repository.PharmacyRepository;
import ftn.isa.team12.pharmacy.repository.WorkTimeRepository;
import ftn.isa.team12.pharmacy.service.DermatologistService;
import ftn.isa.team12.pharmacy.service.PharmacyAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(readOnly = false)
public class DermatologistServiceImpl implements DermatologistService {

    @Autowired
    private DermatologistRepository dermatologistRepository;

    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Override
    public Dermatologist saveAndFlush(Dermatologist dermatologistRequest) {
        return this.dermatologistRepository.saveAndFlush(dermatologistRequest);
    }

    @Override
    public Dermatologist findById(UUID userId) {
        return this.dermatologistRepository.findById(userId).get();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Dermatologist findByEmail(String email) {
        return this.dermatologistRepository.findByLoginInfoEmail(email);
    }

    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    @Override
    public List<EmployeesDTO> findAllDermatologist() {
        List<EmployeesDTO> list = new ArrayList<>();
        for(Dermatologist der : dermatologistRepository.findAll()){
            if(!der.getPharmacies().isEmpty()) {
                List<PharmacyDTO> phList = new ArrayList<>();
                for (Pharmacy ph : der.getPharmacies())
                    phList.add(new PharmacyDTO(ph));
                list.add(new EmployeesDTO(der, phList));
            }
        }
        return list;
    }

    @Override
    public List<EmployeesDTO> findAllByPhAdmin(String email) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(email);
        List<EmployeesDTO> list = new ArrayList<>();
        for(Dermatologist der : phAdmin.getPharmacy().getDermatologists()){
            List<PharmacyDTO> phList = new ArrayList<>();
            for(Pharmacy ph : der.getPharmacies())
                phList.add(new PharmacyDTO(ph));
            list.add(new EmployeesDTO(der,phList));
        }
        return list;
    }

    @Override
    public List<EmployeesDTO> searchDermatologist(EmployeesSearchDTO searchDTO) {
        List<EmployeesDTO> dto = new ArrayList<>();
        List<Dermatologist> list;

        if(searchDTO.getEmail().equals("") && searchDTO.getRole().equals(""))
            throw new IllegalArgumentException("Bad input for search");

        if(searchDTO.getRole().equals("ROLE_PATIENT"))
            list = this.searchByPatient(searchDTO);
        else
            list = this.searchByPhAdmin(searchDTO);

        for(Dermatologist der : list){
            List<PharmacyDTO> phList = new ArrayList<>();
            for(Pharmacy ph : der.getPharmacies())
                phList.add(new PharmacyDTO(ph));
            dto.add(new EmployeesDTO(der,phList));
        }

        return dto;
    }

    @Override
    public List<Dermatologist> searchByPatient(EmployeesSearchDTO searchDTO) {
        List<Dermatologist> list = dermatologistRepository.findAll();
        return this.search(searchDTO,list);
    }

    @Override
    public List<Dermatologist> searchByPhAdmin(EmployeesSearchDTO searchDTO) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(searchDTO.getEmail());
        return this.search(searchDTO, new ArrayList<>(phAdmin.getPharmacy().getDermatologists()));
    }

    @Override
    public List<Dermatologist> search(EmployeesSearchDTO searchDTO, List<Dermatologist> dermatologists) {
        List<Dermatologist> list = new ArrayList<>();
        for (Dermatologist der : dermatologists){
            if(!searchDTO.getName().equals("") && !searchDTO.getLastName().equals(""))
                if(der.getAccountInfo().getLastName().equals(searchDTO.getLastName()) && der.getAccountInfo().getName().equals(searchDTO.getName()))
                    list.add(der);
            if(searchDTO.getName().equals("") && !searchDTO.getLastName().equals(""))
                if(der.getAccountInfo().getLastName().equals(searchDTO.getLastName()))
                    list.add(der);
            if(!searchDTO.getName().equals("") && searchDTO.getLastName().equals(""))
                if(der.getAccountInfo().getName().equals(searchDTO.getName()))
                    list.add(der);
        }
        return list;
    }


    @Override
    public boolean deleteDermatologist(DeleteEmployeeDTO dto) {
        this.checkForDeleteDermatologist(dto);
        Dermatologist dermatologist = dermatologistRepository.findByLoginInfoEmail(dto.getEmployeeEmail());
        Pharmacy pharmacy = pharmacyAdministratorService.findAdminByEmail(dto.getPhAdminEmail()).getPharmacy();
        if(!dermatologist.getExaminations().isEmpty()) {
            List<Examination> ex = examinationRepository.findAllByEmployeeAndPharmacy(dermatologist, pharmacy);
            if (ex != null) {
                for (Examination examination : ex) {
                    if (examination.getDateOfExamination().after(new Date())) {
                        examination.setPharmacy(null);
                        examination.setPatient(null);
                        examination.setEmployee(null);
                        if(examination.getExaminationPrice() != null)
                            examination.getExaminationPrice().setPharmacy(null);
                        examination.setExaminationPrice(null);
                        dermatologist.getExaminations().remove(examination);
                        examinationRepository.delete(examination);
                    }
                }
            }
        }

        dermatologist.getPharmacies().remove(pharmacy);
        pharmacy.getDermatologists().remove(dermatologist);
        dermatologistRepository.save(dermatologist);

        return true;
    }

    @Override
    public boolean checkForDeleteDermatologist(DeleteEmployeeDTO dto) {
        Dermatologist dermatologist = dermatologistRepository.findByLoginInfoEmail(dto.getEmployeeEmail());
        PharmacyAdministrator pharmacyAdministrator = pharmacyAdministratorService.findAdminByEmail(dto.getPhAdminEmail());

        boolean flag = true;
        if(dermatologist == null || pharmacyAdministrator == null)
            throw new IllegalArgumentException("bad input");

        for(Pharmacy ph : dermatologist.getPharmacies()) {
            if (ph.getId() == pharmacyAdministrator.getPharmacy().getId()) {
                flag = false;
                break;
            }
        }
        if(flag)
            throw new IllegalArgumentException("No dermatologist with: " + dto.getEmployeeEmail() + " in pharmacy " + pharmacyAdministrator.getPharmacy().getName());

        if(!dermatologist.getExaminations().isEmpty()) {
            List<Examination> ex = examinationRepository.findAllByEmployeeAndPharmacy(dermatologist,pharmacyAdministrator.getPharmacy());
            for(Examination examination: ex){
                if (examination.getPatient() != null && examination.getDateOfExamination().after(new Date()))
                    throw new IllegalArgumentException("Pharmacist have examination " + examination.getDateOfExamination().toString());
            }
        }
        return true;
    }

    @Override
    public Dermatologist addDermatologist(EmployeesCreateDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Dermatologist dermatologist = dermatologistRepository.findByLoginInfoEmail(dto.getEmailPhAdmin());
        Pharmacy pharmacy = pharmacyAdministrator.getPharmacy();
        if(dermatologist == null || pharmacyAdministrator == null)
            throw new IllegalArgumentException("Bad input");

        for(Pharmacy ph : dermatologist.getPharmacies()){
            if(ph.getId().toString().equals(pharmacy.getId().toString()))
                throw new IllegalArgumentException("Already exist in pharmacy " + ph.getName());
        }

        if(!dermatologist.getWorkTime().isEmpty()){
            for(WorkTime w: this.createWorkTime(dto,pharmacyAdministrator.getPharmacy(),dermatologist)){
                dermatologist.getWorkTime().add(w);
            }
        }else
            dermatologist.setWorkTime(this.createWorkTime(dto,pharmacyAdministrator.getPharmacy(),dermatologist));



        dermatologist.getPharmacies().add(pharmacy);
        pharmacy.getDermatologists().add(dermatologist);
        pharmacyRepository.save(pharmacy);
        dermatologist = dermatologistRepository.save(dermatologist);

        return dermatologist;
    }

    @Override
    public Set<WorkTime> createWorkTime(EmployeesCreateDTO dto, Pharmacy pharmacy, Dermatologist dermatologist) {
        List<WorkTime> workTimes = workTimeRepository.findAllByEmployeeUserId(dermatologist.getUserId());
        Set<WorkTime> list = new HashSet<>();
        if(dto.getWorkTimes().isEmpty())
            throw new IllegalArgumentException("Need to add work day");

        for (WorkTimeDTO wtd : dto.getWorkTimes()){
            if(wtd.getEndTime().isAfter(wtd.getStartTime()) && (wtd.getDate().after(new Date()))){
                WorkTime w = new WorkTime();
                w.setStartTime(wtd.getStartTime());
                w.setEndTime(wtd.getEndTime());
                w.setDate(wtd.getDate());
                w.setPharmacy(pharmacy);
                w.setEmployee(dermatologist);
                for(WorkTime work: workTimes){
                    if(work.getDate().equals(w.getDate()))
                        throw new IllegalArgumentException("On " + w.getDate().toString() + " dermatologist work in " + w.getPharmacy().getName());
                }
                if(!list.isEmpty()) {
                    for (WorkTime workTime : list) {
                        if (workTime.getDate().equals(w.getDate()))
                            throw new IllegalArgumentException("Day already added");
                    }
                    list.add(w);
                }
                else
                    list.add(w);
            }else
                throw new IllegalArgumentException("Bad workTime");
        }

        return list;
    }




    @Override
    public List<EmployeesDTO> findAllFromOtherPharmacy() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        List<EmployeesDTO> list = new ArrayList<>();
        boolean flag = false;
        for(Dermatologist der : dermatologistRepository.findAll()){
            List<PharmacyDTO> phList = new ArrayList<>();
            for (Pharmacy ph : der.getPharmacies()) {
                if(!ph.getId().toString().equals(pharmacyAdministrator.getPharmacy().getId().toString())) {
                    phList.add(new PharmacyDTO(ph));
                    flag = true;
                }
            }
            if(flag)
                list.add(new EmployeesDTO(der, phList));
            else{
                if(der.getPharmacies().isEmpty()){
                    list.add(new EmployeesDTO(der, phList));
                }
            }

        }
        return list;
    }


}
