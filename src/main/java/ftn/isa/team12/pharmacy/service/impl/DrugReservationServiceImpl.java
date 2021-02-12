package ftn.isa.team12.pharmacy.service.impl;
import ftn.isa.team12.pharmacy.domain.common.DateRange;
import ftn.isa.team12.pharmacy.domain.common.LoyaltyProgram;
import ftn.isa.team12.pharmacy.domain.drugs.Drug;
import ftn.isa.team12.pharmacy.domain.drugs.DrugInPharmacy;
import ftn.isa.team12.pharmacy.domain.drugs.DrugReservation;
import ftn.isa.team12.pharmacy.domain.enums.ReservationStatus;
import ftn.isa.team12.pharmacy.domain.pharmacy.Pharmacy;
import ftn.isa.team12.pharmacy.domain.users.Patient;
import ftn.isa.team12.pharmacy.dto.DrugReservationDTO;
import ftn.isa.team12.pharmacy.email.EmailSender;
import ftn.isa.team12.pharmacy.repository.DrugInPharmacyRepository;
import ftn.isa.team12.pharmacy.repository.DrugReservationRepository;
import ftn.isa.team12.pharmacy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@EnableScheduling
@Service
@Transactional(readOnly = false)
public class DrugReservationServiceImpl implements DrugReservationService {

    @Autowired
    private DrugReservationRepository drugReservationRepository;

    @Autowired
    private EmailSender sender;

    @Autowired
    private DrugInPharmacyService drugInPharmacyService;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private LoyaltyProgramService loyaltyProgramService;

    @Autowired
    private DrugService drugService;

    @Autowired
    private DrugInPharmacyRepository drugInPharmacyRepository;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public DrugReservation createDrugReservation(DrugReservationDTO drugReservationDTO) throws Exception {
        Patient patient = this.patientService.findByEmail(drugReservationDTO.getPatientEmail());
        if(drugReservationDTO.getDeadline().before(new Date())){
            throw new IllegalArgumentException("Bad input date");
        }
        if(patient.getPenalties() > 2) {
            throw new IllegalArgumentException("You have 3 or more penalties and you cant reserve drug");
        }
        LoyaltyProgram lp = this.loyaltyProgramService.getLoyaltyProgram();
        double discount = lp.getDiscountByCategory(patient.getCategory().getCategory());
        DrugReservation drugReservation = new DrugReservation();
        Drug drug = this.drugService.findById(drugReservationDTO.getDrugId());
        Pharmacy pharmacy = this.pharmacyService.findPharmacyById(drugReservationDTO.getPharmacyId());
        DateRange dateRange = new DateRange();
        dateRange.setStartDate(new Date());
        dateRange.setEndDate(drugReservationDTO.getDeadline());
        drugReservation.setPatient(patient);
        drugReservation.setPharmacy(pharmacy);
        drugReservation.setQuantity(drugReservationDTO.getQuantity());
        drugReservation.setReservationStatus(ReservationStatus.created);
        drugReservation.setReservationDateRange(dateRange);
        drugReservation.setDrug(drug);
        drugReservation.setPrice(drugReservationDTO.getPrice());
        discount = (1.0 * discount/100) * drugReservation.getPrice();
        BigDecimal bd1 = new BigDecimal(discount).setScale(2, RoundingMode.HALF_UP);
        double nr = bd1.doubleValue();
        drugReservation.setDiscount(nr);
        drugReservation = this.save(drugReservation);

        DrugInPharmacy drugInPharmacy = this.drugInPharmacyRepository.findDrugInPharmacy(drug.getDrugId(),pharmacy.getId());
        int beforeRes = drugInPharmacy.getQuantity();
        int newQuantity = beforeRes - drugReservationDTO.getQuantity();
        drugInPharmacy.setQuantity(newQuantity);
        this.drugInPharmacyService.save(drugInPharmacy);
        try {
            sender.sendDrugReservationEmail(drugReservation.getDrug_reservation_id(), patient.getLoginInfo().getEmail(), pharmacy.getName(), drugReservationDTO.getDeadline().toString(),
                    drug.getName());
        } catch (Exception e) {
            System.out.println(e);
        }
        return drugReservation;
    }

