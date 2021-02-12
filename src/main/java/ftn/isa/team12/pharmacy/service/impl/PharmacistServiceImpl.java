package ftn.isa.team12.pharmacy.service.impl;

import ftn.isa.team12.pharmacy.domain.common.*;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.*;
import ftn.isa.team12.pharmacy.dto.*;
import ftn.isa.team12.pharmacy.repository.PharmacistRepository;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = false)
public class PharmacistServiceImpl implements PharmacistService {

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private PharmacyAdministratorService pharmacyAdministratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CityService cityService;

    @Override
    public Pharmacist findByEmail(String email) {
        return this.pharmacistRepository.findByLoginInfoEmail(email);
    }

    @Override
    public List<Pharmacist> findAll() {
        return this.pharmacistRepository.findAll();
    }

    @Override
    public List<EmployeesDTO> findAllPharmacist() {
        List<EmployeesDTO> list = new ArrayList<>();
        for(Pharmacist pharmacist : pharmacistRepository.findAll()){
            List<PharmacyDTO> phList = new ArrayList<>();
            phList.add(new PharmacyDTO(pharmacist.getPharmacy()));
            list.add(new EmployeesDTO(pharmacist,phList));
        }
        return list;
    }

    @Override
    public List<EmployeesDTO> findAllByPhADmin(String email) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(email);
        List<EmployeesDTO> list = new ArrayList<>();
        for(Pharmacist pharmacist : phAdmin.getPharmacy().getPharmacists()){
            List<PharmacyDTO> phList = new ArrayList<>();
            phList.add(new PharmacyDTO(pharmacist.getPharmacy()));
            list.add(new EmployeesDTO(pharmacist,phList));
        }
        return list;
    }

    @Override
    public List<EmployeesDTO> searchPharmacist(EmployeesSearchDTO searchDTO) {
        List<EmployeesDTO> dto = new ArrayList<>();
        List<Pharmacist> list;

        if(searchDTO.getEmail().equals("") && searchDTO.getRole().equals(""))
            throw new IllegalArgumentException("Bad input for search");

        if(searchDTO.getRole().equals("ROLE_PATIENT"))
            list = this.serachByPatient(searchDTO);
        else
            list = this.serachByPhAdmin(searchDTO);

        for(Pharmacist pharmacist : list){
            List<PharmacyDTO> phList = new ArrayList<>();
            phList.add(new PharmacyDTO(pharmacist.getPharmacy()));
            dto.add(new EmployeesDTO(pharmacist,phList));
        }
        return dto;
    }

    @Override
    public List<Pharmacist> serachByPatient(EmployeesSearchDTO searchDTO) {
        List<Pharmacist> list = pharmacistRepository.findAll();
        return this.search(searchDTO,list);
    }

    @Override
    public List<Pharmacist> serachByPhAdmin(EmployeesSearchDTO searchDTO) {
        PharmacyAdministrator phAdmin = pharmacyAdministratorService.findAdminByEmail(searchDTO.getEmail());
        return this.search(searchDTO, new ArrayList<>(phAdmin.getPharmacy().getPharmacists()));
    }

    @Override
    public List<Pharmacist> search(EmployeesSearchDTO searchDTO, List<Pharmacist> pharmacist) {
        List<Pharmacist> list = new ArrayList<>();
        for (Pharmacist phar : pharmacist){
            if(!searchDTO.getName().equals("") && !searchDTO.getLastName().equals(""))
                if(phar.getAccountInfo().getLastName().equals(searchDTO.getLastName()) && phar.getAccountInfo().getName().equals(searchDTO.getName()))
                    list.add(phar);
            if(searchDTO.getName().equals("") && !searchDTO.getLastName().equals(""))
                if(phar.getAccountInfo().getLastName().equals(searchDTO.getLastName()))
                    list.add(phar);
            if(!searchDTO.getName().equals("") && searchDTO.getLastName().equals(""))
                if(phar.getAccountInfo().getName().equals(searchDTO.getName()))
                    list.add(phar);
        }
        return list;
    }


    @Override
    public Pharmacist createPharmacist(EmployeesCreateDTO dto) {
        if(this.validation(dto))
            throw new IllegalArgumentException("Input not valide");

        if(userService.findUserByEmail(dto.getUser().getEmail()) != null)
            throw new IllegalArgumentException("Email already exists");

        Country country = countryService.saveAndFlush(new Country(dto.getUser().getCountryName()));

        City city = cityService.saveAndFlush(new City(dto.getUser().getCityName(), country, dto.getUser().getZipCode()));

        Address address = new Address(dto.getUser().getStreet(), dto.getUser().getStreetNumber());
        Location location = locationService.saveAndFlush(new Location(city, address));

        Pharmacy ph = pharmacyAdministratorService.findAdminByEmail(dto.getEmailPhAdmin()).getPharmacy();
        Pharmacist pharmacist = new Pharmacist();
        ph.getPharmacists().add(pharmacist);
        pharmacist.setPharmacy(ph);
        pharmacist.setAccountInfo(new AccountInfo(dto.getUser().getName(),dto.getUser().getLastName(),dto.getUser().getPhoneNumber(),false,true));
        pharmacist.setLoginInfo(new LoginInfo());
        pharmacist.getLoginInfo().setPassword("$2y$10$FEUww.MoQd8La2ZVp05CD.3Pum8kpy25PdMszrLvlWifF6JguCzQy");//123
        pharmacist.getLoginInfo().setEmail(dto.getUser().getEmail());
        pharmacist.setWorkTime(this.createWorkTime(dto,ph,pharmacist));
        pharmacist.setAuthorities(authorityService.findByRole("ROLE_PHARMACIST"));
        pharmacist.setAverageMark(0.0);
        pharmacist.setLocation(location);
        pharmacist = pharmacistRepository.save(pharmacist);
        return pharmacist;

    }

    @Override
    public Set<WorkTime> createWorkTime(EmployeesCreateDTO dto, Pharmacy pharmacy, Pharmacist pharmacist) {
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
                w.setEmployee(pharmacist);
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
    public boolean validation(EmployeesCreateDTO dto) {
        if(dto.getUser().getName().equals("") || dto.getUser().getLastName().equals("") || dto.getUser().getEmail().equals(""))
            return true;
        else if(dto.getUser().getStreet().equals("") || dto.getUser().getCityName().equals("") || dto.getUser().getCountryName().equals(""))
            return true;
        else if(dto.getUser().getStreetNumber() < 0 || dto.getUser().getZipCode() < 0)
            return true;
        else if(dto.getWorkTimes().isEmpty())
            return true;
        return false;
    }

    @Override
    public boolean deletePharmacist(DeleteEmployeeDTO dto) {
        this.checkForDeletePharmacist(dto);
        Pharmacist pharmacist = pharmacistRepository.findByLoginInfoEmail(dto.getEmployeeEmail());
        pharmacist.setPharmacy(null);
        pharmacist.setAuthorities(null);
        pharmacist.setLocation(null);

        for (WorkTime w : pharmacist.getWorkTime()){
            w.setPharmacy(null);
            w.setEmployee(null);
        }
        if(!pharmacist.getExaminations().isEmpty()) {
            for (Examination x : pharmacist.getExaminations()) {
                x.setEmployee(null);
                x.setPharmacy(null);
                x.setPatient(null);
                x.getExaminationPrice().setPharmacy(null);
                x.setExaminationPrice(null);
            }
        }
        if(!pharmacist.getVacations().isEmpty()){
            for(Vacation v: pharmacist.getVacations()){
                v.setPharmacy(null);
                v.setEmployee(null);
            }
        }
        pharmacistRepository.delete(pharmacist);
        return true;
    }

    @Override
    public boolean checkForDeletePharmacist(DeleteEmployeeDTO dto) {
        Pharmacist pharmacist = pharmacistRepository.findByLoginInfoEmail(dto.getEmployeeEmail());
        PharmacyAdministrator ph = pharmacyAdministratorService.findAdminByEmail(dto.getPhAdminEmail());

        if(pharmacist == null || ph == null)
            throw new IllegalArgumentException("No pharmacist");
        if(ph.getPharmacy().getId() !=  pharmacist.getPharmacy().getId())
            throw new IllegalArgumentException("No pharmacist with:" + pharmacist.getLoginInfo().getEmail() + " in pharmacy " + ph.getPharmacy().getName());

        if(!pharmacist.getExaminations().isEmpty()) {
            for (Examination x : pharmacist.getExaminations()) {
                if (x.getPatient() != null && x.getDateOfExamination().after(new Date()))
                    throw new IllegalArgumentException("Pharmacist have examination " + x.getDateOfExamination().toString());
            }
        }
        return true;
    }

    @Override
    public Pharmacist findById(UUID id) {
        return this.pharmacistRepository.findById(id).get();
    }

    @Override
    public Pharmacist save(Pharmacist p) {
        return this.pharmacistRepository.save(p);
    }
}
