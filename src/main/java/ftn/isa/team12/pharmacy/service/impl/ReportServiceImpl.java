package ftn.isa.team12.pharmacy.service.impl;


import ftn.isa.team12.pharmacy.domain.drugs.DrugReservation;
import ftn.isa.team12.pharmacy.domain.pharmacy.Examination;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Dermatologist;
import ftn.isa.team12.pharmacy.domain.users.Pharmacist;
import ftn.isa.team12.pharmacy.domain.users.PharmacyAdministrator;
import ftn.isa.team12.pharmacy.dto.PeriodeReportsDTO;
import ftn.isa.team12.pharmacy.dto.ReportsAverageMarksDTO;
import ftn.isa.team12.pharmacy.dto.ReportsEmployeeDTO;
import ftn.isa.team12.pharmacy.dto.ReportsMonthlyDTO;
import ftn.isa.team12.pharmacy.repository.*;
import ftn.isa.team12.pharmacy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = false)
public class ReportServiceImpl implements ReportService {
    @Autowired
    DermatologistRepository dermatologistRepository;

    @Autowired
    PharmacistRepository pharmacistRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    ExaminationRepository examinationRepository;

    @Autowired
    DrugReservationRepository drugReservationRepository;

    @Override
    public ReportsAverageMarksDTO averageMarks() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ReportsAverageMarksDTO reportsAverageMarksDTO = new ReportsAverageMarksDTO(pharmacy);

        for(Dermatologist der: pharmacy.getDermatologists())
            reportsAverageMarksDTO.getEmployeeDTOS().add(new ReportsEmployeeDTO(der,"Dermatologist"));

        for(Pharmacist der: pharmacy.getPharmacists())
            reportsAverageMarksDTO.getEmployeeDTOS().add(new ReportsEmployeeDTO(der,"Pharmacist"));


        return reportsAverageMarksDTO;
    }


    @Override
    public List<Integer> yearsReportsExamination(){
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(),1,1);
        List<Integer> years = new ArrayList<>();
        LocalDate end = LocalDate.now();

        for(int i =0; i<12; i++){
            if(i != 0)
                start = start.plusMonths(1);
            end = start.plusMonths(1);
            Date startDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
            Date endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
            List<Examination> examinations = examinationRepository.getALlHealExamination(pharmacy,startDate,endDate);
            years.add(examinations.size());
        }
        return years;
    }


    @Override
    public ReportsMonthlyDTO monthlyReportExamination(Integer month) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(),month,1);
        ReportsMonthlyDTO reportsMonthlyDTO = new ReportsMonthlyDTO();
        LocalDate end = start.plusMonths(1);

        for(int i =0; i<31; i++) {
            if (i != 0)
                start = start.plusDays(1);
            if(start.isBefore(end)) {
                Date startDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
                List<Examination> examinations = examinationRepository.getALlHealExaminationPerDay(pharmacy, startDate);
                if (examinations == null) {
                    reportsMonthlyDTO.getNumberOfExamination().add(0);
                    String a = String.valueOf(start.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                } else {
                    reportsMonthlyDTO.getNumberOfExamination().add(examinations.size());
                    String a = String.valueOf(start.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                }
            }else {
                break;
            }
        }
        return reportsMonthlyDTO;
    }


    @Override
    public List<Integer> yearsReportDrug() throws ParseException {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(),1,1);
        List<Integer> years = new ArrayList<>();
        LocalDate end = LocalDate.now();

        for(int i =0; i<12; i++){
            if(i != 0)
                start = start.plusMonths(1);
            end = start.plusMonths(1);
            Date startDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
            Date endDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
            List<DrugReservation> examinations = drugReservationRepository.getAllForReports(pharmacy,startDate,endDate);
            int quantity = 0;
            for (DrugReservation a: examinations){
                quantity += a.getQuantity();
            }
            years.add(quantity);
        }
        return years;
    }


    @Override
    public ReportsMonthlyDTO monthlyReportDrug(Integer month) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of(now.getYear(),month,1);
        ReportsMonthlyDTO reportsMonthlyDTO = new ReportsMonthlyDTO();
        LocalDate end = start.plusMonths(1);

        for(int i =0; i<31; i++) {
            if (i != 0)
                start = start.plusDays(1);
            if(start.isBefore(end)) {
                Date startDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
                List<DrugReservation> examinations = drugReservationRepository.getALlDrugReservationPerDay(pharmacy,startDate);
                if (examinations == null) {
                    reportsMonthlyDTO.getNumberOfExamination().add(0);
                    String a = String.valueOf(start.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                } else {
                    int price = 0;
                    for(DrugReservation d: examinations){
                        price += d.getPrice();
                    }
                    reportsMonthlyDTO.getNumberOfExamination().add(price);
                    String a = String.valueOf(start.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                }
            }else {
                break;
            }
        }
        return reportsMonthlyDTO;
    }


    @Override
    public ReportsMonthlyDTO reportIncome(PeriodeReportsDTO dto) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        PharmacyAdministrator pharmacyAdministrator = (PharmacyAdministrator) currentUser.getPrincipal();
        Pharmacy pharmacy = pharmacyRepository.findPharmacyById(pharmacyAdministrator.getPharmacy().getId());
        ReportsMonthlyDTO reportsMonthlyDTO = new ReportsMonthlyDTO();
        ZoneId defaultZoneId = ZoneId.systemDefault();

        LocalDate startDate = dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = dto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for(int i =0; i<31; i++) {
            if (i != 0)
                startDate = startDate.plusDays(1);
            if(startDate.isBefore(endDate)) {
                Date start = Date.from(startDate.atStartOfDay(defaultZoneId).toInstant());
                List<DrugReservation> examinations = drugReservationRepository.getALlDrugReservationPerDay(pharmacy,start);
                List<Examination> e = examinationRepository.getALlHealExaminationPerDay(pharmacy, start);
                if (examinations == null || e == null) {
                    reportsMonthlyDTO.getNumberOfExamination().add(0);
                    String a = String.valueOf(startDate.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                } else {
                    int quantity = 0;
                    for(DrugReservation d: examinations){
                        quantity += d.getQuantity();
                    }
                    double examinationPrice = 0;
                    for(Examination ex : e){
                        examinationPrice += ex.getExaminationPrice().getPrice();
                    }

                    quantity = quantity + (int)examinationPrice;
                    reportsMonthlyDTO.getNumberOfExamination().add(quantity);
                    String a = String.valueOf(startDate.getDayOfMonth());
                    reportsMonthlyDTO.getDays().add(a);
                }
            }else {
                break;
            }
        }

        return reportsMonthlyDTO;
    }
}