    @Override
    public List<DrugReservation> findDrugReservationByPatient(String patientEmail) {
        Patient patient = this.patientService.findByEmail(patientEmail);
        List<DrugReservation> drugReservations = this.drugReservationRepository.findAllByPatientUserId(patient.getUserId());
        return drugReservations;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public DrugReservation cancelReservation(UUID id) {

        Calendar calendar = Calendar.getInstance();
        DrugReservation drugReservation = this.drugReservationRepository.findDrugReservationByDrug_reservation_id(id);
        Date deadline = drugReservation.getReservationDateRange().getEndDate();
        calendar.setTime(deadline);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date dayberofedealdine = calendar.getTime();
        if (new Date().before(dayberofedealdine)) {
            drugReservation.setReservationStatus(ReservationStatus.cancelled);
            this.save(drugReservation);
            DrugInPharmacy drugInPharmacy = this.drugInPharmacyRepository.findDrugInPharmacy(drugReservation.getDrug().getDrugId(), drugReservation.getPharmacy().getId());
            int returnQuantity = drugReservation.getQuantity();
            int currentQuantity = drugInPharmacy.getQuantity();
            int saveQuantity = currentQuantity + returnQuantity;
            drugInPharmacy.setQuantity(saveQuantity);
            this.drugInPharmacyService.save(drugInPharmacy);
            return drugReservation;
        } else {
            throw new IllegalArgumentException("You cant cancel reservation 24h before deadline");
        }
    }

    @Override
    public List<Pharmacy> findPharmaciesWherePatientReservedDrugs(String  patientEmail) {
        Patient patient = this.patientService.findByEmail(patientEmail);
        return this.drugReservationRepository.findPharmaciesWherePatientReservedDrugs(patient.getUserId());
    }

    @Override
    public List<Drug> findDrugsPatientReserved(String patientEmail) {
        Patient patient = this.patientService.findByEmail(patientEmail);
        return this.drugReservationRepository.findDrugsPatientReserved(patient.getUserId());
    }

    @Override
    public DrugReservation findDrugReservationByIdAndPharmacyId(UUID id, UUID pharmacyId) {
        DrugReservation drugReservation = this.drugReservationRepository.findDrugReservationByIdAndPharmacyId(id, pharmacyId);
        if(drugReservation != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            Date endDate = drugReservation.getReservationDateRange().getEndDate();
            boolean isNotPassed = (sdf.format(today).compareTo(sdf.format(endDate))) < 0;
            boolean canIssue = drugReservation.getReservationStatus() == ReservationStatus.created;
            if(isNotPassed && canIssue){
                return drugReservation;
            }
            else{
                return null;
            }
        }
        return drugReservation;
    }

    @Scheduled(fixedRate = 86400000)
    public void checkReservation(){
        List<DrugReservation> reservations = this.findAllBefore(new Date());
        for(DrugReservation dr : reservations){
            Patient patient = dr.getPatient();
            dr.setReservationStatus(ReservationStatus.cancelled);
            patient.setPenalties(patient.getPenalties() + 1);
            patientService.save(patient);
            drugReservationRepository.save(dr);
        }

    }

    @Override
    @Transactional(readOnly = false)
    public DrugReservation issueDrug(UUID id) {
        DrugReservation drugReservation = this.drugReservationRepository.findDrugReservationById(id);
        Patient patient = drugReservation.getPatient();
        LoyaltyProgram lp = this.loyaltyProgramService.getLoyaltyProgram();
        Drug drug = drugReservation.getDrug();
        patient.getCategory().setPoints(patient.getCategory().getPoints() + drug.getPoints());
        patient.getCategory().setCategory(lp.getCategory(patient.getCategory().getPoints()));
        drugReservation.setReservationStatus(ReservationStatus.checked);
        this.drugReservationRepository.save(drugReservation);
        this.patientService.save(patient);
        return drugReservation;
    }

    @Override
    public List<DrugReservation> findAllBefore(Date today) {
        return this.drugReservationRepository.findAllBefore(today);
    }


    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public DrugReservation save(DrugReservation drugReservation) {
        return this.drugReservationRepository.save(drugReservation);
    }

}
